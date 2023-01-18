package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.os.Bundle;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TransactionScreen extends AppCompatActivity {
private EditText fecha;
private EditText descripción;
private EditText cantidad;
private Button submitButton ;
private Spinner spinner;
private RequestQueue requestQueue;
private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_screen);
        Spinner spinnertransaction=findViewById(R.id.spinner_transaction);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.transaction, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnertransaction.setAdapter(adapter);
        submitButton = findViewById(R.id.submit);
        descripción = findViewById(R.id.Descripcion);
        cantidad = findViewById(R.id.Cantidad);
        fecha = findViewById(R.id.Fecha);
        spinner = findViewById(R.id.spinner_transaction);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Nueva transacción añadida", Toast.LENGTH_SHORT).show();
                newtransaction();
            }
        });

        requestQueue= Volley.newRequestQueue(this);
    }
    private void newtransaction(){
        JSONObject requestBody = new JSONObject();
        //SharedPreferences prefs =getSharedPreferences("user", Context.MODE_PRIVATE);
        try {
          //  requestBody.put("userId",prefs.getString("userId",null));
            requestBody.put("amount", cantidad.getText().toString());
            requestBody.put("description", descripción.getText().toString());
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
                        Toast.makeText(context, "Transacción creada con éxito", Toast.LENGTH_SHORT).show();
                        finish();
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