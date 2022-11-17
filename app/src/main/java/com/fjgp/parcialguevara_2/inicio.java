package com.fjgp.parcialguevara_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class inicio extends AppCompatActivity implements View.OnClickListener {
    Button btnIngresar,btnRegistrarseInicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btnIngresar = (Button) findViewById(R.id.button);
        btnRegistrarseInicio = (Button) findViewById(R.id.button3);
        btnIngresar.setOnClickListener(this);
        btnRegistrarseInicio.setOnClickListener(this);
    }

    public void IngresarAlApp() {
        Intent intent = new Intent(inicio.this, MainActivity.class);
        startActivity(intent);
    }

    public void RegistrarseEnElApp() {
        Intent intent = new Intent(inicio.this, RegistrarActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == btnIngresar) {
            IngresarAlApp();
        } else if (v == btnRegistrarseInicio) {
            RegistrarseEnElApp();
        }
    }

}