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

import com.fjgp.parcialguevara_2.alumno.Alumno;
import com.fjgp.parcialguevara_2.alumno.AlumnoAdapter;
import com.fjgp.parcialguevara_2.curso.Curso;
import com.fjgp.parcialguevara_2.ml.PidmNotas;
import com.fjgp.parcialguevara_2.notas.Nota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.util.ArrayList;

public class perfil_curso extends AppCompatActivity {
    String userID;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String codigo_curso;
    private TextView nombre, codigo, horario;

    Button btn_agregar_alumno;

    //Lista de alumnos
    private RecyclerView recyclerView;
    private AlumnoAdapter alumnoAdapter;
    private ArrayList<Alumno> alumnos = new ArrayList<>();
    private ArrayList<Nota> notasalumno = new ArrayList<>();
    private AlumnoAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_curso);

        btn_agregar_alumno = (Button) findViewById(R.id.btn_agregar_alumno);
        recyclerView = (RecyclerView) findViewById(R.id.listaalumnos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        nombre = (TextView) findViewById(R.id.txt_nombrecurso);
        codigo = (TextView) findViewById(R.id.txt_codigocurso);
        horario = (TextView) findViewById(R.id.txt_horariocurso);

        user= FirebaseAuth.getInstance().getCurrentUser();
        codigo_curso =  getIntent().getStringExtra("codigo_curso");
        userID = user.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Usuario");

        reference.child(userID).child("Cursos").child(codigo_curso).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Curso curso=snapshot.getValue(Curso.class);
                if(curso!=null){
                    String codigo_curso  =curso.getCodigo();
                    String nombre_curso  =curso.getNombre();
                    String silabus_curso =curso.getSilabus();
                    String horario_curso =curso.getHorario();


                    nombre.setText(nombre_curso);
                    codigo.setText(codigo_curso);
                    horario.setText(horario_curso);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(PrincipalActivity.this, "Algo inesperado ocurrio",Toast.LENGTH_SHORT).show();
            }
        });

        //Lista de alumnos
        reference
                .child(userID)
                .child("Cursos")
                .child(codigo_curso)
                .child("alumnos")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                int colorrgb=1;
                                String codigo  = dataSnapshot.child("codigo").getValue().toString();
                                String nombre = dataSnapshot.child("nombre").getValue().toString();
                                String apellido  = dataSnapshot.child("apellido").getValue().toString();
                                String email = dataSnapshot.child("email").getValue().toString();
                                String nota1  = dataSnapshot.child("nt1").getValue().toString();
                                String nota2 = dataSnapshot.child("nt2").getValue().toString();
                                String nota3  = dataSnapshot.child("nt3").getValue().toString();
                                String nota4 = dataSnapshot.child("nt4").getValue().toString();
                                String examenparcial = dataSnapshot.child("ntparcial").getValue().toString();
                                Float nota1fl=Float.parseFloat(nota1);

                                Float nota2fl=Float.parseFloat(nota2);
                                Float nota3fl=Float.parseFloat(nota3);
                                Float nota4fl=Float.parseFloat(nota4);
                                Float notaparcialfl=Float.parseFloat(examenparcial);
                                float matriznotas[]={nota1fl,nota2fl,nota3fl,nota4fl,notaparcialfl};

                                try {
                                    PidmNotas model = PidmNotas.newInstance(perfil_curso.this);

                                    // Creates inputs for reference.
                                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 5}, DataType.FLOAT32);

                                    inputFeature0.loadArray(matriznotas);
                                    // Runs model inference and gets result.
                                    PidmNotas.Outputs outputs = model.process(inputFeature0);
                                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                                    float[] data1=outputFeature0.getFloatArray();

                                    if (data1[0]>=11){
                                        colorrgb=android.graphics.Color.rgb(183, 216, 129);

                                    }else {
                                        colorrgb=android.graphics.Color.rgb(255, 136, 119);
                                    }
                                    Alumno objcal=new Alumno();
                                    objcal.setCodigo(codigo);
                                    objcal.setNombre(nombre);
                                    objcal.setApellido(apellido);
                                    objcal.setEmail(email);
                                    objcal.setEvaluar(colorrgb);

                                    alumnos.add(objcal);


                                    // Releases model resources if no longer used.
                                } catch (IOException e) {
                                    Toast.makeText(perfil_curso.this, "Algo inesperado ocurrio",Toast.LENGTH_SHORT).show();
                                }




                            }
                            setOnClickListener();
                            alumnoAdapter = new AlumnoAdapter(alumnos, R.layout.activity_listar_alumno, perfil_curso.this, listener);
                            recyclerView.setAdapter(alumnoAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }

    //Boton para ir al registro de alumno
    int getMax(float[] arr){
        int max=0;
        for (int i=0;i<arr.length;i++){
            if(arr[i]>arr[max])max=i;
        }
        return max;
    }
    public void irRegistroAlumno(View view){
        //System.out.println("Click en agregar");
        Intent intent = new Intent(perfil_curso.this, registro_alumno.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        //objBundle.putSerializable("datos",listaregistrada);
        objBundle.putString("codigo_curso",codigo_curso);
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }

    private void setOnClickListener(){
        listener = new AlumnoAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                System.out.println("Click en detalle curso");
                Intent intent = new Intent(perfil_curso.this, registro_nota_alumno.class);
                Bundle objBundle = new Bundle();
                //System.out.println(listaregistrada);
                objBundle.putString("codigo_alumno",alumnos.get(position).getCodigo());
                objBundle.putString("nombres_alumno",alumnos.get(position).getNombre());
                objBundle.putString("apellidos_alumno",alumnos.get(position).getApellido());
                objBundle.putString("correo_alumno",alumnos.get(position).getEmail());
                objBundle.putString("codigo_curso",codigo_curso );
                objBundle.putString("nombre_curso", nombre.getText().toString());
                intent.putExtras(objBundle);
                startActivity(intent);
                finish();
            }
        };
    }

    public void regresar(View view){
        Intent intent = new Intent(perfil_curso.this, PrincipalActivity.class);
        //Bundle ObjBundle = new Bundle();
        //ObjBundle.putString("codigo_curso",codigo_curso);
        //intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }




}