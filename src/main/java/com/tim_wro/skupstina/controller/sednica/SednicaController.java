package com.tim_wro.skupstina.controller.sednica;

import com.tim_wro.skupstina.services.SednicaService;
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

@RestController
@RequestMapping("/api/sednica")
public class SednicaController {

    private final SednicaService sednicaService;

    @Autowired
    public SednicaController(SednicaService sednicaService){
        this.sednicaService = sednicaService;
    }


    @PostMapping("/novi")
    public ResponseEntity create(@RequestBody Sednica sednica) throws FileNotFoundException {

        //marshalling
        File file = new File("file1.xml");
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Sednica.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(sednica, file);
            jaxbMarshaller.marshal(sednica, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        //writing in marklogic db

        sednicaService.writeInMarkLogicDB(file);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);


    }
}
