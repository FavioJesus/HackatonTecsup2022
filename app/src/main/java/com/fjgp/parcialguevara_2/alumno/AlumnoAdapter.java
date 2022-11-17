package com.fjgp.parcialguevara_2.alumno;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fjgp.parcialguevara_2.PerfilAula;
import com.fjgp.parcialguevara_2.R;
import com.fjgp.parcialguevara_2.RegistrarInputs;

import java.util.ArrayList;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {

    private final ArrayList<Alumno> alumnos;
    Context context;

    public AlumnoAdapter(Context context) {
        this.alumnos = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_alumno, parent,false);

        return new AlumnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        holder.bindData(alumnos.get(position));
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos.clear();
        this.alumnos.addAll(alumnos);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class AlumnoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_nombres, tv_apellidos, tv_codigo;
        ImageView iv_icon;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombres = (TextView) itemView.findViewById(R.id.apellido_alumno);
            tv_apellidos = (TextView) itemView.findViewById(R.id.nombre_alumno);
            iv_icon = (ImageView) itemView.findViewById(R.id.detalle1);

            itemView.setOnClickListener(this);
        }

        public void bindData(Alumno alumno) {
            if (alumno.getCurrentStatus() > 50) {
                tv_nombres.setTextColor(Color.RED);
                tv_apellidos.setTextColor(Color.RED);
            } else {
                tv_nombres.setTextColor(Color.GREEN);
                tv_apellidos.setTextColor(Color.GREEN);
            }

            tv_nombres.setText(alumno.getNombre());
            tv_apellidos.setText(alumno.getApellido());
            tv_codigo.setText(alumno.getCodigo());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, RegistrarInputs.class);
            intent.putExtra("alumno_codigo", tv_codigo.getText().toString());
            intent.putExtra("aula_id", ((PerfilAula) context).aula_id);
            context.startActivity(intent);
        }
    }
}
