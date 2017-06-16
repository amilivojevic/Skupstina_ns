package com.tim_wro.skupstina.controller.auth;

import com.tim_wro.skupstina.dto.RegisterDTO;
import com.tim_wro.skupstina.model.*;
import com.tim_wro.skupstina.services.UserService;
import com.tim_wro.skupstina.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Korisnik on 6/15/2017.
 */
@RestController
@RequestMapping("/api/users")
public class RegisterController {

    @Autowired
    private UserService userService;


    //registracija korisnika
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody RegisterDTO registerDTO) {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Pocinje registracija korisnika na backendu!");

        System.out.println("new User: " + registerDTO.toString());

        Korisnik korisnik;
        System.out.println("tip = " + registerDTO.getType());

        if (registerDTO.getType().equalsIgnoreCase("Predsednik") ||
                registerDTO.getType().equalsIgnoreCase("Poslanik")) {

            TipKorisnika type;
            if (registerDTO.getType().equalsIgnoreCase("predsednik")){
                type = TipKorisnika.PREDSEDNIK;
            }
            else{
                type = TipKorisnika.PREDSEDNIK;
            }

            //sifra je enkodirana
            korisnik = new Korisnik(
                    registerDTO.getName(),
                    registerDTO.getSurname(),
                    registerDTO.getUsername(),
                    registerDTO.getPassword(),
                    //encoder.encode(registerDTO.getPassword()),
                    type,
                    UUID.randomUUID().toString()
            );

        }
        else {
            System.out.println("Nije predsednik ili poslanik");
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage("Cant create that type of user, only President and Poslanik allowed"),
                    HttpStatus.BAD_REQUEST);
        }

        userService.register(korisnik);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(korisnik.toString()), HttpStatus.OK);
    }
}
