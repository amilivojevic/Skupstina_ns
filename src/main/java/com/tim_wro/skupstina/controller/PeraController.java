package com.tim_wro.skupstina.controller;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.tim_wro.skupstina.model.Pera;
import com.tim_wro.skupstina.util.ResponseMessage;
import com.tim_wro.skupstina.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/pera")
public class PeraController {


    @PostMapping("/dodaj")
    public ResponseEntity create(@RequestBody PeraController p) throws FileNotFoundException {

        //writing in marklogic db

        writeInMarkLogicDB(new File("pera1.xml"));

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(p.toString()), HttpStatus.CREATED);

    }

    @GetMapping("/citaj")
    public ResponseEntity read() throws FileNotFoundException, JAXBException {

        //writing in marklogic db

        Pera p = readFromMarkLogicDB("pera/pera1.xml");

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(p.toString()), HttpStatus.CREATED);

    }

    public Pera readFromMarkLogicDB(String uri) throws JAXBException {

        DatabaseClient client;
        Util.ConnectionProperties props = null;
        try {
            props = Util.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the database client
        System.out.println("[INFO] Using default database.");
        client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // A JAXB handle to receive the document's content.
        JAXBContext context = JAXBContext.newInstance("com.tim_wro.skupstina.model");
        JAXBHandle<Pera> handle = new JAXBHandle<Pera>(context);


        // A metadata handle for metadata retrieval
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        // A document URI identifier.
        String docId = "/pera/pera1.xml";

        // Write the document to the database
        System.out.println("[INFO] Retrieving \"" + docId + "\" from "
                + (props.database.equals("") ? "default" : props.database)
                + " database.");

        xmlManager.read(docId, metadata, handle);

        // Retrieving a Bookstore instance
        Pera p = handle.get();

        // Reading metadata
        System.out.println("[INFO] Assigned collections: " + metadata.getCollections());

        // Serializing DOM tree to standard output.
        System.out.println("[INFO] Retrieved content:");
        System.out.println(p.toString());

        // Release the client
        client.release();

        System.out.println("[INFO] End.");
        return p;
    }

    public void writeInMarkLogicDB(File file) throws FileNotFoundException {
        DatabaseClient client;
        Util.ConnectionProperties props = null;
        try {
            props = Util.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the database client
        if (props.database.equals("")) {
            System.out.println("[INFO] Using default database.");
            client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
        } else {
            System.out.println("[INFO] Using \"" + props.database + "\" database.");
            client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
        }

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        String docId = "/pera/pera1.xml";

        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = new InputStreamHandle(new FileInputStream("data/instance1.xml"));

        // Write the document to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \"" + props.database + "\" database.");
        xmlManager.write(docId, handle);


        System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + docId);
        // Release the client
        client.release();

        System.out.println("[INFO] End.");
    }
}
