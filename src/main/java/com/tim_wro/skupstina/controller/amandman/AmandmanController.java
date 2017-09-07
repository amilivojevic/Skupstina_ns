package com.tim_wro.skupstina.controller.amandman;

import com.tim_wro.skupstina.dto.PredlogAmandmanaDTO;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Amandman;
import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.model.StanjeAmandmana;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.services.AmandmanService;
import com.tim_wro.skupstina.services.UserService;
import com.tim_wro.skupstina.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;

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

    @Autowired
    private UserService userService;

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

    // vraca listu akata u proceduri odredjenog usera
    @RequestMapping(value = "/svi_zakazani", method = RequestMethod.GET)
    public ResponseEntity getAllByUser(@RequestHeader("X-Auth-Token") String token) throws JAXBException {

        Korisnik k = userService.findByToken(token);

        List<Amandman> amandmaniUsera = amandmanService.getByUser(k.getKorisnickoIme());
        List<Amandman> zakazaniAmandmani = new ArrayList<>();
        for(Amandman a : amandmaniUsera){
            if(a.getStanje() == StanjeAmandmana.ZAKAZAN){
                zakazaniAmandmani.add(a);
            }
        }
        return new ResponseEntity<>(zakazaniAmandmani,HttpStatus.OK);
    }

    // fizicki brise predlozeni amandman na akt i uklanje njegov id iz akta
    @RequestMapping(value = "/otkazi", method = RequestMethod.POST)
    public ResponseEntity otkaziAmandman(@RequestBody PredlogAmandmanaDTO predlogAmandmanaDTO) throws JAXBException, FileNotFoundException {

        /////////////////////////////////////////////////////////////////
        ///// izbaciti id amandmana iz liste idava amandmana na aktu
        Akt akt = aktService.getById(predlogAmandmanaDTO.getAktID());
        List<String> amandmaniAkta = akt.getAmandmanID();

        for(int i = 0; i<amandmaniAkta.size(); i++){
            if(amandmaniAkta.get(i).equals(predlogAmandmanaDTO.getAmandmanID())){
                amandmaniAkta.remove(i);
            }
        }

        aktService.updateAkt(akt);

        ////////////////////////////////////////////////////////////////
        ///// fizicki ukloniti amandman

        Amandman amd = amandmanService.findById(predlogAmandmanaDTO.getAmandmanID());
        amandmanService.deleteFromDB(amd);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);

    }

    @PostMapping("/obrisi")
    public ResponseEntity delete(@RequestBody Amandman amandman) throws FileNotFoundException, JAXBException {

        Akt akt = aktService.getById(amandman.getAktID());
        List<String> amandmaniAkta = akt.getAmandmanID();
        for(int i = 0; i < amandmaniAkta.size(); i++){
            if(amandmaniAkta.get(i).equals(amandman.getId())){
                amandmaniAkta.remove(i);
                aktService.updateAkt(akt);
                break;
            }
        }

        amandmanService.deleteFromDB(amandman);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(amandman.toString()), HttpStatus.CREATED);

    }

    @GetMapping("/svi")
    public ResponseEntity getAll() throws JAXBException {
        System.out.println("pre aktService.getAll();");
        List<Amandman> lista = amandmanService.getAll();
        System.out.println("posle aktService.getAll();");
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @GetMapping("/svi/{korisnickoIme}")
    public ResponseEntity getAllFormUser(@PathVariable String korisnickoIme){
        List<Amandman> lista = amandmanService.getAll();
        List<Amandman> retVal = new ArrayList<>();
        for(Amandman a : lista){
            if(a.getKreirao().equals(korisnickoIme)){
                retVal.add(a);
            }
        }
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping("/primeni/{amdID}")
    public ResponseEntity applyAmandman(@PathVariable String amdID) throws JAXBException, FileNotFoundException {
        System.out.println("amdID = " + amdID);
        Amandman amd = amandmanService.getOne(amdID);
        amandmanService.applyAmandman(amd);

        return new ResponseEntity<>( HttpStatus.OK);

    }
}
