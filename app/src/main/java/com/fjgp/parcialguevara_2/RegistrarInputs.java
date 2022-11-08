package com.fjgp.parcialguevara_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrarInputs extends AppCompatActivity {

    Spinner meses_sp;
    EditText faltas_et, concentracion_et, apatia_et, culpa_et;
    Button registrar_btn;

    String userID;
    FirebaseUser user;
    DatabaseReference reference;
    DatabaseReference input_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_inputs);

        meses_sp = findViewById(R.id.meses_sp);
        faltas_et = findViewById(R.id.faltas_ed);
        concentracion_et = findViewById(R.id.concentracion_ed);
        apatia_et = findViewById(R.id.apatia_et);
        culpa_et = findViewById(R.id.culpa_et);
        registrar_btn = findViewById(R.id.registrar_btn);

        user= FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Usuario");
        input_ref = reference.child(userID).child("Input");

        loadData();

        registrar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mes = meses_sp.getSelectedItem().toString();
                String faltas = faltas_et.getText().toString();
                String concentracion = concentracion_et.getText().toString();
                String apatia = apatia_et.getText().toString();
                String culpa = culpa_et.getText().toString();

                if(!(faltas.isEmpty() || concentracion.isEmpty() || apatia.isEmpty() || culpa.isEmpty())) {
                    InputData data = new InputData();
                    data.setFaltas(Integer.parseInt(faltas));
                    data.setConcentacion(Integer.parseInt(concentracion));
                    data.setApatia(Integer.parseInt(apatia));
                    data.setCulpa(Integer.parseInt(culpa));

                    input_ref.child(mes).setValue(data);
                }
            }
        });

        meses_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Diego", "onNothingSelected: nada seleccionado");
            }
        });
    }

    void loadData() {
        String mes = meses_sp.getSelectedItem().toString();
        input_ref.child(mes).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();

                    Log.i("Diego", snapshot.toString());
                    if (snapshot.exists()) {
                        InputData data = snapshot.getValue(InputData.class);
                        Log.i("Diego", data.toString());

                        faltas_et.setText(String.valueOf(data.getFaltas()));
                        concentracion_et.setText(String.valueOf(data.getConcentacion()));
                        apatia_et.setText(String.valueOf(data.getApatia()));
                        culpa_et.setText(String.valueOf(data.getCulpa()));
                    } else {
                        faltas_et.setText("");
                        concentracion_et.setText("");
                        apatia_et.setText("");
                        culpa_et.setText("");
                    }
                }
            }
        });
    }
}

class InputData {
    String meses;
    int faltas;
    int concentacion;
    int apatia;
    int culpa;

    public InputData() {}

    public String getMeses() {
        return meses;
    }

    public void setMeses(String meses) {
        this.meses = meses;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getConcentacion() {
        return concentacion;
    }

    public void setConcentacion(int concentacion) {
        this.concentacion = concentacion;
    }

    public int getApatia() {
        return apatia;
    }

    public void setApatia(int apatia) {
        this.apatia = apatia;
    }

    public int getCulpa() {
        return culpa;
    }

    public void setCulpa(int culpa) {
        this.culpa = culpa;
    }
}