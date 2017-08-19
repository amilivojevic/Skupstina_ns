package com.tim_wro.skupstina.dto.akt;

import java.util.ArrayList;

public class AktDTO {

    ArrayList<ClanDTO> clanovi;

    public AktDTO(ArrayList<ClanDTO> clanovi) {
        this.clanovi = clanovi;
    }

    public AktDTO() {
    }

    public ArrayList<ClanDTO> getClanovi() {
        return clanovi;
    }

    public void setClanovi(ArrayList<ClanDTO> clanovi) {
        this.clanovi = clanovi;
    }

    @Override
    public String toString() {
        return "AktDTO{" +
                "clanovi=" + clanovi +
                '}';
    }
}
