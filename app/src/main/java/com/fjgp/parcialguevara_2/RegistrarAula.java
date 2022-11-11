package com.fjgp.parcialguevara_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fjgp.parcialguevara_2.aula.Aula;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarAula extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference referencia;
    private EditText et_grado, et_seccion, et_anyo;
    private Button btn_registrar;
    private String userID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_aula);
        et_grado = (EditText) findViewById(R.id.txt_grado);
        et_seccion = (EditText) findViewById(R.id.txt_seccion);
        et_anyo = (EditText) findViewById(R.id.txt_anyo);

        btn_registrar = (Button) findViewById(R.id.btn_registrar_alumno);
        btn_registrar.setOnClickListener(this);

        referencia = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
    }

    public void registrarCurso() {
        String grado = et_grado.getText().toString();
        String seccion = et_seccion.getText().toString();
        String anyo = et_anyo.getText().toString();
        
        if (!(grado.isEmpty() || seccion.isEmpty() || anyo.isEmpty())) {
            Aula aula = new Aula(grado, seccion, anyo);
            String id = grado + seccion + anyo;
            referencia.child("Profesores").child(userID).child("Aulas").child(id).setValue(aula).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Log.e("Registrar curso", task.getException().toString());
                    }
                }
            });

            finish();
        } else {
            Toast.makeText(this, "Completar todos los campos!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_registrar) {
            registrarCurso();
        }
    }
}