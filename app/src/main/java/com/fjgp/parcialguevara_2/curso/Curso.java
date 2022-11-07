package com.fjgp.parcialguevara_2.curso;

public class Curso {
    private String nombre;
    private String horario;
    private String codigo;
    private String silabus;
    private String carrera;

    public Curso() {
    }

    public Curso(String nombre, String horario, String codigo, String silabus, String carrera) {
        this.nombre = nombre;
        this.horario = horario;
        this.codigo = codigo;
        this.silabus = silabus;
        this.carrera = carrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSilabus() {
        return silabus;
    }

    public void setSilabus(String silabus) {
        this.silabus = silabus;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
