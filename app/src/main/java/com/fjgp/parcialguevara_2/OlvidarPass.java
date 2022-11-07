package com.fjgp.parcialguevara_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class OlvidarPass extends AppCompatActivity {
    Button btn_regresar,btn_recuprear;
    EditText correorecuperar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_pass);
        correorecuperar=(EditText) findViewById(R.id.txt_correo_recuperar);
        btn_regresar=(Button) findViewById(R.id.btn_olvidar_regresar);
        btn_recuprear=(Button) findViewById(R.id.btn_recuperar_pass);
        mAuth=FirebaseAuth.getInstance();
        btn_recuprear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });
        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });

    }
    public void regresar(){
        Intent intent = new Intent(OlvidarPass.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void resetPass(){
        String email=correorecuperar.getText().toString().trim();
        if(email.isEmpty()){
            correorecuperar.setError("Correo Requerido");
            correorecuperar.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            correorecuperar.setError("Ingrese email valido");
            correorecuperar.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    correorecuperar.setText("");
                    Toast.makeText(OlvidarPass.this,"Revise su correo para resetear su contraseña", Toast.LENGTH_LONG).show();

                }else{
                    correorecuperar.requestFocus();
                    Toast.makeText(OlvidarPass.this,"Intente de nuevo! Algo inesperado ocurrio!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
