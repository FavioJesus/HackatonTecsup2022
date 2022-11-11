package com.fjgp.parcialguevara_2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fjgp.parcialguevara_2.alumno.Alumno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarAlumno extends AppCompatActivity {

    DatabaseReference alumnos_reference;
    private EditText et_codigo, et_nombre, et_apellido;
    private String aulaId;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_alumno);

        et_codigo = (EditText) findViewById(R.id.txt_CodigoAlumno);
        et_nombre = (EditText) findViewById(R.id.txt_NombreAlumno);
        et_apellido = (EditText) findViewById(R.id.txt_ApellidoAlumno);

        aulaId = getIntent().getStringExtra("aula_id");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        alumnos_reference = FirebaseDatabase.getInstance().getReference()
                .child("Profesores")
                .child(userId)
                .child("Aulas")
                .child(aulaId)
                .child("Alumnos");
    }

    public void registrarAlumno(View view) {
        String codigo = et_codigo.getText().toString();
        String nombres = et_nombre.getText().toString();
        String apellidos = et_apellido.getText().toString();

        if (!(codigo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty())) {
            Alumno alumno = new Alumno();
            alumno.setCodigo(codigo);
            alumno.setNombre(nombres);
            alumno.setApellido(apellidos);

            alumnos_reference.child(codigo).setValue(alumno);
            finish();
        } else {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}