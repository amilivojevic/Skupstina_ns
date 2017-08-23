package com.tim_wro.skupstina.controller.akt;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.tim_wro.skupstina.dto.akt.AktDTO;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.services.AktService;
import com.tim_wro.skupstina.util.ResponseMessage;
import com.tim_wro.skupstina.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


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
    public ResponseEntity create(@RequestBody Akt akt) throws FileNotFoundException {

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

        aktService.writeInMarkLogicDB(file);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(akt.toString()), HttpStatus.CREATED);


    }

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
        client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);


        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // A handle to receive the document's content.
        DOMHandle content = new DOMHandle();

        // A metadata handle for metadata retrieval
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        // A document URI identifier.
        String docId = "/akt/akt1.xml";

        // Write the document to the database
        System.out.println("[INFO] Retrieving \"" + docId + "\" from "
                + (props.database.equals("") ? "default" : props.database)
                + " database.");

        xmlManager.read(docId, metadata, content);

        // Retrieving a document node form DOM handle.
        Document doc = content.get();

		/*
		 * A collection defines a set of documents in the database. You can set
		 * documents to be in any number of collections either at the time the
		 * document is created or by updating it.
		 *
		 */

        // Reading metadata
        System.out.println("[INFO] Assigned collections: " + metadata.getCollections());

        // Serializing DOM tree to standard output.
        System.out.println("[INFO] Retrieved content:");
        aktService.transform(doc, System.out);

        // Release the client
        client.release();

        System.out.println("[INFO] End.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/svi")
    public ResponseEntity getAll() throws JAXBException {


            //List<Akt> li = aktService.findAll();
            aktService.getOne("akt1.xml");


        return new ResponseEntity<>(HttpStatus.OK);
    }
}






