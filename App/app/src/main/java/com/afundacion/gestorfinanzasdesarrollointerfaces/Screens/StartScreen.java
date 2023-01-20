package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.afundacion.gestorfinanzasdesarrollointerfaces.ApiCall;
import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class StartScreen extends AppCompatActivity {
    private TextView textView;
    private Context context = this;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        ApiCall apiCall = new ApiCall(this);

        textView = findViewById(R.id.text_view);
        apiCall.makeApiCall("https://63c7b7205c0760f69abc6591.mockapi.io/api/transactions", textView);
    }
    private void retrieveUserStatus() {
        SharedPreferences preferences = getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        String username = preferences.getString("VALID_USERNAME", null);
        JsonObjectRequestWithAuthentication request = new JsonObjectRequestWithAuthentication(Request.Method.GET,
                Server.name + "/users/" + username + "/status",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Estado obtenido", Toast.LENGTH_LONG).show();
                        try {
                            textViewUserStatus.setText(response.getString("status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Estado KO", Toast.LENGTH_LONG).show();
                    }
                },
                context
        );
        queue.add(request);
    }
}