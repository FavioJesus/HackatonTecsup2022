package com.fjgp.parcialguevara_2.alumno;

public class Alumno {

    private String codigo;
    private String nombre;
    private String apellido;
    private String email;
    private String nt1;
    private String nt2;
    private String nt3;
    private String nt4;
    private String pp;
    private String ntparcial;
    private String ntfinal;
    private String promedioFinal;

    private int evaluar;
    public Alumno(){

    }

    public Alumno(String codigo, String nombre, String apellido, String email) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEvaluar() {
        return evaluar;
    }

    public void setEvaluar(int evaluar) {
        this.evaluar = evaluar;
    }

    public String getNt1() {
        return nt1;
    }

    public void setNt1(String nt1) {
        this.nt1 = nt1;
    }

    public String getNt2() {
        return nt2;
    }

    public void setNt2(String nt2) {
        this.nt2 = nt2;
    }

    public String getNt3() {
        return nt3;
    }

    public void setNt3(String nt3) {
        this.nt3 = nt3;
    }

    public String getNt4() {
        return nt4;
    }

    public void setNt4(String nt4) {
        this.nt4 = nt4;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getNtparcial() {
        return ntparcial;
    }

    public void setNtparcial(String ntparcial) {
        this.ntparcial = ntparcial;
    }

    public String getNtfinal() {
        return ntfinal;
    }

    public void setNtfinal(String ntfinal) {
        this.ntfinal = ntfinal;
    }

    public String getPromedioFinal() {
        return promedioFinal;
    }

    public void setPromedioFinal(String promedioFinal) {
        this.promedioFinal = promedioFinal;
    }
}
