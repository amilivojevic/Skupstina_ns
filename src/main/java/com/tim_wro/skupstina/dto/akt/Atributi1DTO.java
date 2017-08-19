package com.tim_wro.skupstina.dto.akt;

public class Atributi1DTO {
    String naziv;
    String br;

    public Atributi1DTO(String naziv, String br) {
        this.naziv = naziv;
        this.br = br;
    }

    public Atributi1DTO() {
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    @Override
    public String toString() {
        return "Atributi1DTO{" +
                "naziv='" + naziv + '\'' +
                ", br='" + br + '\'' +
                '}';
    }
}
