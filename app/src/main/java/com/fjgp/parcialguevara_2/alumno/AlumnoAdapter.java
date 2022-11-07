package com.fjgp.parcialguevara_2.alumno;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fjgp.parcialguevara_2.R;
import com.fjgp.parcialguevara_2.perfil_curso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>{

    private ArrayList<Alumno> alumnos;
    private int resources;

    Context context;
    private RecyclerViewClickListener listener;



    public AlumnoAdapter(ArrayList<Alumno> alumnos, int resources, Context context, RecyclerViewClickListener listener) {
        this.alumnos = alumnos;
        this.resources = resources;
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(
                        parent.getContext()
                )
                .inflate(
                        resources,
                        parent,
                        false
                );
        return new AlumnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        Alumno alumno = alumnos.get(position);
        holder
                .nombre
                .setText(
                        alumno.getNombre()
                );
        holder
                .apellido
                .setText(
                        alumno.getApellido()
                );
        holder
                .codigo
                .setText(
                        alumno.getCodigo()
                );
        int RGBNeutral = android.graphics.Color.rgb(189, 183, 107);

        if (alumno.getEvaluar()==1){

            holder.itemView.setBackgroundColor(RGBNeutral);
        }else{
            holder.itemView.setBackgroundColor(alumno.getEvaluar());
        }



    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class AlumnoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombre, apellido, codigo;
        View view;
        ImageView detalle;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);

            this.view = itemView;
            view.setOnClickListener(this);
            nombre  = (TextView) itemView.findViewById(R.id.apellido_alumno);
            apellido = (TextView) itemView.findViewById(R.id.nombre_alumno);
            codigo  = (TextView) itemView.findViewById(R.id.codigo_alumno);
            detalle = (ImageView) itemView.findViewById(R.id.detalle1);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(view,getAdapterPosition());

        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
