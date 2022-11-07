package com.fjgp.parcialguevara_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Usuario> usuario;

    public MyAdapter(Context context,int layout,ArrayList<Usuario> usuario){
        this.context=context;
        this.layout=layout;
        this.usuario=usuario;

    }
    @Override
    public int getCount() {
        return this.usuario.size();
    }

    @Override
    public Object getItem(int position) {
        return this.usuario.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class Controles{
        TextView lblemail;
        TextView lblnombres;
        TextView lblapellidos;
        TextView lblestado;
        TextView lblsexo;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Controles controles;
        if (convertView==null){
            LayoutInflater layoutinflanter=LayoutInflater.from(this.context);
            convertView=layoutinflanter.inflate(R.layout.historialusuarios,null);
            controles = new Controles();
            controles.lblemail=(TextView) convertView.findViewById(R.id.idtxt_emailusuario);
            controles.lblnombres=(TextView) convertView.findViewById(R.id.idtxt_nombreusuario);
            controles.lblapellidos=(TextView) convertView.findViewById(R.id.idtxt_apellidousuario);
            controles.lblestado=(TextView) convertView.findViewById(R.id.idtxt_estadousuario);
            controles.lblsexo=(TextView) convertView.findViewById(R.id.idtxt_sexousuario);

            convertView.setTag(controles);
        }else {
            controles=(Controles)convertView.getTag();
        }
        controles.lblemail.setText(String.valueOf(usuario.get(position).getEmail()));
        controles.lblnombres.setText(usuario.get(position).getNombres());
        controles.lblapellidos.setText(String.valueOf(usuario.get(position).getApellidos()));



        return convertView;

    }
}

