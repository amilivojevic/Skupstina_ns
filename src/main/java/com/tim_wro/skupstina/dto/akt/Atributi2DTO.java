package com.tim_wro.skupstina.dto.akt;

public class Atributi2DTO {

    String br;

    public Atributi2DTO(String br) {
        this.br = br;
    }

    public Atributi2DTO() {
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    @Override
    public String toString() {
        return "Atributi2DTO{" +
                "br='" + br + '\'' +
                '}';
    }
}
