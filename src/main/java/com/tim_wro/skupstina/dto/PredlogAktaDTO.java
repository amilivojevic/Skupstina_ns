package com.tim_wro.skupstina.dto;

import java.util.Date;

public class PredlogAktaDTO {

    private String aktID;

    private String sednicaRB;

    public PredlogAktaDTO() {
    }

    public PredlogAktaDTO(String aktID, String sednicaRB) {
        this.aktID = aktID;
        this.sednicaRB = sednicaRB;
    }

    public String getAktID() {
        return aktID;
    }

    public void setAktID(String aktID) {
        this.aktID = aktID;
    }

    public String getSednicaRB() {
        return sednicaRB;
    }

    public void setSednicaRB(String sednicaRB) {
        this.sednicaRB = sednicaRB;
    }

    @Override
    public String toString() {
        return "PredlogAktaDTO{" +
                "aktID='" + aktID + '\'' +
                ", sednicaRB='" + sednicaRB + '\'' +
                '}';
    }
}
