package com.fjgp.parcialguevara_2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fjgp.parcialguevara_2.alumno.Alumno;
import com.fjgp.parcialguevara_2.alumno.AlumnoAdapter;
import com.fjgp.parcialguevara_2.aula.Aula;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.util.ArrayList;

public class PerfilAula extends AppCompatActivity {
    private ArrayList<Alumno> alumnos = new ArrayList<>();

    String userID;
    private DatabaseReference aula_reference;
    public String aula_id;
    private TextView tv_aula_id, tv_aula_anyo, tv_aula_grado, tv_aula_seccion;

    private RecyclerView recyclerView;
    private AlumnoAdapter alumnoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_aula);

        recyclerView = findViewById(R.id.listaalumnos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        alumnoAdapter = new AlumnoAdapter(PerfilAula.this);
        recyclerView.setAdapter(alumnoAdapter);

        aula_id = getIntent().getStringExtra("aula_id");
        tv_aula_id = findViewById(R.id.tv_aula_id);
        tv_aula_id.setText(aula_id);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        aula_reference = FirebaseDatabase.getInstance().getReference()
                .child("Profesores")
                .child(userID)
                .child("Aulas")
                .child(aula_id);

        aula_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Aula aula = snapshot.getValue(Aula.class);

                    tv_aula_anyo = findViewById(R.id.tv_aula_anyo);
                    tv_aula_grado = findViewById(R.id.tv_aula_grado);
                    tv_aula_seccion = findViewById(R.id.tv_aula_seccion);

                    tv_aula_anyo.setText(aula.getAnyo());
                    tv_aula_grado.setText(aula.getGrado());
                    tv_aula_seccion.setText(aula.getSeccion());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PerfilAula.this, "Algo inesperado ocurrio", Toast.LENGTH_SHORT).show();
            }
        });

        aula_reference.child("Alumnos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    alumnos = new ArrayList<>();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Alumno alumno = dataSnapshot.getValue(Alumno.class);
                        alumnos.add(alumno);
                    }

                    alumnoAdapter.setAlumnos(alumnos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    public void agregarAlumno(View view) {
        Intent intent = new Intent(PerfilAula.this, RegistrarAlumno.class);
        intent.putExtra("aula_id", aula_id);
        startActivity(intent);
    }

    public void regresar(View view) {
        finish();
    }
}