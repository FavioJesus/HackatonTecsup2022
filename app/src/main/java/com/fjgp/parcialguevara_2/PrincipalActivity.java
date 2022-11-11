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

import com.fjgp.parcialguevara_2.aula.Aula;
import com.fjgp.parcialguevara_2.aula.AulaAdapter;
import com.fjgp.parcialguevara_2.profesor.Profesor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity{
    private ArrayList<Aula> aulas = new ArrayList<>();
    String userID;
    TextView tv_profesor;
    private DatabaseReference profesor_reference;

    private RecyclerView recyclerView;
    private AulaAdapter aulaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tv_profesor = (TextView) findViewById(R.id.txt_usuariologeado);

        recyclerView = (RecyclerView) findViewById(R.id.listacursos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aulaAdapter = new AulaAdapter(PrincipalActivity.this);
        recyclerView.setAdapter(aulaAdapter);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        profesor_reference = FirebaseDatabase.getInstance().getReference()
                .child("Profesores")
                .child(userID);

        profesor_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Profesor profesor = snapshot.getValue(Profesor.class);
                    String nombres = profesor.getNombres();
                    String apellidos = profesor.getApellidos();

                    tv_profesor.setText(nombres + " " + apellidos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PrincipalActivity.this, "Algo inesperado ocurrio", Toast.LENGTH_SHORT).show();
            }
        });

        profesor_reference.child("Aulas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    aulas = new ArrayList<>();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Aula aula = dataSnapshot.getValue(Aula.class);
                        aulas.add(aula);
                    }

                    aulaAdapter.setAulas(aulas);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    public void agregarAula(View view) {
        Intent intent = new Intent(PrincipalActivity.this, RegistrarAula.class);
        startActivity(intent);
    }

    public void regresar(View view) {
        finish();
    }
}