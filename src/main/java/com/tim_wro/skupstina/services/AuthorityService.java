package com.tim_wro.skupstina.services;

import com.tim_wro.skupstina.model.Authority;
import com.tim_wro.skupstina.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class represents Authority Service
 *
 */
@Service
public class AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

    /**
     * This method is finding one Authority by its name
     * @param name
     * @return object Authority
     */
    public Authority findByName(String name){
        return authorityRepository.findByName(name);
    }

}
