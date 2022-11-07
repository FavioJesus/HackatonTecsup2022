package com.fjgp.parcialguevara_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText txt_usuario;
    EditText txt_password;
    ArrayList<Usuario> listaregistrada;
    Button btnregistrar,btningresar;
    TextView recuperar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_usuario=(EditText) findViewById(R.id.txt_usuario);
        recuperar=(TextView)findViewById(R.id.signupText);
        txt_password=(EditText) findViewById(R.id.txt_pass);
        btnregistrar=(Button)findViewById(R.id.btn_registrar_principal);
        btningresar=(Button)findViewById(R.id.btn_ingresar);
        btnregistrar.setOnClickListener(this);
        btningresar.setOnClickListener(this);
        recuperar.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();


    }

    public void registrar(){
        Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        startActivity(intent);
        finish();
    }

    public void olvidepass(){
        Intent intent = new Intent(MainActivity.this, OlvidarPass.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        startActivity(intent);
        finish();
    }

    private void userLogin(){
        String Correo = txt_usuario.getText().toString().trim();
        String Pass = txt_password.getText().toString().trim();
        if(Correo.isEmpty()){
            txt_usuario.setError("Correo Requerido!");
            txt_usuario.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Correo).matches()){
            txt_usuario.setError("Ingrese Email Valido");
            txt_usuario.requestFocus();
            return;
        }
        if(Pass.isEmpty()){
            txt_password.setError("Contraseña Requerida!");
            txt_password.requestFocus();
            return;
        }
        if(Pass.length()<6){
            txt_password.setError("Caracteres minimos: 6");
            txt_password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(Correo,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usuariofirebase= FirebaseAuth.getInstance().getCurrentUser();
                    if(usuariofirebase.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
                    }else{
                        usuariofirebase.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Revise su email para la verificacion",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Error al intetnar Ingresar",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void listar(){
        Intent intent = new Intent(MainActivity.this, ListarActivity.class);
        Bundle objBundle = new Bundle();
        //System.out.println(listaregistrada);
        objBundle.putSerializable("datos",listaregistrada);
        intent.putExtras(objBundle);
        startActivity(intent);
        finish();
    }
    public int validar(Usuario usuariovalidar,ArrayList<Usuario> lista2){

        Integer val=3;
        for (Usuario u: lista2
        ) {
            if(u.getEmail().equals(usuariovalidar.getEmail()) && u.getContrasena().equals(usuariovalidar.getContrasena())){
                val=1;
                break;
            }else if(u.getEmail().equals(usuariovalidar.getEmail()) && !u.getContrasena().equals(usuariovalidar.getContrasena())){
                val=2;
                break;
            }
        }

        return val;
    }

    @Override
    public void onClick(View v) {
        if (v==btnregistrar){
            registrar();
        } else if  (v==btningresar){
            userLogin();
        }else if (v==recuperar){
            olvidepass();
        }
    }
}