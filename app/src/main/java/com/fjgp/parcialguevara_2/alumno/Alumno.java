package com.fjgp.parcialguevara_2.alumno;

public class Alumno {
    private String codigo;
    private String nombre;
    private String apellido;
    private int currentStatus;

    public Alumno() {}

    public Alumno(String codigo, String nombre, String apellido, int currentStatus) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.currentStatus = currentStatus;
    }

    public Alumno(String codigo, String nombre, String apellido) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.currentStatus = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public float getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }
}
