
package com.tim_wro.skupstina.model;

import com.sun.istack.internal.NotNull;
import com.tim_wro.skupstina.model.enumerations.TipKorisnika;

import javax.persistence.*;

@Entity
public class Korisnik {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "ime", nullable = false, unique = true)
    private String ime;

    @Column(name = "prezime", nullable = false)
    private String prezime;

    @Column(name = "korisnickoIme", nullable = false)
    private String korisnickoIme;

    @Column(name = "lozinka", nullable = false)
    private String lozinka;

    //@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @ManyToOne
    private Authority authority;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipKorisnika tip;


    public Korisnik() {
    }

    public Korisnik(String ime, String prezime, String korisnickoIme, String lozinka, Authority authority, TipKorisnika tip) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.authority = authority;
        this.tip = tip;
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

    public TipKorisnika getTip() {
        return tip;
    }

    public void setTip(TipKorisnika tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id='" + id + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", authority=" + authority +
                ", tip=" + tip +
                '}';
    }
}
