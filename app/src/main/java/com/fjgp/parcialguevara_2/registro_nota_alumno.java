package com.fjgp.parcialguevara_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fjgp.parcialguevara_2.alumno.Alumno;
import com.fjgp.parcialguevara_2.curso.Curso;
import com.fjgp.parcialguevara_2.notas.Nota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registro_nota_alumno extends AppCompatActivity {

    String userID;
    DatabaseReference referencia;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String codigo_curso, codigo_alumno, nombre_curso,correo_alumno,apellidos_alumno,nombres_alumno;
    private TextView nombre, apellido, codigo, curso;

    private EditText nota1, nota2, nota3, nota4, notaParcial, notaFinal;

    TextView prom_p, prom_f;
    Button btn_guardar, btn_calcular_pp, btn_calcular_prom_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_nota_alumno);

        //completar información de cabecera

        nombre = (TextView) findViewById(R.id.txt_nombreAlumno1);
        apellido = (TextView) findViewById(R.id.txt_apellidoalumno1);
        codigo = (TextView) findViewById(R.id.txt_codigoalumno1);
        curso = (TextView) findViewById(R.id.txt_nombrecurso1);

        user= FirebaseAuth.getInstance().getCurrentUser();
        codigo_curso =  getIntent().getStringExtra("codigo_curso");
        nombres_alumno =  getIntent().getStringExtra("nombres_alumno");
        apellidos_alumno =  getIntent().getStringExtra("apellidos_alumno");
        correo_alumno =  getIntent().getStringExtra("correo_alumno");

        codigo_alumno = getIntent().getStringExtra("codigo_alumno");
        nombre_curso  = getIntent().getStringExtra("nombre_curso");
        userID = user.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Usuario");


        nota1 = (EditText)findViewById(R.id.txt_nota1);
        nota2 = (EditText)findViewById(R.id.txt_nota2);
        nota3 = (EditText)findViewById(R.id.txt_nota3);
        nota4 = (EditText)findViewById(R.id.txt_nota4);
        btn_guardar = (Button)findViewById(R.id.btn_guardar);
        btn_calcular_pp = (Button) findViewById(R.id.btn_calcularPP);
        btn_calcular_prom_final = (Button) findViewById(R.id.btn_CalcularProm);


        prom_p = (TextView) findViewById(R.id.txt_pp);
        notaParcial = (EditText)findViewById(R.id.txt_parcial);
        notaFinal = (EditText)findViewById(R.id.txt_final) ;

        prom_f = (TextView) findViewById(R.id.txt_promedio);





        reference.child(userID).child("Cursos").child(codigo_curso).child("alumnos").child(codigo_alumno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Alumno alumnos=snapshot.getValue(Alumno.class);
                if(alumnos!=null){
                    String codigo_alumno  =alumnos.getCodigo();
                    String nombre_alumno  =alumnos.getNombre();
                    String apellido_alumno =alumnos.getApellido();
                    String email_alumno =alumnos.getEmail();
                    String nota1b=alumnos.getNt1();
                    String nota2b=alumnos.getNt2();
                    String nota3b=alumnos.getNt3();
                    String nota4b=alumnos.getNt4();
                    String notapp=alumnos.getPp();
                    String notaep=alumnos.getNtparcial();
                    String notaef=alumnos.getNtfinal();
                    String notafinal=alumnos.getPromedioFinal();

                    nombre.setText(nombre_alumno);
                    apellido.setText(apellido_alumno);
                    codigo.setText(codigo_alumno);
                    curso.setText(nombre_curso);
                    nota1.setText(nota1b);
                    nota2.setText(nota2b);
                    nota3.setText(nota3b);
                    nota4.setText(nota4b);
                    prom_p.setText(notapp);
                    notaParcial.setText(notaep);
                    notaFinal.setText(notaef);
                    prom_f.setText(notafinal);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(PrincipalActivity.this, "Algo inesperado ocurrio",Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void registrarNotas(View view){



        String nota_1 = nota1.getText().toString();
        String nota_2 = nota2.getText().toString();
        String nota_3 = nota3.getText().toString();
        String nota_4 = nota4.getText().toString();
        String nota_pp = prom_p.getText().toString();
        String nota_parcial = notaParcial.getText().toString();
        String nota_final = notaFinal.getText().toString();
        String nota_promedio = prom_f.getText().toString();

        Alumno alumnoobj=new Alumno();
        alumnoobj.setCodigo(codigo_alumno);
        alumnoobj.setNombre(nombres_alumno);
        alumnoobj.setApellido(apellidos_alumno);
        alumnoobj.setEmail(correo_alumno);
        alumnoobj.setEvaluar(1);
        alumnoobj.setNt1(nota_1);
        alumnoobj.setNt2(nota_2);
        alumnoobj.setNt3(nota_3);
        alumnoobj.setNt4(nota_4);
        alumnoobj.setPp(nota_pp);
        alumnoobj.setNtparcial(nota_parcial);
        alumnoobj.setNtfinal(nota_final);
        alumnoobj.setPromedioFinal(nota_promedio);


        reference.child(userID).child("Cursos").child(codigo_curso).child("alumnos").child(codigo_alumno).setValue(alumnoobj);
        Intent intent = new Intent(registro_nota_alumno.this, perfil_curso.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        //objBundle.putSerializable("datos",listaregistrada);
        objBundle.putString("codigo_curso",codigo_curso);
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();


    }








    public void calcularP(View view){
        double n1,n2,n3,n4;
        double pp;
            n1 = Float.parseFloat(nota1.getText().toString());
            n2 = Float.parseFloat(nota2.getText().toString());
            n3 = Float.parseFloat(nota3.getText().toString());
            n4 = Float.parseFloat(nota4.getText().toString());

            pp = (n1 + n2 + n3 + n4)/ 4;
            prom_p.setText(String.valueOf(Math.round(pp*100.0)/100.0));
    }

    public void calcularPromedioFinal(View view){
        double n5,n6, n7;
        double pf;
        n5 = Float.parseFloat(prom_p.getText().toString());
        n6 = Float.parseFloat(notaParcial.getText().toString());
        n7 = Float.parseFloat(notaFinal.getText().toString());

        pf = (n5*0.4 + n6*0.3 + n7*0.3) ;
        prom_f.setText(String.valueOf(Math.round(pf*100.0)/100.0));
    }

    public void regresar(View view){
        Intent intent = new Intent(registro_nota_alumno.this, perfil_curso.class);
        startActivity(intent);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        //objBundle.putSerializable("datos",listaregistrada);
        objBundle.putString("codigo_curso",codigo_curso);
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }


}


