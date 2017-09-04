package com.tim_wro.skupstina.controller.sednica;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;
import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import com.tim_wro.skupstina.controller.akt.AktController;
import com.tim_wro.skupstina.dto.FirstVotingDTO;
import com.tim_wro.skupstina.dto.PredlogAktaDTO;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.model.Sednica;
import com.tim_wro.skupstina.model.StanjeSednice;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.services.SednicaService;
import com.tim_wro.skupstina.services.UserService;
import com.tim_wro.skupstina.util.Connection;
import com.tim_wro.skupstina.util.ResponseMessage;
import com.tim_wro.skupstina.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import sun.misc.FloatingDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sednica")
public class SednicaController {

    private final SednicaService sednicaService;

    @Autowired
    public UserService userService;

    @Autowired
    private AktService aktService;

    @Autowired
    private AktController aktController;

    @Autowired
    public SednicaController(SednicaService sednicaService){
        this.sednicaService = sednicaService;
    }


    @PostMapping("/nova")
    public ResponseEntity create(@RequestBody Sednica sednica) throws FileNotFoundException {

        sednica.setId(UUID.randomUUID().toString());

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

        sednicaService.writeInMarkLogicDB(file, sednica.getId());

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);


    }

    // hardcode-ovano da se ucita iz baze akt sa id-om /akt/akt1.xml!
    @RequestMapping(value = "/jedan", method = RequestMethod.GET)
    public ResponseEntity findByIdSednica()
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
        JAXBHandle<Sednica> handle = new JAXBHandle<Sednica>(context);

        // A metadata handle for metadata retrieval
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        // A document URI identifier.
        String docId = "/sednica/sednica1.xml";

        // Write the document to the database
        System.out.println("[INFO] Retrieving \"" + docId + "\" from "
                + (props.database.equals("") ? "default" : props.database)
                + " database.");

        xmlManager.read(docId, metadata, handle);

        // Retrieving a Act instance
        Sednica sednica = handle.get();

        // Reading metadata
        System.out.println("[INFO] Assigned collections: " + metadata.getCollections());

        // Serializing DOM tree to standard output.
        System.out.println("[INFO] Retrieved content:");
        System.out.println(sednica.toString());

        // Release the client
        client.release();

        System.out.println("[INFO] End.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sve")
    public ResponseEntity getAll() throws JAXBException {
        System.out.println("pre sednicaService.getAll();");
        List<Sednica> lista = sednicaService.getAll();
        System.out.println("posle sednicaService.getAll();");
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    // vraca sve zakazane sednice od odredjenog usera
    @RequestMapping(value = "/sve_od_usera", method = RequestMethod.GET)
    public ResponseEntity getAllForUser( @RequestHeader("X-Auth-Token") String token) throws JAXBException {

        Korisnik k = userService.findByToken(token);

        List<Sednica> sedniceUsera = sednicaService.getByUser(k.getKorisnickoIme());
        System.out.println("sednice od usera "+sedniceUsera);
        List<Sednica> zakazaneSednice = new ArrayList<>();
        for(Sednica s : sedniceUsera){
            if(s.getStanje() == StanjeSednice.ZAKAZANA){
                zakazaneSednice.add(s);
            }
        }
        System.out.println("zakazane sednice " + zakazaneSednice);
        return new ResponseEntity<>(zakazaneSednice,HttpStatus.OK);
    }

    // dodaje akt na sednicu i stavlja mu status predlozen
    @RequestMapping(value = "/predlozi", method = RequestMethod.POST)
    public ResponseEntity predloziAkt(@RequestBody PredlogAktaDTO predlogAktaDTO) throws JAXBException {

        /////////////////////////////////////////////////////////////////
        ///// menjanje elementa akta predlozen na true
        Akt akt = aktService.getById(predlogAktaDTO.getAktID());

        akt.setPredlozen(true);
     //   aktService.updateAkt(akt);


        ////////////////////////////////////////////////////////////////
        ///// dodavanje tog akta u listu akata sednice
        Sednica sednica = sednicaService.findById(predlogAktaDTO.getSednicaRB());

        sednica.getAkt().add(akt);
        System.out.println("Akti sednice posle dodavanja: " + sednica.getAkt());
        sednicaService.updateSednica(sednica);
        try {
            aktService.deleteFromDB(akt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);
    }

    // uklanja akt sa sednice i stavlja mu status na false
    @RequestMapping(value = "/otkazi", method = RequestMethod.POST)
    public ResponseEntity otkaziAkt(@RequestBody PredlogAktaDTO predlogAktaDTO) throws JAXBException {

        /////////////////////////////////////////////////////////////////
        ///// menjanje elementa akta predlozen na true
        Akt akt = aktService.getById(predlogAktaDTO.getAktID());
        akt.setPredlozen(false);

        ////////////////////////////////////////////////////////////////
        ///// uklanjanje tog akta iz listu akata sednice
        Sednica sednica = sednicaService.findById(predlogAktaDTO.getSednicaRB());
        List<Akt> aktiSednice = sednica.getAkt();

        System.out.println("Pre remove akt " + aktiSednice);
        // ne radi remove glupi!
        for(int i = 0; i<aktiSednice.size(); i++){
            if(aktiSednice.get(i).getId().equals(akt.getId())){
                aktiSednice.remove(i);
            }
        }


        System.out.println("Posle remove akt " + aktiSednice);
        System.out.println("Akti sednice posle uklanjanja" + sednica.getAkt());
        sednicaService.updateSednica(sednica);

        try {
            aktController.create(akt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);

    }

 /*   @PostMapping("first_voting")
    public ResponseEntity votingOne(@RequestBody FirstVotingDTO firstVotingDTO) throws JAXBException {


        sednicaService.checkIfIzglasan(firstVotingDTO);


        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);

    }*/


    @PostMapping("/aktiviraj/{redniBroj}")
    public ResponseEntity aktiviraj(@PathVariable("redniBroj") String redniBroj) throws JAXBException {

        Sednica sednica = sednicaService.findById(redniBroj);
        sednica.setStanje(StanjeSednice.AKTIVNA);
        sednicaService.updateSednica(sednica);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);

    }
}
