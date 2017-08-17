package com.tim_wro.skupstina.repository;

import com.tim_wro.skupstina.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface represents User repository
 *
 */
public interface UserRepository extends JpaRepository<Korisnik,Long> {


    Korisnik findByKorisnickoIme(String korisnik);


    Korisnik findById(Long id);


}