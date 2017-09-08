package com.tim_wro.skupstina.controller.akt;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.util.EditableNamespaceContext;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.model.Sednica;
import com.tim_wro.skupstina.model.StanjeAkta;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.services.SednicaService;
import com.tim_wro.skupstina.services.UserService;
import com.tim_wro.skupstina.util.FOPReporter;
import com.tim_wro.skupstina.util.ResponseMessage;
import com.tim_wro.skupstina.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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


    @Autowired
    private UserService userService;

    @Autowired
    private SednicaService sednicaService;

    @PostMapping("/novi")
    public ResponseEntity create(@RequestBody Akt akt) throws Exception {

        if(akt.getId() != null){
            aktService.updateIdAndBroj(akt);
        }
        else{
            akt.setId("akt"+UUID.randomUUID().toString());
            aktService.updateIdAndBroj(akt);
        }

        //marshalling
        File file = new File("file.xml");
        JAXBContext jaxbContext = null;
        // Defining namespace mappings

        try {
            jaxbContext = JAXBContext.newInstance(Akt.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.skustinans.rs/akti file:/C:/Users/Nina/Desktop/XML/Skupstina_ns/src/main/java/com/tim_wro/skupstina/model/akt_backup.xsd");

            jaxbMarshaller.marshal(akt, file);
            jaxbMarshaller.marshal(akt, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        //writing in marklogic db

        aktService.writeInMarkLogicDB(file, akt.getId());

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);

    }

    @PostMapping("/obrisi")
    public ResponseEntity delete(@RequestBody Akt akt) throws FileNotFoundException {

        aktService.deleteFromDB(akt);


        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);


    }



    @GetMapping("/svi")
    public ResponseEntity getAll() throws JAXBException {
        System.out.println("pre aktService.getAll();");
        List<Akt> lista = aktService.getAll();
        System.out.println("posle aktService.getAll();");
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    // vraca listu akata zakacenu na odredjenu sednicu
    @RequestMapping(value = "/svi_u_proceduri/{id}", method = RequestMethod.GET)
    public ResponseEntity getAllPrepared(@PathVariable("id") String id) throws JAXBException {
     List<Akt> lista = aktService.getBySednicaRedniBroj(id);
        List<Akt> aktiUProceduri = new ArrayList<>();
        for(Akt o : lista){
            if(o.getStanje() == StanjeAkta.U_PROCEDURI){
                aktiUProceduri.add(o);
            }
        }

        return new ResponseEntity<>(aktiUProceduri,HttpStatus.OK);
    }

    // vraca listu akata zakacenu na odredjenu sednicu
    @RequestMapping(value = "/svi_u_nacelu/{id}", method = RequestMethod.GET)
    public ResponseEntity getAllUNacelu(@PathVariable("id") String id) throws JAXBException {
        List<Akt> lista = aktService.getBySednicaRedniBroj(id);
        List<Akt> aktiUNacelu = new ArrayList<>();
        for(Akt o : lista){
            if(o.getStanje() == StanjeAkta.U_NACELU){
                aktiUNacelu.add(o);
            }
        }

        return new ResponseEntity<>(aktiUNacelu,HttpStatus.OK);
    }

    // vraca listu akata u proceduri odredjenog usera
    @RequestMapping(value = "/svi_u_proceduri", method = RequestMethod.GET)
    public ResponseEntity getAllByUser(@RequestHeader("X-Auth-Token") String token) throws JAXBException {

        Korisnik k = userService.findByToken(token);

        List<Akt> aktiUsera = aktService.getByUser(k.getKorisnickoIme());
        List<Akt> aktiUProceduri = new ArrayList<>();
        for(Akt a : aktiUsera){
            if(a.getStanje() == StanjeAkta.U_PROCEDURI){
                aktiUProceduri.add(a);
            }
        }

        return new ResponseEntity<>(aktiUProceduri,HttpStatus.OK);
    }

    // vraca listu akata u celosti odredjenog usera
    @RequestMapping(value = "/svi_u_celosti", method = RequestMethod.GET)
    public ResponseEntity getAllUCelosti()  {

        List<Akt> akti = aktService.getAll();
        List<Akt> aktiUCelosti = new ArrayList<>();

        for(Akt a : akti){
            if(a.getStanje() == StanjeAkta.U_CELOSTI){
                aktiUCelosti.add(a);
            }
        }
        return new ResponseEntity<>(aktiUCelosti,HttpStatus.OK);
    }

    @RequestMapping(value = "/trazi/naziv/{naziv}", method=RequestMethod.GET)
    public ResponseEntity searchByNaziv(@PathVariable String naziv){
        System.out.println("usao u kontroler");
        aktService.getByNaziv(naziv);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value ="/download-pdf/{id}", method=RequestMethod.GET)
    public ResponseEntity<byte[]> downloadPDF(@PathVariable("id") String id) throws Exception {


        FOPReporter fopReporter = new FOPReporter();

        Akt akt =  aktService.getById(id);
//        System.out.println("Stigao akt sa idem" + akt.getId());
        String docId = "/akt/" + akt.getId() + ".xml";
        String patch = "";

        //marshalling
        JAXBContext jaxbContext = null;
        try {

            jaxbContext = JAXBContext.newInstance(Akt.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(akt, sw);

            patch = sw.toString();
            System.out.println("patch " + patch);


            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }


        byte[] contents =   fopReporter.generatePDF(patch);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value ="/download-xhtml/{id}", method=RequestMethod.GET)
    public ResponseEntity<String> downloadXHTML(@PathVariable("id") String id) throws Exception {


        FOPReporter fopReporter = new FOPReporter();

        Akt akt =  aktService.getById(id);

        String patch = "";

        //marshalling
        JAXBContext jaxbContext = null;
        try {

            jaxbContext = JAXBContext.newInstance(Akt.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(akt, sw);

            patch = sw.toString();
            System.out.println("patch " + patch);


            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }


        String contents =   fopReporter.generateHTML(patch);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/html"));
        ResponseEntity<String> response = new ResponseEntity<String>(contents, headers, HttpStatus.OK);
        return response;
    }

}






