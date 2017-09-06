package com.tim_wro.skupstina.dto;

public class FirstVotingDTO {

    private String sednicaID;

    private String aktID;

    private int brojPrisutnih;

    private int za;

    private int protiv;

    private int suzdrzani;

    public FirstVotingDTO() {
    }

    public FirstVotingDTO(String sednicaID, String aktID, int brojPrisutnih, int za, int protiv, int suzdrzani) {
        this.sednicaID = sednicaID;
        this.aktID = aktID;
        this.brojPrisutnih = brojPrisutnih;
        this.za = za;
        this.protiv = protiv;
        this.suzdrzani = suzdrzani;
    }

    public int getBrojPrisutnih() {
        return brojPrisutnih;
    }

    public void setBrojPrisutnih(int brojPrisutnih) {
        this.brojPrisutnih = brojPrisutnih;
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

    public String getAktID() {
        return aktID;
    }

    public void setAktID(String aktID) {
        this.aktID = aktID;
    }

    public String getSednicaID() {
        return sednicaID;
    }

    public void setSednicaID(String sednicaID) {
        this.sednicaID = sednicaID;
    }
}
