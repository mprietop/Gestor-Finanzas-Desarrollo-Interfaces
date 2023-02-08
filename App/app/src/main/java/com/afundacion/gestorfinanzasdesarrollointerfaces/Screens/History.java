package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.ElementsAdapter;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.Transactions;
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

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private Context context = this;
    private RequestQueue queue;
    private View view;
    private RecyclerView recyclerView;
    private TextView descripcion;
    private TextView cantidad;
    List<Transactions> transactions = new ArrayList<>();
    ElementsAdapter adapter = new ElementsAdapter(transactions);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_screen);
        queue = Volley.newRequestQueue(context);
        recyclerView = findViewById(R.id.recyclerViewHistorial);
        init();
    }


    public void init(){
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://63c7b7205c0760f69abc6591.mockapi.io/api/" + "1" + "/transactions",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ElementsAdapter elementsAdapter = new ElementsAdapter(fillList(response));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(elementsAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error accediendo a las transacciones", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );

        queue.add(request);
    }

    public List<Transactions> fillList(JSONArray array) throws JSONException {
        for (int i=0; i < array.length(); i++) {
            JSONObject jsonElement = array.getJSONObject(i);
            Transactions aTransactions = new Transactions(jsonElement.getString("description"),
                    jsonElement.getString("amount"), jsonElement.getString("type"),
                    jsonElement.getInt("id"));
            transactions.add(aTransactions);
        }
        return transactions;
    }




    //MENÚ CONTEXTUAL

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position, id;
        String cantidad, descripcion, type;
        try {
            position = ((ElementsAdapter) recyclerView.getAdapter()).getPosition();
            id = ((ElementsAdapter) recyclerView.getAdapter()).getId(position);
            cantidad = ((ElementsAdapter) recyclerView.getAdapter()).getCantidad(position);
            descripcion = ((ElementsAdapter) recyclerView.getAdapter()).getDescripcion(position);
            type = ((ElementsAdapter) recyclerView.getAdapter()).getType(position);
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.editar:
                Log.wtf("Pablo", item.toString());
                Toast.makeText(context, "1235622", Toast.LENGTH_SHORT).show();

                mostrarPantallaEditar(id, descripcion, cantidad, type);
                break;


            case R.id.eliminar:
                Log.wtf("Pablo", item.toString());
                Toast.makeText(context, "bdhdhdh", Toast.LENGTH_SHORT).show();

                eliminarTransaccion(id);

                break;
        }
        return super.onContextItemSelected(item);
    }

    public void eliminarTransaccion(int id) {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                "https://63c7b7205c0760f69abc6591.mockapi.io/api/" + "1" + "/transactions/" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Transacción eliminada", Toast.LENGTH_SHORT).show();
                        transactions.clear();
                        init();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Pablo", error.toString());
                    }
                }
        );

        queue.add(request);
    }

    public void mostrarPantallaEditar(int id, String descripcion, String cantidad, String type){
        AlertDialog.Builder ventana = new AlertDialog.Builder(this);
        ventana.setTitle("Editar transacción");

        View ventanaInflada = LayoutInflater.from(this).inflate(R.layout.edit_history, null);
        ventana.setView(ventanaInflada);

        EditText textDescripcion = ventanaInflada.findViewById(R.id.editDescripcionHistorial);
        textDescripcion.setText(descripcion);

        EditText textCantidad = ventanaInflada.findViewById(R.id.editCantidadHistorial);
        textCantidad.setText(cantidad);

        ventana.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String descripcion = textDescripcion.getText().toString().trim();
                String cantidad = textCantidad.getText().toString().trim();
                if(descripcion.isEmpty() || cantidad.isEmpty()){
                    Toast.makeText(History.this, "Los campos no pueden estar vacíos",
                            Toast.LENGTH_SHORT).show();
                }else{
                    editarTransaccion(id, descripcion, cantidad, type);
                }
            }
        });

        ventana.create().show();
    }

    public void editarTransaccion(int id, String descripcion, String cantidad, String type){


        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("description", descripcion);
            requestBody.put("amount", cantidad);
        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.wtf("Pablo", "bfjdnkemfknljnlslksfjknlsekwmdfnswk");
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                "https://63c7b7205c0760f69abc6591.mockapi.io/api/users/"+ "1"+"/transactions/" + id,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.elements_history);
                        TextView descripcionAntigua = findViewById(R.id.descripcionHistorial);
                        descripcionAntigua.setText(descripcion);
                        TextView cantidadAntigua = findViewById(R.id.cantidadHistorial);
                        cantidadAntigua.setText(cantidad);
                        if (type.equalsIgnoreCase("Ingreso")) {
                            cantidadAntigua.setTextColor(Color.parseColor("#4CAF50"));
                        }else if (type.equalsIgnoreCase("Gasto")) {
                            cantidadAntigua.setTextColor(Color.parseColor("#D30E00"));
                        }

                        transactions.clear();
                        init();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        this.queue.add(request);
    }
}
