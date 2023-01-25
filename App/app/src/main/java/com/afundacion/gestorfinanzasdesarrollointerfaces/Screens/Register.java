package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.Rest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private RequestQueue queue;

    private EditText email, username, password, secondPassword;
    private Button submitButton;
    private ProgressBar loadingSpinner;
    private boolean yaRegistrado;

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
            } else if (!validarEmail(email.getText().toString())) {
                email.setError("Email no válido");
            } else if (username.getText().toString().length() == 0) {
                username.setError("Campo obligatorio");
            } else if (!password.getText().toString().equals(secondPassword.getText().toString())) {
                password.setError("Las contraseñas no coinciden");
            } else if (password.length() == 0) {
                password.setError("Campo obligatorio");
            } else if (estaRegistrado()) {
                email.setError("Email ya registrado");
            } else {
                registrarUsuario();
            }

            /*
            if (email.getText().toString().length() == 0) {
                email.setError("Campo obligatorio");
            } else if (!validarEmail(email.getText().toString())) {
                email.setError("Email no válido");
            }


            if (username.getText().toString().length() == 0) {
                username.setError("Campo obligatorio");
            }

            if (!password.getText().toString().equals(secondPassword.getText().toString())) {
                password.setError("Las contraseñas no coinciden");
            } else if (password.length() == 0) {
                password.setError("Campo obligatorio");
            } else {
                registrarUsuario();
            }

             */

        }
    };

    public void peticionInicial() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Rest.getBASE_URL() + "/health",
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
                Rest.getBASE_URL() + "/users",
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

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public boolean estaRegistrado() {
        yaRegistrado = false;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                Rest.getBASE_URL() + "/users?email=" + email.getText().toString(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.wtf("Mario",String.valueOf(response.length()));
                        if (response.length() > 0) {
                            Log.wtf("Mario", "Entró");
                            yaRegistrado = true;
                            Log.wtf("Mario", String.valueOf(yaRegistrado));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        this.queue.add(request);
        Log.wtf("Mario", String.valueOf(yaRegistrado));
        return yaRegistrado;
    }

}
