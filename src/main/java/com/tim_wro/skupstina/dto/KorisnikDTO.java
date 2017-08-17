package com.tim_wro.skupstina.dto;

import com.tim_wro.skupstina.model.Authority;
import com.tim_wro.skupstina.model.Korisnik;

public class KorisnikDTO {

    private long id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private Authority authority;

    public KorisnikDTO() {
    }

    public KorisnikDTO(Korisnik user){
        this.id = user.getId();
        this.ime = user.getIme();
        this.prezime = user.getPrezime();
        this.korisnickoIme = user.getKorisnickoIme();
        this.lozinka = user.getLozinka();
        this.authority = user.getAuthority();

    }
    public KorisnikDTO(long id, String ime, String prezime, String korisnickoIme, String lozinka, Authority authority) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
