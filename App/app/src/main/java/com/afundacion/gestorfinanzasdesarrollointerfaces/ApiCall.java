package com.afundacion.gestorfinanzasdesarrollointerfaces;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ApiCall {
    private Context mContext;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    public ApiCall(Context context) {
        mContext = context;
    }

    public void makeApiCall(String url, final TextView textView) {
        mRequestQueue = Volley.newRequestQueue(mContext);

        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Error: " + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}