package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import static java.security.AccessController.getContext;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Stats extends Fragment {

    private RequestQueue queue;
    private Context context;

    GraphView graphView;

    public static Stats newInstance(){
        Stats fragment = new Stats();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stats_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        graphView = view.findViewById(R.id.idGraphView);

        SharedPreferences prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://63c7b7205c0760f69abc6591.mockapi.io/api/users/"+ userId +"/transactions",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            int numOfBars = jsonArray.length();
                            DataPoint[] dataPoints = new DataPoint[numOfBars];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int duration = jsonObject.getInt("duration");
                                String start_date = jsonObject.getString("start-date");
                                dataPoints[i] = new DataPoint(i, duration);
                            }

                            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
                            graphView.setTitle("My Graph View");

                            graphView.setTitleColor(R.color.purple_200);

                            graphView.setTitleTextSize(18);
                            graphView.addSeries(series);

                            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                                @Override
                                public int get(DataPoint data) {
                                    return Color.rgb((int) data.getX()*255/4, (int) data.getY()*255/6, 100);
                                }
                            });

                            series.setSpacing(10);
                            series.setDrawValuesOnTop(true);
                            series.setValuesOnTopColor(Color.BLUE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error
                    }
                });

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(stringRequest);
    }

}
