package com.fjgp.parcialguevara_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener{
    EditText txtnombres,txtapellidos,txtemail,txtpassword,txtfacultad,txtcarrera,txttelefono;
    Button btnregistrar,btnregresar;
    ArrayList<Usuario> listaregistrada;
    String nombres,apellidos,correo,telefono,facultad,carrera,password;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        txtnombres=(EditText) findViewById(R.id.txt_nombres);
        txtapellidos=(EditText) findViewById(R.id.txt_apellidos);
        txtemail=(EditText) findViewById(R.id.txt_email);
        txtfacultad=(EditText) findViewById(R.id.txt_facultad);
        txtcarrera=(EditText) findViewById(R.id.txt_Carrera);
        txttelefono=(EditText) findViewById(R.id.txt_telefono);
        txtpassword=(EditText) findViewById(R.id.txt_password);
        btnregistrar=(Button)findViewById(R.id.btn_registrar);
        btnregresar=(Button)findViewById(R.id.btn_regresarregistro);
        btnregistrar.setOnClickListener(this);
        btnregresar.setOnClickListener(this);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombres=txtnombres.getText().toString();
                apellidos=txtapellidos.getText().toString();
                correo=txtemail.getText().toString();
                telefono=txttelefono.getText().toString();
                facultad=txtfacultad.getText().toString();
                carrera=txtcarrera.getText().toString();
                password=txtpassword.getText().toString();
                if(!nombres.isEmpty()&&!apellidos.isEmpty()&&!correo.isEmpty()&&!password.isEmpty()
                        &&!telefono.isEmpty()&&!facultad.isEmpty()&&!carrera.isEmpty()){
                    if(password.length()>=6){
                        registrar();
                    }else{
                        Toast.makeText(RegistrarActivity.this, "El password debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegistrarActivity.this, "Debe Ingresar todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void registrar(){
        mAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mAuth.signInWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser usuariofirebase=FirebaseAuth.getInstance().getCurrentUser();
                                if(!usuariofirebase.isEmailVerified()){
                                    usuariofirebase.sendEmailVerification();
                                }

                            }
                        }
                    });
                    Usuario objcalculado=new Usuario();

                    objcalculado.setEmail(correo);
                    objcalculado.setNombres(nombres);
                    objcalculado.setApellidos(apellidos);
                    objcalculado.setFacultad(facultad);
                    objcalculado.setCarrera(carrera);
                    objcalculado.setTelefono(telefono);
                    objcalculado.setContrasena(password);
                    String id= mAuth.getCurrentUser().getUid();
                    mDatabase.child("Usuario").child(id).setValue(objcalculado).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2){
                            if(task2.isSuccessful()){
                                Toast.makeText(RegistrarActivity.this, "Revise su correo para verificar su cuenta",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrarActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(RegistrarActivity.this, "No se pudieron agregar los datos correctamente",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegistrarActivity.this, "No se pudo registrar este usuario",Toast.LENGTH_SHORT).show();
                }
            }
        });


        txtnombres.setText("");
        txtemail.setText("");
        txtapellidos.setText("");
        txtpassword.setText("");
        txtfacultad.setText("");
        txtcarrera.setText("");
        txttelefono.setText("");

    }



    public void regresar(){
        Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v==btnregistrar){
            registrar();
        } else if  (v==btnregresar){
            regresar();
        }
    }
}