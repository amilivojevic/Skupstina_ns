package com.tim_wro.skupstina.services;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.repository.AktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim_wro.skupstina.util.*;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nina on 16-Jun-17.
 */

@Service
public class AktService {

    private AktRepository aktRepository;
    public static final String RDF_XSL = "src/main/resources/schemes/akt.xsl";
    private static TransformerFactory transformerFactory;

    static {
        transformerFactory = TransformerFactory.newInstance();
    }

    @Autowired
    public AktService(AktRepository aktRepository) {
        this.aktRepository = aktRepository;

    }

    public int brojAkata(){
        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace a = \"http://www.skustinans.rs/akti\";\n//a:akt");

        int brojAkata = 0;
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            brojAkata++;
        }

        return brojAkata;
    }

    public List<Akt> getAll(){
        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace a = \"http://www.skustinans.rs/akti\";\n//a:akt");

        final List<Akt> akti = new ArrayList<>();
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            final Akt act = JAXB.unmarshal(byteArrayInputStream, Akt.class);
            System.out.println("Akt: " + act.toString());
            akti.add(act);
        }

        return akti;
    }


    public Akt getOne(String imeAkta) throws JAXBException {
        DatabaseClient client = Connection.getConnection();
        final XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // A JAXB handle to receive the document's content.
        JAXBContext context = JAXBContext.newInstance("com.tim_wro.skupstina.model");
        JAXBHandle<Akt> handle = new JAXBHandle<Akt>(context);


        // A metadata handle for metadata retrieval
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        // A document URI identifier.
        String docId = "/akt/akt1.xml";

        xmlManager.read(docId, metadata,handle);

        // Retrieving a Bookstore instance
        Akt akt = handle.get();

        // Reading metadata
        System.out.println("[INFO] Assigned collections: " + metadata.getCollections());

        // Serializing DOM tree to standard output.
        System.out.println("[INFO] Retrieved content:");
        System.out.println(akt);

        // Release the client
        client.release();

        System.out.println("[INFO] End.");

        return akt;
    }

    public void writeInMarkLogicDB(File file) throws FileNotFoundException {
        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        int br = brojAkata() + 1;
        String docId = "/akt/akt" + br + ".xml";

        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = new InputStreamHandle(new FileInputStream(file));

        // Write the document to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \" database.");
        xmlManager.write(docId, handle);

        // Release the client
        client.release();
    }

}