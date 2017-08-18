package com.tim_wro.skupstina.controller.akt;

import com.tim_wro.skupstina.dto.akt.AktDTO;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Nina on 16-Jun-17.
 */

@RestController
@RequestMapping("/api/akt")
public class AktController {

    private final AktService aktService;

    @Autowired
    public AktController(AktService aktService){
        this.aktService = aktService;

    }

    @PostMapping("/novi")
    public ResponseEntity create(@RequestBody AktDTO akt) {

        System.out.println("stigao akt!!!" + akt.toString());

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);


    }
}






