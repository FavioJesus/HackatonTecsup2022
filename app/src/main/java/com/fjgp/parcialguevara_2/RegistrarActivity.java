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

import com.fjgp.parcialguevara_2.profesor.Profesor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtnombres, txtapellidos, txtemail, txtpassword, txttelefono;
    Button btnregistrar, btnregresar;
    String nombres, apellidos, correo, telefono, password;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        txtnombres = (EditText) findViewById(R.id.txt_nombres);
        txtapellidos = (EditText) findViewById(R.id.txt_apellidos);
        txtemail = (EditText) findViewById(R.id.txt_email);
        txttelefono = (EditText) findViewById(R.id.txt_telefono);
        txtpassword = (EditText) findViewById(R.id.txt_password);
        btnregistrar = (Button) findViewById(R.id.btn_registrar);
        btnregresar = (Button) findViewById(R.id.btn_regresarregistro);
        btnregistrar.setOnClickListener(this);
        btnregresar.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombres = txtnombres.getText().toString();
                apellidos = txtapellidos.getText().toString();
                correo = txtemail.getText().toString();
                telefono = txttelefono.getText().toString();
                password = txtpassword.getText().toString();

                if (!nombres.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !password.isEmpty() && !telefono.isEmpty()) {
                    if (password.length() >= 6) {
                        registrar();
                    } else {
                        Toast.makeText(RegistrarActivity.this, "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegistrarActivity.this, "Debe Ingresar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void registrar() {
        mAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser usuariofirebase = FirebaseAuth.getInstance().getCurrentUser();
                                usuariofirebase.sendEmailVerification();
                            }
                        }
                    });

                    Profesor profe = new Profesor();
                    profe.setEmail(correo);
                    profe.setNombres(nombres);
                    profe.setApellidos(apellidos);
                    profe.setTelefono(telefono);
                    profe.setContrasena(password);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Profesores").child(id).setValue(profe).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(RegistrarActivity.this, "Revise su correo para verificar su cuenta", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrarActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(RegistrarActivity.this, "No se pudieron guardar sus datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegistrarActivity.this, "No se pudo registrar este profesor", Toast.LENGTH_SHORT).show();
                    Log.e("Firebase auth", task.getException().toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnregistrar) {
            registrar();
        } else if (v == btnregresar) {
            finish();
        }
    }

    public void regresar(View view) {
        finish();
    }
}