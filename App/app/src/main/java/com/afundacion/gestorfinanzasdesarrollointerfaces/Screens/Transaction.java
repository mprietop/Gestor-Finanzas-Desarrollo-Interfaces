package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction extends Fragment {

    private EditText fecha, descripcion, cantidad;
    private Button submitButton ;
    private Spinner spinner;
    private RequestQueue requestQueue;
    private Context context;

    public static Transaction newInstance() {
        Transaction fragment = new Transaction();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();

        View view = inflater.inflate(R.layout.activity_transaction_screen, container, false);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(context, R.array.transaction, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner = view.findViewById(R.id.spinner_transaction);
        spinner.setAdapter(adapter);

        descripcion = view.findViewById(R.id.Descripcion);
        cantidad = view.findViewById(R.id.Cantidad);
        fecha = view.findViewById(R.id.Fecha);

        submitButton = view.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                newTransaction();
            }
        });

        requestQueue = Volley.newRequestQueue(context);

        return view;
    }

    private void newTransaction(){
        JSONObject requestBody = new JSONObject();

        //SharedPreferences prefs =getSharedPreferences("user", Context.MODE_PRIVATE);
        try {

            //  requestBody.put("userId",prefs.getString("userId",null));
            requestBody.put("amount", cantidad.getText().toString());
            requestBody.put("description", descripcion.getText().toString());
            requestBody.put("date", fecha.getText().toString());
            requestBody.put("type", spinner.getSelectedItem().toString());

        }catch (JSONException e){

            throw new RuntimeException(e);

        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://63c7b7205c0760f69abc6591.mockapi.io/api/" + "/transactions",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Nueva transacción creada con éxito", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(context, "Sin conexión", Toast.LENGTH_LONG).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(context, "Código de respuesta "+serverCode, Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        this.requestQueue.add(request);
    }
}
