package com.tim_wro.skupstina.dto.akt;

import java.util.ArrayList;

public class TackaDTO {

    Atributi2DTO atributi;
    ArrayList<PodtackaDTO> podtacke;
    ArrayList<String> sadrzaj;

    public TackaDTO(Atributi2DTO atributi, ArrayList<PodtackaDTO> podtacke, ArrayList<String> sadrzaj) {
        this.atributi = atributi;
        this.podtacke = podtacke;
        this.sadrzaj = sadrzaj;
    }

    public TackaDTO() {
    }

    public Atributi2DTO getAtributi() {
        return atributi;
    }

    public ArrayList<String> getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(ArrayList<String> sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public void setAtributi(Atributi2DTO atributi) {
        this.atributi = atributi;
    }

    public ArrayList<PodtackaDTO> getPodtacke() {
        return podtacke;
    }

    public void setPodtacke(ArrayList<PodtackaDTO> podtacke) {
        this.podtacke = podtacke;
    }
}
