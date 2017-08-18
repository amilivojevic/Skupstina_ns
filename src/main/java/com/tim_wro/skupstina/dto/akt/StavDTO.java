package com.tim_wro.skupstina.dto.akt;

public class StavDTO {
    String nesto;

    public StavDTO(String nesto) {
        this.nesto = nesto;
    }

    public StavDTO() {
    }

    public String getNesto() {
        return nesto;
    }

    public void setNesto(String nesto) {
        this.nesto = nesto;
    }

    @Override
    public String toString() {
        return "StavDTO{" +
                "nesto='" + nesto + '\'' +
                '}';
    }
}
