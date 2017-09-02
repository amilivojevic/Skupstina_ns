package com.tim_wro.skupstina.controller.sednica;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        // menjanje elementa akta predlozen na true
        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        String docId = "/akt/" + akt.getId() + ".xml";

        // Defining namespace mappings
        EditableNamespaceContext namespaces = new EditableNamespaceContext();
        namespaces.put("a", "http://www.skustinans.rs/akti");
        namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

        // Assigning namespaces to patch builder
        DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
        patchBuilder.setNamespaces(namespaces);

        // Creating an XML patch
        akt.setPredlozen(true);
        Boolean patch = akt.isPredlozen();

        // Defining XPath context
        String contextXPath1 = "/a:akt/a:predlozen";

        DocumentPatchHandle patchHandle;

        // Replace fragment
        patchBuilder.replaceFragment(contextXPath1, patch);

        patchHandle = patchBuilder.build();

        xmlManager.patch(docId, patchHandle);

        client.release();

        ////////////////////////////////////////////////////////////////
        ///// dodavanje tog akta u listu akata sednice
        Sednica sednica = sednicaService.findById(predlogAktaDTO.getSednicaRB());

        System.out.println("lista akata za tu sednicu " + sednica.getAkt());

        ///////////////////////////////////////////////////////////////
        // kacenje selektovanog akta na sednicu
        // !!!!!! izmeniti jednom kada sema bude gotoga
        client = Connection.getConnection();

        // Create a document manager to work with XML files.
        xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        docId = "/sednica/" + sednica.getId() + ".xml";

        // Defining namespace mappings
        namespaces.put("s", "http://www.skustinans.rs/sednice");

        // Assigning namespaces to patch builder
        patchBuilder = xmlManager.newPatchBuilder();
        patchBuilder.setNamespaces(namespaces);

        // Creating an XML patch
        String patch2 = "\t<akt id=\"" + akt.getId() + "\" naziv=\"" + akt.getNaziv() + "\" drzava=\"" +
                akt.getDrzava() + "\" regija=\"" + akt.getRegija() + "\" grad=\"" + akt.getGrad() + "\" stanje=\"" +
                akt.getStanje() + "\" kreirao=\"" + akt.getKreirao() + "\">\n" +

                "\t\t<preambula>\n" +
                "\t\t\t<pravni_osnov>" + akt.getPreambula().getPravniOsnov() + "</pravni_osnov>\n" +
                "\t\t\t<organ>" + akt.getPreambula().getOrgan() + "</organ>\n" +
                "\t\t\t<oblast>" + akt.getPreambula().getOblast() + "</oblast>\n" +
                "\t\t</preambula>\n" +
                "\t\t<predlozen>" + akt.isPredlozen() + "</predlozen>\n" +
                "\t</akt>\n";

        sednica.getAkt().add(akt);
        List<Akt> aktiSednice =  sednica.getAkt();
        System.out.println("akti sednice " + aktiSednice);
     //   List<Akt> patch2 = aktiSednice ;

        // Defining XPath context
        String contextXPath2 = "/s:sednica/s:stanje";

        // insert fragment
        patchBuilder.insertFragment(contextXPath2, DocumentPatchBuilder.Position.BEFORE, patch2);

        patchHandle = patchBuilder.build();

        xmlManager.patch(docId, patchHandle);

        client.release();
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);
    }

    // dodaje akt na sednicu i stavlja mu status predlozen
    @RequestMapping(value = "/otkazi", method = RequestMethod.POST)
    public ResponseEntity otkaziAkt(@RequestBody PredlogAktaDTO predlogAktaDTO) throws JAXBException {

        Akt akt = aktService.getById(predlogAktaDTO.getAktID());
        akt.setPredlozen(false);

        List<Sednica> sednice = sednicaService.getAll();
        List<Akt> aktiSednice;
        Sednica sednica = new Sednica();
        for(Sednica s : sednice){
            System.out.println("usao u for");
            aktiSednice = s.getAkt();
            System.out.println("akti od trenutne sednice");
            for(Akt a : aktiSednice){
                if(a.getId().equals(akt.getId())){
                    System.out.println("nasao sednicu!");
                    sednica = s;
                }
            }
        }

        sednica.getAkt().remove(akt);
        System.out.println("lista akata za tu sednicu " + sednica.getAkt());

        //marshalling
        File file = new File("file.xml");
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

        //writing in marklogic db

        try {
            aktService.writeInMarkLogicDB(file, akt.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File file1 = new File("file1.xml");
        JAXBContext jaxbContext1 = null;
        try {
            jaxbContext1 = JAXBContext.newInstance(Sednica.class);

            Marshaller jaxbMarshaller1 = jaxbContext1.createMarshaller();

            // output pretty printed
            jaxbMarshaller1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller1.marshal(sednica, file1);
            jaxbMarshaller1.marshal(sednica, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        try {
            sednicaService.writeInMarkLogicDB(file1, sednica.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);
    }

  /*  @RequestMapping(value = "/aktiviraj/{redniBroj}", method = RequestMethod.POST)
    public ResponseEntity aktivirajSednicu(@PathVariable("redniBroj") String redniBroj) throws JAXBException {

        Sednica sednica = sednicaService.findById(redniBroj);

        sednica.setStanje(StanjeSednice.AKTIVNA);

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
        try {
            sednicaService.writeInMarkLogicDB(file, sednica.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);
    } */

    @PostMapping("cfirst_voting")
    public ResponseEntity votingOne(@RequestBody Sednica sednica) throws FileNotFoundException {

  /*      //marshalling
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

        sednicaService.writeInMarkLogicDB(file); */

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);


    }


    @PostMapping("/aktiviraj/{redniBroj}")
    public ResponseEntity aktiviraj(@PathVariable("redniBroj") String redniBroj) throws JAXBException {


        DatabaseClient client = Connection.getConnection();

        Sednica sednica = sednicaService.findById(redniBroj);
        System.out.println("sednica je " + sednica.toString());

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        String docId = "/sednica/" + sednica.getId() + ".xml";
        System.out.println("uzeo sednicu " + sednica.getId());

        // Defining namespace mappings
        EditableNamespaceContext namespaces = new EditableNamespaceContext();
        namespaces.put("s", "http://www.skustinans.rs/sednice");
        namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

        // Assigning namespaces to patch builder
        DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
        patchBuilder.setNamespaces(namespaces);

        // Creating an XML patch
        String patch2 = "aktivna";

        // Defining XPath context
        String contextXPath1 = "/s:sednica/s:stanje";

        DocumentPatchHandle patchHandle;

        // Replace fragment
        patchBuilder.replaceFragment(contextXPath1, patch2);

        // Remove fragment
        //patchBuilder.delete(contextXPath1);

        patchHandle = patchBuilder.build();

        xmlManager.patch(docId, patchHandle);

        client.release();

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(sednica.toString()), HttpStatus.CREATED);

    }
}
