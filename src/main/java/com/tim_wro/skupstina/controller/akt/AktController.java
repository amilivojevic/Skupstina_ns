package com.tim_wro.skupstina.controller.akt;

import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


/**
 * Created by Nina on 16-Jun-17.
 */

@RestController
@RequestMapping("/api/acts")
/** Sta je to nama**/


public class AktController {

    private final AktService aktService;

    @Autowired
    public AktController(AktService aktService){
        this.aktService = aktService;

    }

    @PostMapping
    public ResponseEntity create(@RequestBody String akt, Korisnik korisnik) {

        aktService.create(akt, korisnik);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);


    }
}






