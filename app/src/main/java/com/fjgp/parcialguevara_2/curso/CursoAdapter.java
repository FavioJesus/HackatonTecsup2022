package com.fjgp.parcialguevara_2.curso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fjgp.parcialguevara_2.R;
import com.fjgp.parcialguevara_2.listar_curso;
import com.fjgp.parcialguevara_2.perfil_curso;

import java.util.ArrayList;

public class CursoAdapter  extends RecyclerView.Adapter<CursoAdapter.CursoViewHolder> {

    private ArrayList<Curso> cursos;
    private int resources;
    Context context;
    private RecyclerViewClickListener listener;

    public CursoAdapter(ArrayList<Curso> cursos, int resources, Context context, RecyclerViewClickListener listener) {
        this.cursos = cursos;
        this.resources = resources;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(
                        parent.getContext()
                )
                .inflate(
                        resources,
                        parent,
                        false
                );
        return new CursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        Curso curso = cursos.get(position);
        holder
                .nombre
                .setText(
                        curso.getNombre()
                );
        holder
                .carrera
                .setText(
                        curso.getCarrera()
                );
        holder
                .codigo
                .setText(
                        curso.getCodigo()
                );
        String variable="Taller de tesis";
        String nombre2=curso.getNombre();
        int RGBBad = android.graphics.Color.rgb(255, 136, 119);
        int RGBGood= android.graphics.Color.rgb(183, 216, 129);
        if (nombre2.equals(variable)){
            holder.itemView.setBackgroundColor(RGBBad);
        }else {
            holder.itemView.setBackgroundColor(RGBGood);
        }
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    public class CursoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombre, carrera, codigo;
        View view;
        ImageView detalle;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            view.setOnClickListener(this);

            nombre  = (TextView) itemView.findViewById(R.id.nombre_curso);
            carrera = (TextView) itemView.findViewById(R.id.carrera_curso);
            codigo  = (TextView) itemView.findViewById(R.id.codigo_curso);
            detalle = (ImageView) itemView.findViewById(R.id.detalle);



        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
