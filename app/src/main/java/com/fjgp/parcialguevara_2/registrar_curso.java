package com.fjgp.parcialguevara_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fjgp.parcialguevara_2.curso.Curso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrar_curso extends AppCompatActivity implements View.OnClickListener {

    private EditText nombre, horario, codigo, silabus, carrera;
    private Button btn_registrar;
    DatabaseReference referencia;
    private DatabaseReference reference;
    private String userID;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_curso);
        nombre  = (EditText) findViewById(R.id.txt_Nombre);
        horario = (EditText) findViewById(R.id.txt_Horario);
        codigo  = (EditText) findViewById(R.id.txt_Codigo);
        silabus = (EditText) findViewById(R.id.txt_Sillabus);
        carrera = (EditText) findViewById(R.id.txt_Carrera);

        btn_registrar = (Button) findViewById(R.id.btn_registrar_alumno);
        referencia = FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Usuario");
        userID = user.getUid();
        btn_registrar.setOnClickListener(this);
    }

    public void registrarCurso(){
        if(validarCampo(nombre)){ return;}
        if(validarCampo(horario)){ return;}
        if(validarCampo(codigo)){ return;}
        if(validarCampo(silabus)){ return;}
        if(validarCampo(carrera)){ return;}

        String nombre_curso  = nombre.getText().toString();
        String horario_curso = horario.getText().toString();
        String codigo_curso  = codigo.getText().toString();
        String silabus_curso = silabus.getText().toString();
        String carrera_curso = carrera.getText().toString();

        Curso curso = new Curso(
                nombre_curso,
                horario_curso,
                codigo_curso,
                silabus_curso,
                carrera_curso
        );

        referencia.child("Usuario").child(userID).child("Cursos").child(codigo_curso).setValue(curso);
        regresar();

    }

    public boolean validarCampo(EditText campo){
        Boolean formularioIncorrecto = false;
        if(campo.getText().toString().equals("")){
            formularioIncorrecto = true;
        }
        return formularioIncorrecto;
    }

    @Override
    public void onClick(View v) {
        if  (v==btn_registrar){
            registrarCurso();
        }
    }

    public void regresar(){
        Intent intent = new Intent(registrar_curso.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }
}