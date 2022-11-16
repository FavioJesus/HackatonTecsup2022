package com.fjgp.parcialguevara_2;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fjgp.parcialguevara_2.alumno.Alumno;
import com.fjgp.parcialguevara_2.ml.MlBlHt;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.security.auth.login.LoginException;

public class RegistrarInputs extends AppCompatActivity {

    Spinner meses_sp;
    EditText n1_et, n2_et, n3_et, n4_et;
    EditText faltas_et, concentracion_et, apatia_et;
    Button registrar_btn;

    String alumno_codigo, aula_id;

    String userID;
    FirebaseUser user;
    DatabaseReference inputData_reference;
    DatabaseReference aula_reference;
    ArrayList<InputData> list_data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_inputs);

        n1_et = findViewById(R.id.n1_et);
        n2_et = findViewById(R.id.n2_et);
        n3_et = findViewById(R.id.n3_et);
        n4_et = findViewById(R.id.n4_et);
        meses_sp = findViewById(R.id.meses_sp);
        faltas_et = findViewById(R.id.faltas_ed);
        concentracion_et = findViewById(R.id.concentracion_ed);
        apatia_et = findViewById(R.id.apatia_et);
        registrar_btn = findViewById(R.id.registrar_btn);

        aula_id = getIntent().getStringExtra("aula_id");
        alumno_codigo = getIntent().getStringExtra("alumno_codigo");

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        aula_reference = FirebaseDatabase.getInstance().getReference()
                .child("Profesores")
                .child(userID)
                .child("Aulas")
                .child(aula_id);

        inputData_reference = aula_reference
                .child("Alumnos")
                .child(alumno_codigo)
                .child("InputData");

        loadData();

        registrar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n1 = n1_et.getText().toString();
                String n2 = n2_et.getText().toString();
                String n3 = n3_et.getText().toString();
                String n4 = n4_et.getText().toString();
                String mes = meses_sp.getSelectedItem().toString();
                String faltas = faltas_et.getText().toString();
                String concentracion = concentracion_et.getText().toString();
                String apatia = apatia_et.getText().toString();

                boolean notas_vacias = n1.isEmpty() || n2.isEmpty() || n3.isEmpty() || n4.isEmpty();
                if (!(faltas.isEmpty() || concentracion.isEmpty() || apatia.isEmpty() || notas_vacias)) {
                    InputData data = new InputData();
                    data.setN1(Integer.parseInt(n1));
                    data.setN2(Integer.parseInt(n2));
                    data.setN3(Integer.parseInt(n3));
                    data.setN4(Integer.parseInt(n4));
                    data.setFaltas(Integer.parseInt(faltas));
                    data.setConcentacion(Integer.parseInt(concentracion));
                    data.setApatia(Integer.parseInt(apatia));

                    getAllData(mes, data);
                } else {
                    Toast.makeText(RegistrarInputs.this.getApplicationContext(), "Campos vacios !!!", Toast.LENGTH_SHORT).show();
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
                Log.e(TAG, "onNothingSelected: ");
            }
        });
    }

    void loadData() {
        String mes = meses_sp.getSelectedItem().toString();
        inputData_reference.child(mes).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();

                    if (snapshot.exists()) {
                        InputData data = snapshot.getValue(InputData.class);

                        n1_et.setText(String.valueOf(data.n1));
                        n2_et.setText(String.valueOf(data.n2));
                        n3_et.setText(String.valueOf(data.n3));
                        n4_et.setText(String.valueOf(data.n4));
                        faltas_et.setText(String.valueOf(data.getFaltas()));
                        concentracion_et.setText(String.valueOf(data.getConcentacion()));
                        apatia_et.setText(String.valueOf(data.getApatia()));
                    } else {
                        n1_et.setText("");
                        n2_et.setText("");
                        n3_et.setText("");
                        n4_et.setText("");
                        faltas_et.setText("");
                        concentracion_et.setText("");
                        apatia_et.setText("");
                    }
                }
            }
        });
    }

    void getAllData(String mes, InputData data1) {
        if (!"Enero".equals(mes)) {
            inputData_reference.child(getFormerMonth(mes)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        InputData data0 = dataSnapshot.getValue(InputData.class);

                        calcularPromedioDeFaltas(data0, data1, mes);
                    }
                }
            });
        } else {
            saveData(data1, mes);
        }
    }


    void machinelearning(InputData data0, InputData data1, float sum, String mes) {
        float matriz_data[] = {
                data0.getN1(),
                data0.getN2(),
                data0.getN3(),
                data0.getN4(),
                data1.getN1(),
                data1.getN2(),
                data1.getN3(),
                data1.getN4(),
                data1.getFaltas() / sum,
                data1.getConcentacion(),
                data1.getApatia(),
        };

        try {
            MlBlHt model = MlBlHt.newInstance(this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 11}, DataType.FLOAT32);
            inputFeature0.loadArray(matriz_data);

            // Runs model inference and gets result.
            MlBlHt.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] res = outputFeature0.getFloatArray();

            data1.setStatus(Math.round(res[0]*100));
            saveData(data1, mes);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    void calcularPromedioDeFaltas(InputData data0, InputData data1, String mes) {
        aula_reference.child("Alumnos").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Thread thread = new Thread() {
                        public void run() {
                            DataSnapshot dataSnapshot = task.getResult();

                            list_data.clear();
                            for (DataSnapshot alumno : dataSnapshot.getChildren()) {
                                Alumno alumno1 = alumno.getValue(Alumno.class);
                                Task<DataSnapshot> t = aula_reference
                                        .child("Alumnos")
                                        .child(alumno1.getCodigo())
                                        .child("InputData")
                                        .child(mes).get();

                                try {
                                    DataSnapshot res = Tasks.await(t);
                                    if (alumno_codigo.equals(alumno1.getCodigo())) {
                                        list_data.add(data1);
                                    } else {
                                        list_data.add(res.getValue(InputData.class));
                                    }

                                    //calculando el promedio
                                    Log.i("Diego", "" + list_data.get(0));
                                    float p = 0;
                                    for (int i = 0; i < list_data.size(); i++) {
                                        p += list_data.get(i).getFaltas();
                                    }
                                    if (list_data.size() != 0)
                                        p = p / list_data.size();

                                    Log.i("Promedio", String.valueOf(p) + " <> " + String.valueOf(list_data.size()));

                                    aula_reference.child("pdfdum").setValue(p);

                                    machinelearning(data0, data1, p, mes);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };

                    thread.start();
                }
            }
        });
    }

    void saveData(InputData data, String mes) {
        setCurrentStatus(data);
        inputData_reference.child(mes).setValue(data);
    }

    String getFormerMonth(String mes) {
        String[] meses = getResources().getStringArray(R.array.meses);
        ArrayList list_meses = new ArrayList(Arrays.asList(meses));
        int mes_index = list_meses.indexOf(mes);
        return meses[mes_index - 1];
    }

    void setCurrentStatus(InputData data) {
        aula_reference
                .child("Alumnos")
                .child(alumno_codigo)
                .child("currentStatus")
                .setValue(data.getStatus());
    }
}

class InputData {
    int n1;
    int n2;
    int n3;
    int n4;
    String mes;
    int faltas;
    int concentacion;
    int apatia;
    int status;

    public float getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InputData() {
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getN3() {
        return n3;
    }

    public void setN3(int n3) {
        this.n3 = n3;
    }

    public int getN4() {
        return n4;
    }

    public void setN4(int n4) {
        this.n4 = n4;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
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
}