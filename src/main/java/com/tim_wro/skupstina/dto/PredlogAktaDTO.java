package com.tim_wro.skupstina.dto;

import java.util.Date;

public class PredlogAktaDTO {

    private String aktID;

    private Date datumSednice;

    public PredlogAktaDTO() {
    }

    public PredlogAktaDTO(String aktID, Date datumSednice) {
        this.aktID = aktID;
        this.datumSednice = datumSednice;
    }

    public String getAktID() {
        return aktID;
    }

    public void setAktID(String aktID) {
        this.aktID = aktID;
    }

    public Date getDatumSednice() {
        return datumSednice;
    }

    public void setDatumSednice(Date datumSednice) {
        this.datumSednice = datumSednice;
    }

    @Override
    public String toString() {
        return "PredlogAktaDTO{" +
                "aktID='" + aktID + '\'' +
                ", datumSednice=" + datumSednice +
                '}';
    }
}
