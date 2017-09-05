package com.tim_wro.skupstina.controller.amandman;

import com.tim_wro.skupstina.model.Amandman;
import com.tim_wro.skupstina.services.AmandmanService;
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
import java.io.FileNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("/api/amandman")
public class AmandmanController {

    @Autowired
    private AmandmanService amandmanService;

    @PostMapping("/novi")
    public ResponseEntity create(@RequestBody Amandman amd) throws FileNotFoundException {

        amd.setId(UUID.randomUUID().toString());


        //marshalling
        File file = new File("file.xml");
        JAXBContext jaxbContext = null;

        try {
            jaxbContext = JAXBContext.newInstance(Amandman.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.skustinans.rs/akti file:/C:/Users/Nina/Desktop/XML/Skupstina_ns/src/main/java/com/tim_wro/skupstina/model/akt_backup.xsd");

            jaxbMarshaller.marshal(amd, file);
            jaxbMarshaller.marshal(amd, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        //writing in marklogic db
        amandmanService.writeInMarkLogicDB(file, amd.getId());

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(amd.toString()), HttpStatus.CREATED);
    }
}
