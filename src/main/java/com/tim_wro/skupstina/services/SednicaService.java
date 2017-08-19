package com.tim_wro.skupstina.services;

import com.tim_wro.skupstina.repository.SednicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SednicaService {

    private SednicaRepository sednicaRepository;
    public static final String RDF_XSL = "src/main/resources/schemes/sednica.xsl";

    @Autowired
    public SednicaService(SednicaRepository sednicaRepository) {
        this.sednicaRepository = sednicaRepository;
    }


}
