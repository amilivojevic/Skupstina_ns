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
import com.tim_wro.skupstina.dto.SecondVotingDTO;
import com.tim_wro.skupstina.model.*;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.services.AmandmanService;
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
    private AmandmanService amandmanService;

    @Autowired
    public SednicaController(SednicaService sednicaService){
        this.sednicaService = sednicaService;
    }


    @PostMapping("/nova")
    public ResponseEntity create(@RequestBody Sednica sednica) throws FileNotFoundException {

        sednica.setId("sedn"+UUID.randomUUID().toString());

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);

    }


    @RequestMapping(value = "/voting/first_voting", method = RequestMethod.POST)
    public ResponseEntity votingOne(@RequestBody FirstVotingDTO firstVotingDTO) throws JAXBException, FileNotFoundException {


        boolean izglasanAkt = sednicaService.checkIfIzglasan(firstVotingDTO);

        Akt akt = aktService.getById(firstVotingDTO.getAktID());
        Sednica s = sednicaService.findById(firstVotingDTO.getSednicaID());
        List<Akt> aktiSednice = s.getAkt();

        if(izglasanAkt){
            for(Akt a : aktiSednice) {
                if (a.getId().equals(akt.getId())) {

                    a.setStanje(StanjeAkta.U_NACELU);
                }
            }
        }else{
            for(int i = 0; i<aktiSednice.size(); i++){
                if(aktiSednice.get(i).getId().equals(akt.getId())){

                    // pobrisi amandmane nakacene na taj akt
                    List<String> amandmaniAkta = akt.getAmandmanID();
                    for(String amandmanID : amandmaniAkta){
                        Amandman a = amandmanService.findById(amandmanID);
                        amandmanService.deleteFromDB(a);
                    }
                    aktiSednice.remove(i);
                }
            }
        }

        sednicaService.updateSednica(s);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Success"), HttpStatus.OK);

    }

    @RequestMapping(value = "/voting/second_voting", method = RequestMethod.POST)
    public ResponseEntity votingSecond(@RequestBody SecondVotingDTO secondVotingDTO) throws JAXBException {

        boolean izglasanAmandman = sednicaService.checkIfIzglasanAmandman(secondVotingDTO);

        Amandman amandman = amandmanService.findById(secondVotingDTO.getAmandmanID());

        Akt akt = aktService.getById(amandman.getAktID());

        Sednica s = sednicaService.findById(secondVotingDTO.getSednicaID());
        List<Akt> listaAkata = s.getAkt();


        if(izglasanAmandman){
            amandman.setStanje(StanjeAmandmana.PRIHVACEN);
        }else{

            amandman.setStanje(StanjeAmandmana.ODBIJEN);
            // obrisi je iz liste sednica
            for(int i = 0; i < listaAkata.size(); i++)
            {
                if(listaAkata.get(i).getId().equals(akt.getId())){
                    listaAkata.remove(i);
                    sednicaService.updateSednica(s);
                }

            }

            List<String> amandmaniAkta = akt.getAmandmanID();
            for(int i = 0; i < amandmaniAkta.size(); i++){
                if(amandmaniAkta.get(i).equals(amandman.getId())){
                    amandmaniAkta.remove(i);
                    System.out.println("amandmani akt " + akt.getId() + amandmaniAkta);
                    s.getAkt().add(akt);
                    break;
                }
            }
        }

        amandmanService.updateAmandman(amandman);
        sednicaService.updateSednica(s);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Success"), HttpStatus.OK);

    }

    @RequestMapping(value = "/voting/third_voting", method = RequestMethod.POST)
    public ResponseEntity votingThird(@RequestBody FirstVotingDTO firstVotingDTO) throws JAXBException, FileNotFoundException {


        boolean izglasanAkt = sednicaService.checkIfIzglasanThird(firstVotingDTO);
        System.out.println("vratio izglasan " + izglasanAkt);

        Akt akt = aktService.getById(firstVotingDTO.getAktID());
        Sednica s = sednicaService.findById(firstVotingDTO.getSednicaID());
        List<Akt> aktiSednice = s.getAkt();

        Akt a = new Akt();

        if(izglasanAkt) {

            //    for (Akt a : aktiSednice) {

            for (int j = 0; j < aktiSednice.size(); j++){
                a = aktService.getById(aktiSednice.get(j).getId());
                if (a.getId().equals(akt.getId())) {
                    a.setStanje(StanjeAkta.U_CELOSTI);
                    a.setDatumIzglasan(s.getDatum());

                    aktController.create(a);

                    // obrisi je iz liste sednica
                    for (int i = 0; i < aktiSednice.size(); i++) {
                        if (aktiSednice.get(i).getId().equals(akt.getId())) {
                            aktiSednice.remove(i);
                            sednicaService.updateSednica(s);
                            break;
                        }

                    }
                }
            }
        }
        else{
     //       for(Akt a1 : aktiSednice) {
            for (int j = 0; j < aktiSednice.size(); j++){
                a = aktService.getById(aktiSednice.get(j).getId());
                if (a.getId().equals(akt.getId())) {
                    a.setStanje(StanjeAkta.ODBIJEN);
                    a.setDatumIzglasan(s.getDatum());

                    aktController.create(a);

                    // obrisi je iz liste sednica
                    for(int i = 0; i < aktiSednice.size(); i++)
                    {
                        if(aktiSednice.get(i).getId().equals(akt.getId())){
                            aktiSednice.remove(i);
                            sednicaService.updateSednica(s);
                        }

                    }

                }
            }
        }

        sednicaService.updateSednica(s);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Success"), HttpStatus.OK);

    }

    @PostMapping("/aktiviraj/{redniBroj}")
    public ResponseEntity aktiviraj(@PathVariable("redniBroj") String redniBroj) throws JAXBException {

        Sednica sednica = sednicaService.findById(redniBroj);
        sednica.setStanje(StanjeSednice.AKTIVNA);
        sednicaService.updateSednica(sednica);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);

    }

    @PostMapping("/zavrsi/{id}")
    public ResponseEntity zavrsi(@PathVariable("id") String id) throws JAXBException {

        Sednica sednica = sednicaService.findById(id);
        System.out.println("Nasao sednicu sa rbr " + sednica.toString());
        sednica.setStanje(StanjeSednice.ZAVRSENA);
        sednicaService.updateSednica(sednica);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);

    }
}
