package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
    private TextView money1, money2, money3, money4, money5, money6, money7, type1, type2, type3, type4, type5, type6, type7,SaldoMax;
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

        SaldoMax = findViewById(R.id.TotalSaldoText);

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
                        double saldo1 = 0,saldo2 = 0,saldo3 = 0,saldo4 = 0,saldo5 = 0,saldo6 = 0,saldo7 = 0,sum;

                        try {
                                type1.setText(response.getJSONObject(0).getString("type"));
                                money1.setText(response.getJSONObject(0).getString("amount"));
                                    if (response.getJSONObject(0).getString("type").equals("Ingreso")) {
                                    saldo1 = Double.valueOf(response.getJSONObject(0).getString("amount"));
                                    } else {
                                    saldo1 = -Double.valueOf(response.getJSONObject(0).getString("amount"));
                                    }


                                money2.setText(response.getJSONObject(1).getString("amount"));
                                type2.setText(response.getJSONObject(1).getString("type"));
                                    if (response.getJSONObject(1).getString("type").equals("Ingreso")) {
                                        saldo2 = Double.valueOf(response.getJSONObject(1).getString("amount"));
                                    } else {
                                        saldo2 = -Double.valueOf(response.getJSONObject(1).getString("amount"));
                                    }


                                money3.setText(response.getJSONObject(2).getString("amount"));
                                type3.setText(response.getJSONObject(2).getString("type"));
                                    if (response.getJSONObject(2).getString("type").equals("Ingreso")) {
                                        saldo3 = Double.valueOf(response.getJSONObject(2).getString("amount"));
                                    } else {
                                        saldo3 = -Double.valueOf(response.getJSONObject(2).getString("amount"));
                                    }


                                money4.setText(response.getJSONObject(3).getString("amount"));
                                type4.setText(response.getJSONObject(3).getString("type"));
                                    if (response.getJSONObject(3).getString("type").equals("Ingreso")) {
                                        saldo4 = Double.valueOf(response.getJSONObject(3).getString("amount"));
                                    } else {
                                        saldo4 = -Double.valueOf(response.getJSONObject(3).getString("amount"));
                                    }

                                money5.setText(response.getJSONObject(4).getString("amount"));
                                type5.setText(response.getJSONObject(4).getString("type"));
                                    if (response.getJSONObject(4).getString("type").equals("Ingreso")) {
                                        saldo5 = Double.valueOf(response.getJSONObject(4).getString("amount"));
                                    } else {
                                        saldo5 = -Double.valueOf(response.getJSONObject(4).getString("amount"));
                                    }


                                money6.setText(response.getJSONObject(5).getString("amount"));
                                type6.setText(response.getJSONObject(5).getString("type"));
                                    if (response.getJSONObject(5).getString("type").equals("Ingreso")) {
                                        saldo6 = Double.valueOf(response.getJSONObject(5).getString("amount"));
                                    } else {
                                        saldo6 = -Double.valueOf(response.getJSONObject(5).getString("amount"));
                                    }


                                money7.setText(response.getJSONObject(6).getString("amount"));
                                type7.setText(response.getJSONObject(6).getString("type"));
                                    if (response.getJSONObject(6).getString("type").equals("Ingreso")) {
                                        saldo7 = Double.valueOf(response.getJSONObject(6).getString("amount"));
                                    } else {
                                        saldo7 = -Double.valueOf(response.getJSONObject(6).getString("amount"));
                                    }


                                sum = saldo1+saldo2+saldo3+saldo4+saldo5+saldo6+saldo7;
                                SaldoMax.setText(String.valueOf(sum));

                        }catch (JSONException e) {
                            sum = saldo1+saldo2+saldo3+saldo4+saldo5+saldo6+saldo7;
                            SaldoMax.setText(String.valueOf(sum));
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
