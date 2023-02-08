package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class StartScreen extends Fragment {
    private TextView money1, money2, money3, money4, money5, money6, money7, type1, type2, type3, type4, type5, type6, type7,SaldoMax;
    private RequestQueue queue;
    private Context context;

    public static StartScreen newInstance() {
        StartScreen fragment = new StartScreen();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.activity_start_screen,container,false);

        money1 = view.findViewById(R.id.Money1);
        money2 = view.findViewById(R.id.Money2);
        money3 = view.findViewById(R.id.Money3);
        money4 = view.findViewById(R.id.Money4);
        money5 = view.findViewById(R.id.Money5);
        money6 = view.findViewById(R.id.Money6);
        money7 = view.findViewById(R.id.Money7);

        type1 = view.findViewById(R.id.Type1);
        type2 = view.findViewById(R.id.Type2);
        type3 = view.findViewById(R.id.Type3);
        type4 = view.findViewById(R.id.Type4);
        type5 = view.findViewById(R.id.Type5);
        type6 = view.findViewById(R.id.Type6);
        type7 = view.findViewById(R.id.Type7);

        SaldoMax = view.findViewById(R.id.TotalSaldoText);


        queue = Volley.newRequestQueue(context);
        JsonRequest();
        return view;
    }

    public void JsonRequest() {
        SharedPreferences prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        //String userId = "1";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://63c7b7205c0760f69abc6591.mockapi.io/api/users/"+String.valueOf(userId)+"/transactions",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        double saldo1 = 0,saldo2 = 0,saldo3 = 0,saldo4 = 0,saldo5 = 0,saldo6 = 0,saldo7 = 0,sum;

                        JSONArray sortedJsonArray = new JSONArray();
                        List<JSONObject> jsonList = new ArrayList<JSONObject>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonList.add(response.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Collections.sort(jsonList, new Comparator<JSONObject>() {
                            public int compare(JSONObject a, JSONObject b) {
                                DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                                String valA = "";
                                String valB = "";
                                try {
                                    valA = (String) a.get("date");
                                    valB = (String) b.get("date");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    return f.parse(valA).compareTo(f.parse(valB));
                                } catch (ParseException e) {
                                    throw new IllegalArgumentException(e);
                                }
                            }
                        });
                        Collections.reverse(jsonList);
                        for (int i = 0; i < response.length(); i++) {
                            sortedJsonArray.put(jsonList.get(i));
                        }

                        try {

                            type1.setText(sortedJsonArray.getJSONObject(0).getString("type"));
                            money1.setText(sortedJsonArray.getJSONObject(0).getString("amount"));
                            if (sortedJsonArray.getJSONObject(0).getString("type").equals("Ingreso")) {
                                saldo1 = Double.valueOf(sortedJsonArray.getJSONObject(0).getString("amount"));
                            } else {
                                saldo1 = -Double.valueOf(sortedJsonArray.getJSONObject(0).getString("amount"));
                            }

                            type2.setText(sortedJsonArray.getJSONObject(1).getString("type"));
                            money2.setText(sortedJsonArray.getJSONObject(1).getString("amount"));
                            if (sortedJsonArray.getJSONObject(1).getString("type").equals("Ingreso")) {
                                saldo2 = Double.valueOf(sortedJsonArray.getJSONObject(1).getString("amount"));
                            } else {
                                saldo2 = -Double.valueOf(sortedJsonArray.getJSONObject(1).getString("amount"));
                            }

                            type3.setText(sortedJsonArray.getJSONObject(2).getString("type"));
                            money3.setText(sortedJsonArray.getJSONObject(2).getString("amount"));
                            if (sortedJsonArray.getJSONObject(2).getString("type").equals("Ingreso")) {
                                saldo3 = Double.valueOf(sortedJsonArray.getJSONObject(2).getString("amount"));
                            } else {
                                saldo3 = -Double.valueOf(sortedJsonArray.getJSONObject(2).getString("amount"));
                            }

                            type4.setText(sortedJsonArray.getJSONObject(3).getString("type"));
                            money4.setText(sortedJsonArray.getJSONObject(3).getString("amount"));
                            if (sortedJsonArray.getJSONObject(3).getString("type").equals("Ingreso")) {
                                saldo4 = Double.valueOf(sortedJsonArray.getJSONObject(3).getString("amount"));
                            } else {
                                saldo4 = -Double.valueOf(sortedJsonArray.getJSONObject(0).getString("amount"));
                            }

                            type5.setText(sortedJsonArray.getJSONObject(4).getString("type"));
                            money5.setText(sortedJsonArray.getJSONObject(4).getString("amount"));
                            if (sortedJsonArray.getJSONObject(4).getString("type").equals("Ingreso")) {
                                saldo5 = Double.valueOf(sortedJsonArray.getJSONObject(4).getString("amount"));
                            } else {
                                saldo5 = -Double.valueOf(sortedJsonArray.getJSONObject(4).getString("amount"));
                            }

                            type6.setText(sortedJsonArray.getJSONObject(5).getString("type"));
                            money6.setText(sortedJsonArray.getJSONObject(5).getString("amount"));
                            if (sortedJsonArray.getJSONObject(5).getString("type").equals("Ingreso")) {
                                saldo6 = Double.valueOf(sortedJsonArray.getJSONObject(5).getString("amount"));
                            } else {
                                saldo6 = -Double.valueOf(sortedJsonArray.getJSONObject(5).getString("amount"));
                            }

                            type7.setText(sortedJsonArray.getJSONObject(6).getString("type"));
                            money7.setText(sortedJsonArray.getJSONObject(6).getString("amount"));
                            if (sortedJsonArray.getJSONObject(6).getString("type").equals("Ingreso")) {
                                saldo7 = Double.valueOf(sortedJsonArray.getJSONObject(6).getString("amount"));
                            } else {
                                saldo7 = -Double.valueOf(sortedJsonArray.getJSONObject(6).getString("amount"));
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
