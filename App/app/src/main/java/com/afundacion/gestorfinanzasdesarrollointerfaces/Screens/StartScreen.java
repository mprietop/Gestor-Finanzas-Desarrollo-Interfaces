package com.afundacion.gestorfinanzasdesarrollointerfaces.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.afundacion.gestorfinanzasdesarrollointerfaces.ApiCall;
import com.afundacion.gestorfinanzasdesarrollointerfaces.R;

public class StartScreen extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        ApiCall apiCall = new ApiCall(this);

        textView = findViewById(R.id.text_view);
        apiCall.makeApiCall("https://63c7b7205c0760f69abc6591.mockapi.io/api/transactions", textView);
    }
}