package com.tim_wro.skupstina.dto.akt;

import java.util.ArrayList;

public class StavDTO {
    ArrayList<TackaDTO> tacke;
    ArrayList<String> sadrzaj;

    public StavDTO(ArrayList<TackaDTO> tacke, ArrayList<String> sadrzaj) {
        this.tacke = tacke;
        this.sadrzaj = sadrzaj;
    }

    public StavDTO() {
    }

    public ArrayList<TackaDTO> getTacke() {
        return tacke;
    }

    public void setTacke(ArrayList<TackaDTO> tacke) {
        this.tacke = tacke;
    }

    public ArrayList<String> getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(ArrayList<String> sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    @Override
    public String toString() {
        return "StavDTO{" +
                "tacke=" + tacke +
                ", sadrzaj=" + sadrzaj +
                '}';
    }
}
