package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Utils.DatePickerFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormatSymbols;
import java.util.Calendar;

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
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        fecha.setText(twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year);
        cantidad.setFilters(new InputFilter[]{new InputFilter() {

            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                int indexPoint = dest.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());

                if (indexPoint == -1)
                    return source;

                int decimals = dend - (indexPoint+1);
                return decimals < 2 ? source : "";
            }
        }
        });

        submitButton = view.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (cantidad.length() == 0){
                    cantidad.setError("Falta Cantidad");
                }
                if (descripcion.length() == 0){
                    descripcion.setError("Falta descripción");
                }
                if (fecha.length() == 0){
                    fecha.setError("Falta fecha");
                }
                if (cantidad.getError() == null && descripcion.getError() == null && fecha.getError() == null){
                    newTransaction();
                }

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
                        cantidad.setText("");
                        descripcion.setText("");
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        fecha.setText(twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year);
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
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year ;
                fecha.setText(selectedDate);
            }
        });
        //Cuando sea un fragment antes del getSupport hay que poner un getActivity()
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}


