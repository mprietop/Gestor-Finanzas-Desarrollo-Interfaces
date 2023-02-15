package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import static java.security.AccessController.getContext;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.ElementsAdapter;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.Rest;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.Transactions;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

    private View view;
    private RequestQueue queue;
    private Context context;
    private RecyclerView recyclerView;
    private History history = new History();
    List<Transactions> transactions = new ArrayList<>();

    GraphView graphView;

    public static Stats newInstance() {
        Stats fragment = new Stats();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stats_screen, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHistorial);
        context = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        int position, id, cantidad;

        try {
            position = ((ElementsAdapter) recyclerView.getAdapter()).getPosition();
            id = ((ElementsAdapter) recyclerView.getAdapter()).getId(position);
            cantidad = Integer.parseInt(((ElementsAdapter) recyclerView.getAdapter()).getCantidad(position));
        } catch (Exception e) {
            e.printStackTrace();
        }

        init(id, cantidad, position);
    }

    public void init(int id, int cantidad, int position) {
        graphView = view.findViewById(R.id.idGraphView);

        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        int sessionToken = preferences.getInt("userId", -1);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Rest.getBASE_URL() + "users/" + sessionToken + "/transactions" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LineGraphSeries<DataPoint> transacciones = new LineGraphSeries<DataPoint>(new DataPoint[]{
                                new DataPoint(position, cantidad)
                        });

                        graphView.setTitle("MIS TRANSACCIONES");
                        graphView.setTitleColor(R.color.purple_200);
                        graphView.setTitleTextSize(18);
                        graphView.addSeries(transacciones);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Pablo", error.toString());
                    }
                }
        );
        queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
        /*super.onViewCreated(view, savedInstanceState);
        graphView = view.findViewById(R.id.idGraphView);

        SharedPreferences prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Rest.getBASE_URL() + "users/" + userId + "/transactions",
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
    }*/

}
