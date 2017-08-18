package com.tim_wro.skupstina.dto.akt;

import java.util.ArrayList;

public class ClanDTO {

    Atributi1DTO atributi;
    ArrayList<StavDTO> stav;

    public ClanDTO(Atributi1DTO atributi, ArrayList<StavDTO> stavovi) {
        this.atributi = atributi;
        this.stav = stavovi;
    }

    public ClanDTO() {
    }

    public Atributi1DTO getAtributi() {
        return atributi;
    }

    public void setAtributi(Atributi1DTO atributi) {
        this.atributi = atributi;
    }

    public ArrayList<StavDTO> getStavovi() {
        return stav;
    }

    public void setStavovi(ArrayList<StavDTO> stavovi) {
        this.stav = stavovi;
    }

    @Override
    public String toString() {
        return "ClanDTO{" +
                "atributi=" + atributi +
                ", stavovi=" + stav +
                '}';
    }
}
