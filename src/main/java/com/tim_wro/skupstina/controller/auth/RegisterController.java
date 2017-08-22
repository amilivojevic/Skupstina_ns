package com.tim_wro.skupstina.controller.auth;

import com.tim_wro.skupstina.dto.KorisnikDTO;
import com.tim_wro.skupstina.dto.LoginDTO;
import com.tim_wro.skupstina.dto.RegisterDTO;
import com.tim_wro.skupstina.model.*;
import com.tim_wro.skupstina.model.enumerations.TipKorisnika;
import com.tim_wro.skupstina.security.TokenUtils;
import com.tim_wro.skupstina.services.AuthorityService;
import com.tim_wro.skupstina.services.UserService;
import com.tim_wro.skupstina.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Korisnik on 6/15/2017.
 */
@RestController
@RequestMapping("/api/users")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtils tokenUtils;


    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        try {
         /*   System.out.println("*** Pocinje login na backendu"); */

            if(loginDTO.getUsername() == null || loginDTO.getPassword() == null){
                return new ResponseEntity<>(new ResponseMessage("Username or password must be inserted!"), HttpStatus.BAD_REQUEST);
            }
            // Perform the authentication
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                    loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
            String final_token = tokenUtils.generateToken(details);

            Korisnik u = userService.findByToken(final_token);
            // vratimo 200 OK, klijennt MORA DA SACUVA TRAJNi O ovaj token, i da ga stalno salje u svaki sledeci request
            // X-Auth-Header
            return new ResponseEntity<>(new ResponseMessage(final_token + " " + u.getTip().name()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseMessage("Invalid login"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<KorisnikDTO> getData(@RequestHeader("X-Auth-Token") String token)
    {
        Korisnik user = userService.findByToken(token);
        return new ResponseEntity<>(new KorisnikDTO(user), HttpStatus.OK);
    }

    //registracija korisnika
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody RegisterDTO registerDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

       // System.out.println("Pocinje registracija korisnika na backendu!");

        System.out.println("new User: " + registerDTO.toString());

        Korisnik korisnik;
        System.out.println("tip = " + registerDTO.getType());

        if (!registerDTO.getType().equalsIgnoreCase("Predsednik") &&
                !registerDTO.getType().equalsIgnoreCase("Poslanik")) {
            System.out.println("Nije predsednik ili poslanik");
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage("Cant create that type of user, only President and Poslanik allowed"),
                    HttpStatus.BAD_REQUEST);
        }


        TipKorisnika role;
        if(registerDTO.getType().equals("Predsednik"))  {
            role = TipKorisnika.PREDSEDNIK;
        } else {
            role = TipKorisnika.POSLANIK;
        }

        //sifra je enkodirana
        korisnik = new Korisnik(
                registerDTO.getName(),
                registerDTO.getSurname(),
                registerDTO.getUsername(),
                encoder.encode(registerDTO.getPassword()),
                authorityService.findByName(("ROLE_" + registerDTO.getType())),
             //   authorityService.findByName("ROLE_PREDSEDNIK"),
                role
        );

        userService.register(korisnik);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(korisnik.toString()), HttpStatus.OK);
    }

}
