package com.tim_wro.skupstina.services;

import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.repository.UserRepository;
import com.tim_wro.skupstina.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by Sandra on 6/14/2017.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    public Korisnik findByUsername(String username){
        return userRepository.findByKorisnickoIme(username);
    }

    public Korisnik findByToken(String token){
        String un = tokenUtils.getUsernameFromToken(token);
        UserDetails details = userDetailsService.loadUserByUsername(un);

        System.out.println("username : " + details.getUsername());
        Korisnik user = findByUsername(details.getUsername());

        return user;
    }

    public Korisnik register(Korisnik korisnik){
        return userRepository.save(korisnik);

    }
    public Korisnik save(Korisnik u){
        return userRepository.save(u);
    }



}
