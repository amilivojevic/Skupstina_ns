package com.tim_wro.skupstina.controller.akt;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.util.EditableNamespaceContext;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.model.StanjeAkta;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.services.UserService;
import com.tim_wro.skupstina.util.ResponseMessage;
import com.tim_wro.skupstina.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @PostMapping("/novi")
    public ResponseEntity create(@RequestBody Akt akt) throws FileNotFoundException {

        akt.setId("akt"+UUID.randomUUID().toString());
        aktService.updateIdAndBroj(akt);

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

    // hardcode-ovano da se ucita iz baze akt sa id-om /akt/akt1.xml!
    @RequestMapping(value = "/jedan", method = RequestMethod.GET)
    public ResponseEntity findByIdAct()
            throws JAXBException, IOException, SAXException {

        DatabaseClient client;
        Util.ConnectionProperties props = null;
        try {
            props = Util.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Initialize the database client
        client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);


        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // A JAXB handle to receive the document's content.
        JAXBContext context = JAXBContext.newInstance("com.tim_wro.skupstina.model");
        JAXBHandle<Akt> handle = new JAXBHandle<Akt>(context);

        // A metadata handle for metadata retrieval
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        // A document URI identifier.
        String docId = "/akt/akt1.xml";

        // Write the document to the database
        System.out.println("[INFO] Retrieving \"" + docId + "\" from "
                + (props.database.equals("") ? "default" : props.database)
                + " database.");

        xmlManager.read(docId, metadata, handle);

        // Retrieving a Act instance
        Akt a = handle.get();

        // Reading metadata
        System.out.println("[INFO] Assigned collections: " + metadata.getCollections());

        // Serializing DOM tree to standard output.
        System.out.println("[INFO] Retrieved content:");
        System.out.println(a.toString());

        // Release the client
        client.release();

        System.out.println("[INFO] End.");
        return new ResponseEntity<>(HttpStatus.OK);
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
}






