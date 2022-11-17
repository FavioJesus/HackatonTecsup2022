package com.fjgp.parcialguevara_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListarCurso extends AppCompatActivity {

    private TextView codigo;
    private ImageView detalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_aula);

        codigo = (TextView) findViewById(R.id.codigo_curso);
        detalle = (ImageView) findViewById(R.id.detalle1);

    }

    public void detalleCurso() {
        System.out.println("Click en detalle curso");
        Intent intent = new Intent(ListarCurso.this, PerfilAula.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        objBundle.putString("codigo_curso", codigo.getText().toString());
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }
}