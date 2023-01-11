package com.afundacion.gestorfinanzasdesarrollointerfaces.Launcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzasdesarrollointerfaces.MainActivity;


public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Launcher.this, MainActivity.class);
        startActivity(intent);
    }
}
