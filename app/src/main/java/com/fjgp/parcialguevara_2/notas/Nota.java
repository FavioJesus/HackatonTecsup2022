package com.fjgp.parcialguevara_2.notas;

public class Nota {
    private String nt1;
    private String nt2;
    private String nt3;
    private String nt4;
    private String pp;
    private String ntparcial;
    private String ntfinal;
    private String promedioFinal;

    public Nota() {
    }

    public Nota(String nt1, String nt2, String nt3, String nt4, String pp, String ntparcial, String ntfinal, String promedioFinal) {
        this.nt1 = nt1;
        this.nt2 = nt2;
        this.nt3 = nt3;
        this.nt4 = nt4;
        this.pp = pp;
        this.ntparcial = ntparcial;
        this.ntfinal = ntfinal;
        this.promedioFinal = promedioFinal;
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








