package com.tim_wro.skupstina.controller.akt;

import com.tim_wro.skupstina.dto.akt.AktDTO;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;


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
    public ResponseEntity create(@RequestBody Akt akt) {

        System.out.println("stigao akt!!!");

        //marshalling
        File file = new File("D:\\file.xml");
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Akt.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(akt, file);
            jaxbMarshaller.marshal(akt, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);


    }
}






