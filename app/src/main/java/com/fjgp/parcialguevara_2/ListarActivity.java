package com.fjgp.parcialguevara_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnregresar;
    ArrayList<Usuario> listaregistrada;
    ListView listVHistorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        btnregresar=findViewById(R.id.btn_regresarlistar);
        btnregresar.setOnClickListener(this);
        listVHistorial=(ListView)findViewById(R.id.listview_usuarios);
        Intent objIntent = getIntent();
        Bundle objBundle = objIntent.getExtras();
        if(objBundle != null){
            ArrayList<Usuario> listarecibida = (ArrayList<Usuario>) objBundle.getSerializable("datos");
            listaregistrada = listarecibida;
        }else{
            listaregistrada = new ArrayList<Usuario>();
        }
        MyAdapter myAdapter=new MyAdapter(this,R.layout.historialusuarios,listaregistrada);
        listVHistorial.setAdapter(myAdapter);



    }

    public void regresar(){
        Intent intent = new Intent(ListarActivity.this, MainActivity.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        objBundle.putSerializable("datos",listaregistrada);
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if  (v==btnregresar){
            regresar();
        }
    }
}