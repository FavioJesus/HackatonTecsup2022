package com.fjgp.parcialguevara_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText et_usuario, et_contrasenya;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_contrasenya = (EditText) findViewById(R.id.et_contrasenya);

        mAuth = FirebaseAuth.getInstance();
    }

    public void registrar(View view) {
        Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
        startActivity(intent);
        finish();
    }

    public void ingresar(View view) {
        String correo = et_usuario.getText().toString().trim();
        String contrasenya = et_contrasenya.getText().toString().trim();

        if (correo.isEmpty()) {
            et_usuario.setError("Correo requerido!");
            et_usuario.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            et_usuario.setError("Ingrese un correo calido!");
            et_usuario.requestFocus();
            return;
        }
        if (contrasenya.isEmpty()) {
            et_contrasenya.setError("Contraseña requerida!");
            et_contrasenya.requestFocus();
            return;
        }
        if (contrasenya.length() < 6) {
            et_contrasenya.setError("6 caracteres minimo!");
            et_contrasenya.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(correo, contrasenya).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser.isEmailVerified()) {
                        startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Revise su email para la verificación", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error al intentar ingresar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void recuperar(View view) {
        Intent intent = new Intent(MainActivity.this, RecuperarActivity.class);
        startActivity(intent);
        finish();
    }
}