package com.fjgp.parcialguevara_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fjgp.parcialguevara_2.curso.Curso;
import com.fjgp.parcialguevara_2.curso.CursoAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Usuario> listaregistrada;
    Button btnregresar, btn_agregar_curso;
    String userID;
    private FirebaseUser user;
    private DatabaseReference reference;
    TextView usuariologeado;

    //Lista de cursos
    private RecyclerView recyclerView;
    private CursoAdapter cursoAdapter;
    private ArrayList<Curso> cursos = new ArrayList<>();

    private CursoAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnregresar=(Button)findViewById(R.id.btn_regresarprincipal);
        btn_agregar_curso=(Button)findViewById(R.id.btn_agregar_curso);
        usuariologeado=(TextView)findViewById(R.id.txt_usuariologeado);

        recyclerView = (RecyclerView) findViewById(R.id.listacursos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnregresar.setOnClickListener(this);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Usuario");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuProfile=snapshot.getValue(Usuario.class);
                if(usuProfile!=null){
                    String nombres=usuProfile.nombres;
                    String apellidos=usuProfile.apellidos;
                    String correo=usuProfile.email;
                    String facultad=usuProfile.facultad;
                    String carrera=usuProfile.carrera;
                    String telefono=usuProfile.telefono;

                    usuariologeado.setText(nombres);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PrincipalActivity.this, "Algo inesperado ocurrio",Toast.LENGTH_SHORT).show();
            }
        });

        //Lista de cursos
        reference
                .child(userID)
                .child("Cursos")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String nombre  = dataSnapshot.child("nombre").getValue().toString();
                                String carrera = dataSnapshot.child("carrera").getValue().toString();
                                String codigo  = dataSnapshot.child("codigo").getValue().toString();
                                String horario = dataSnapshot.child("horario").getValue().toString();
                                String silabu  = dataSnapshot.child("silabus").getValue().toString();
                                cursos.add(
                                        new Curso(
                                                nombre,
                                                horario,
                                                codigo,
                                                silabu,
                                                carrera
                                        )
                                );
                                System.out.println(cursos);
                            }
                            setOnClickListener();
                            cursoAdapter = new CursoAdapter(cursos, R.layout.activity_listar_curso, PrincipalActivity.this,listener );
                            recyclerView.setAdapter(cursoAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //esto es un comentario
    }

    public void regresar(){
        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        objBundle.putSerializable("datos",listaregistrada);
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }

    //Boton para ir al registro de curso
    public void irRegistroCurso(View view){
        System.out.println("Click en agregar");
        Intent intent = new Intent(PrincipalActivity.this, registrar_curso.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        objBundle.putSerializable("datos",listaregistrada);
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }

    public void irRegistroInputs(View view) {
        System.out.println("Click en agregar data/input");
        Intent intent = new Intent(PrincipalActivity.this, RegistrarInputs.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if  (v==btnregresar){
            regresar();
        }
    }

    private void setOnClickListener(){
        listener = new CursoAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                System.out.println("Click en detalle curso");
                Intent intent = new Intent(PrincipalActivity.this, perfil_curso.class);
                Bundle objBundle = new Bundle();
                //System.out.println(listaregistrada);
                objBundle.putString("codigo_curso",cursos.get(position).getCodigo());
                intent.putExtras(objBundle);
                startActivity(intent);
                finish();
            }
        };
    }

}