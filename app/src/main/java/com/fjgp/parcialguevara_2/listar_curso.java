package com.fjgp.parcialguevara_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class listar_curso extends AppCompatActivity {

    private TextView codigo;
    private ImageView detalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_curso);

        codigo = (TextView) findViewById(R.id.codigo_alumno);
        detalle = (ImageView) findViewById(R.id.detalle1);

    }

    public void detalleCurso(){
        System.out.println("Click en detalle curso");
        Intent intent = new Intent(listar_curso.this, perfil_curso.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        objBundle.putString("codigo_curso",codigo.getText().toString());
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }
}