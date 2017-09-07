package com.tim_wro.skupstina.dto;

public class PredlogAmandmanaDTO {

    private String amandmanID;

    private String aktID;

    public PredlogAmandmanaDTO() {
    }

    public PredlogAmandmanaDTO(String amandmanID, String aktID) {
        this.amandmanID = amandmanID;
        this.aktID = aktID;
    }

    public String getAmandmanID() {
        return amandmanID;
    }

    public void setAmandmanID(String amandmanID) {
        this.amandmanID = amandmanID;
    }

    public String getAktID() {
        return aktID;
    }

    public void setAktID(String aktID) {
        this.aktID = aktID;
    }

    @Override
    public String toString() {
        return "PredlogAmandmanaDTO{" +
                "amandmanID='" + amandmanID + '\'' +
                ", aktID='" + aktID + '\'' +
                '}';
    }
}
