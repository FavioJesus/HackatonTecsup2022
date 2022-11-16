package com.fjgp.parcialguevara_2.aula;

public class Aula {
    private String grado;
    private String seccion;
    private String anyo;
    private float pdfdum; // promedio de faltas del ultimo mes

    public Aula() {}

    public Aula(String grado, String seccion, String anyo) {
        this.grado = grado;
        this.seccion = seccion;
        this.anyo = anyo;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    public float getPdfdum() {
        return pdfdum;
    }

    public void setPdfdum(float pdfdum) {
        this.pdfdum = pdfdum;
    }
}
