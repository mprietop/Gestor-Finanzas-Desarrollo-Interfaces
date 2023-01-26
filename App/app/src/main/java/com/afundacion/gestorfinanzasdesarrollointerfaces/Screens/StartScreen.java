package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class StartScreen extends AppCompatActivity {
    private TextView money1, money2, money3, money4, money5, money6, money7, type1, type2, type3, type4, type5, type6, type7;
    private Context context = this;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        money1 = findViewById(R.id.Money1);
        money2 = findViewById(R.id.Money2);
        money3 = findViewById(R.id.Money3);
        money4 = findViewById(R.id.Money4);
        money5 = findViewById(R.id.Money5);
        money6 = findViewById(R.id.Money6);
        money7 = findViewById(R.id.Money7);

        type1 = findViewById(R.id.Type1);
        type2 = findViewById(R.id.Type2);
        type3 = findViewById(R.id.Type3);
        type4 = findViewById(R.id.Type4);
        type5 = findViewById(R.id.Type5);
        type6 = findViewById(R.id.Type6);
        type7 = findViewById(R.id.Type7);



        queue = Volley.newRequestQueue(this);
        JsonRequest();
    }

    public void JsonRequest() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://63c7b7205c0760f69abc6591.mockapi.io/api/transactions",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            type1.setText(response.getJSONObject(0).getString("type"));
                            money1.setText(response.getJSONObject(0).getString("amount"));


                            money2.setText(response.getJSONObject(1).getString("amount"));
                            type2.setText(response.getJSONObject(1).getString("type"));

                            money3.setText(response.getJSONObject(2).getString("amount"));
                            type3.setText(response.getJSONObject(2).getString("type"));

                            money4.setText(response.getJSONObject(3).getString("amount"));
                            type4.setText(response.getJSONObject(3).getString("type"));

                            money5.setText(response.getJSONObject(4).getString("amount"));
                            type5.setText(response.getJSONObject(4).getString("type"));

                            money6.setText(response.getJSONObject(5).getString("amount"));
                            type6.setText(response.getJSONObject(5).getString("type"));

                            money7.setText(response.getJSONObject(6).getString("amount"));
                            type7.setText(response.getJSONObject(6).getString("type"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        this.queue.add(request);
    }
}
