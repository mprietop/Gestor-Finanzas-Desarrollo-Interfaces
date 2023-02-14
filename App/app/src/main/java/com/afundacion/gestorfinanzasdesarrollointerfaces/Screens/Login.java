package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.Drawer;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.Rest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private Button registerButton, loginButton;
    private EditText email, password;
    private Context context = this;
    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(registerListener);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginListener);

        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);

        queue = Volley.newRequestQueue(this);
    }

    @Override
    public void onBackPressed() {
    }

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            iniciarSesion();
        }
    };

    private void iniciarSesion() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                Rest.getBASE_URL() + "/users?email=" + email.getText().toString(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject user = response.getJSONObject(0);

                            if (email.getText().toString().equals(user.getString("email")) && password.getText().toString().equals(user.getString("password"))) {
                                //Toast.makeText(Login.this, user.getString("id"), Toast.LENGTH_SHORT).show();

                                // Guardando el id del usuario en las sharedPreferences
                                SharedPreferences prefs = getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("userId",Integer.parseInt(user.getString("id")));
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(), Drawer.class);
                                startActivity(intent);
                            } else {
                                email.setError("Email y/o contraseña incorrectos");
                            }
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        this.queue.add(request);
    }




}
