package com.tim_wro.skupstina.dto;

import java.util.Date;

public class SendicaDTO {

    private Date datum;

    private String redniBroj;

    private String naziv;

    public SendicaDTO() {
    }

    public SendicaDTO(Date datum, String redniBroj, String naziv) {
        this.datum = datum;
        this.redniBroj = redniBroj;
        this.naziv = naziv;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(String redniBroj) {
        this.redniBroj = redniBroj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "SendicaDTO{" +
                "datum=" + datum +
                ", redniBroj='" + redniBroj + '\'' +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}
