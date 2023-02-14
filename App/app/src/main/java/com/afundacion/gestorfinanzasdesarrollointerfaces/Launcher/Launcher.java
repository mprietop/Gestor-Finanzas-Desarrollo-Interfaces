package com.afundacion.gestorfinanzasdesarrollointerfaces.Launcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzasdesarrollointerfaces.Screens.Login;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.Drawer;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.Rest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Launcher extends AppCompatActivity {
    private Context context = this;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent intent = new Intent(Launcher.this, StartScreen.class);
        inicioSesionToken();
    }

    public void inicioSesionToken() {
        SharedPreferences prefs = getSharedPreferences("user", Context.MODE_PRIVATE);
        int id = prefs.getInt("userId",-1);
        Log.wtf("Mario", String.valueOf(id));

        if (id != -1) {
            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    Rest.getBASE_URL() + "/users?id=" + id,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response.length()>0) {
                                try {
                                    Log.wtf("Mario", "entro");
                                    JSONObject json = response.getJSONObject(0);

                                    Intent intent = new Intent(context, Drawer.class);
                                    Toast.makeText(context, "Iniciando sesión, " + json.getString("username"), Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error cargando el token", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, Login.class);
                            startActivity(intent);
                        }
                    }
            );
            queue = Volley.newRequestQueue(this);
            queue.add(request);
        } else {
            Intent intent = new Intent(context, Login.class);
            startActivity(intent);
        }
    }
}
