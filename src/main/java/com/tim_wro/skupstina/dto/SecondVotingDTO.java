package com.tim_wro.skupstina.dto;

public class SecondVotingDTO {

    private String sednicaID;

    private String amandmanID;

    private int za;

    private int protiv;

    private int suzdrzani;

    public SecondVotingDTO() {
    }

    public SecondVotingDTO(String sednicaID, String amandmanID, int za, int protiv, int suzdrzani) {
        this.sednicaID = sednicaID;
        this.amandmanID = amandmanID;
        this.za = za;
        this.protiv = protiv;
        this.suzdrzani = suzdrzani;
    }

    public String getSednicaID() {
        return sednicaID;
    }

    public void setSednicaID(String sednicaID) {
        this.sednicaID = sednicaID;
    }

    public String getAmandmanID() {
        return amandmanID;
    }

    public void setAmandmanID(String amandmanID) {
        this.amandmanID = amandmanID;
    }

    public int getZa() {
        return za;
    }

    public void setZa(int za) {
        this.za = za;
    }

    public int getProtiv() {
        return protiv;
    }

    public void setProtiv(int protiv) {
        this.protiv = protiv;
    }

    public int getSuzdrzani() {
        return suzdrzani;
    }

    public void setSuzdrzani(int suzdrzani) {
        this.suzdrzani = suzdrzani;
    }
}
