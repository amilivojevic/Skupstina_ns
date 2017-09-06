package com.tim_wro.skupstina.controller.amandman;

import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Amandman;
import com.tim_wro.skupstina.model.StanjeAmandmana;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.services.AmandmanService;
import com.tim_wro.skupstina.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/amandman")
public class AmandmanController {

    @Autowired
    private AmandmanService amandmanService;

    @Autowired
    private AktService aktService;

    @PostMapping("/novi")
    public ResponseEntity create(@RequestBody Amandman amd) throws FileNotFoundException {

        amd.setId("amd"+UUID.randomUUID().toString());

        String aktID = amd.getAktID();
        Akt akt = aktService.getById(aktID);

        akt.getAmandmanID().add(amd.getId());
        try {
            aktService.updateAkt(akt);
        } catch (JAXBException e) {
            e.printStackTrace();
        }


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

    // vraca listu amandmana zakacenih za odredjeni akt koji je na ovoj sednici
    @RequestMapping(value = "/svi_zakazani/{id}", method = RequestMethod.GET)
    public ResponseEntity getAllPrepared(@PathVariable("id") String id) throws JAXBException {
        List<Amandman> lista = amandmanService.getBySednicaRedniBroj(id);
        List<Amandman> zakazaniAmandmani = new ArrayList<>();
        for(Amandman o : lista){
            if(o.getStanje() == StanjeAmandmana.ZAKAZAN){
                zakazaniAmandmani.add(o);
            }
        }

        return new ResponseEntity<>(zakazaniAmandmani,HttpStatus.OK);
    }
}
