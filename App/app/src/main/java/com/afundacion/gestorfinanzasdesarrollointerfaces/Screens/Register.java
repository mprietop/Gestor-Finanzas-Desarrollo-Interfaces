package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private final String BASE_API_URL = "https://883325ad-0f53-4488-9414-093857463fb4.mock.pstmn.io";
    private RequestQueue queue;

    private EditText email, username, password, secondPassword;
    private Button submitButton;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        queue = Volley.newRequestQueue(this);

        // Asociando a los objetos java los objetos xml
        email = findViewById(R.id.emailEditText);
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        secondPassword = findViewById(R.id.secondPasswordEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(submitListener);
        // Peticion inicial para comprobar si se conecta bien con la API
        peticionInicial();
    }

    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (email.getText().toString().length() == 0) {
                email.setError("Campo obligatorio");
            }

            if (username.getText().toString().length() == 0) {
                username.setError("Campo obligatorio");
            }

            if (password.getText().toString().equals(secondPassword.getText().toString()) && password.length() != 0) {
                registrarUsuario();
            } else {
                // Vaciar los campos contresena?
                password.setError("Las contraseñas no coinciden");
            }

        }
    };

    public void peticionInicial() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                BASE_API_URL + "/health",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(Register.this, response.getString("health"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(Register.this, "Error conectando con el API", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        this.queue.add(request);
    }

    public void registrarUsuario() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                BASE_API_URL + "/users",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Register.this, "oki", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> user = new HashMap<String, String>();
                user.put("email", email.getText().toString());
                user.put("username", username.getText().toString());
                user.put("password", password.getText().toString());
                return user;
            }
        };

        this.queue.add(request);
    }
}
