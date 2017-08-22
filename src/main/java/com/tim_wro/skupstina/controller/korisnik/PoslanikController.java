package com.tim_wro.skupstina.controller.korisnik;

import com.tim_wro.skupstina.dto.RegisterDTO;
import com.tim_wro.skupstina.dto.RegisterSDTO;
import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.model.enumerations.TipKorisnika;
import com.tim_wro.skupstina.services.AuthorityService;
import com.tim_wro.skupstina.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users/poslanik")
public class PoslanikController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    //registracija administratora i verifikatora!
    @RequestMapping(value = "/modify", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity modifyUser(@RequestBody RegisterSDTO changedUserDTO, @RequestHeader("X-Auth-Token") String token) {
        Korisnik korisnik = userService.findByToken(token);


        korisnik.setIme(changedUserDTO.getIme());
        korisnik.setPrezime(changedUserDTO.getPrezime());
        korisnik.setKorisnickoIme(changedUserDTO.getKorisnickoIme());
        korisnik.setLozinka(changedUserDTO.getLozinka());
        korisnik.setAuthority(authorityService.findByName(("ROLE_POSLANIK")));
        TipKorisnika tipKorisnika = TipKorisnika.POSLANIK;
        korisnik.setTip(tipKorisnika);

        userService.save(korisnik);
        return new ResponseEntity<>(korisnik, HttpStatus.OK);
    }

}
