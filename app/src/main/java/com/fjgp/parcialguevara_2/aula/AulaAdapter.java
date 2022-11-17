package com.fjgp.parcialguevara_2.aula;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fjgp.parcialguevara_2.PerfilAula;
import com.fjgp.parcialguevara_2.R;

import java.util.ArrayList;

public class AulaAdapter extends RecyclerView.Adapter<AulaAdapter.AulaViewHolder> {

    private final ArrayList<Aula> aulas;
    Context context;

    public AulaAdapter(Context context) {
        this.aulas = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public AulaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_aula, parent, false);

        return new AulaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AulaViewHolder holder, int position) {
        holder.bindData(aulas.get(position));
    }

    @Override
    public int getItemCount() {
        return aulas.size();
    }

    public void setAulas(ArrayList<Aula> aulas) {
        this.aulas.clear();
        this.aulas.addAll(aulas);

        notifyDataSetChanged();
    }

    public class AulaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_grado, tv_seccion, tv_anyo;
        ImageView iv_icon;

        public AulaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_grado = (TextView) itemView.findViewById(R.id.nombre_curso);
            tv_seccion = (TextView) itemView.findViewById(R.id.carrera_curso);
            iv_icon = (ImageView) itemView.findViewById(R.id.aula_icon);

            itemView.setOnClickListener(this);
        }

        void bindData(final Aula aula) {
            tv_grado.setText(aula.getGrado());
            tv_seccion.setText(aula.getSeccion());
            tv_anyo.setText(aula.getAnyo());
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, PerfilAula.class);
            String aula_id = tv_grado.getText().toString() + tv_seccion.getText().toString() + tv_anyo.getText().toString();
            intent.putExtra("aula_id", aula_id);
            context.startActivity(intent);
        }
    }
}
