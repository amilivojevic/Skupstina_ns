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
import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.model.Sednica;
import com.tim_wro.skupstina.repository.SednicaRepository;
import com.tim_wro.skupstina.util.Connection;
import com.tim_wro.skupstina.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class SednicaService {

    private SednicaRepository sednicaRepository;
    public static final String RDF_XSL = "src/main/resources/schemes/sednica.xsl";

    @Autowired
    public SednicaService(SednicaRepository sednicaRepository) {
        this.sednicaRepository = sednicaRepository;
    }

    @Autowired
    public UserService userService;

 /*   public void writeInMarkLogicDB(File file) throws FileNotFoundException {
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
        String docId = "/sednica/sednica1.xml";
        String testDocId = "/example/test/books.xml";

        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = new InputStreamHandle(new FileInputStream(file));

        // Write the document to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \"" + props.database + "\" database.");
        xmlManager.write(docId, handle);

        System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + docId);
        // Release the client
        client.release();
    } */

    public int brojSednice(){

        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace s = \"http://www.skustinans.rs/sednice\";\n//s:sednica");

        int brojSednice = 0;
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            brojSednice++;
        }

        return brojSednice;
    }

    public List<Sednica> getAll(){
        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace s = \"http://www.skustinans.rs/sednice\";\n//s:sednica");

        final List<Sednica> sednice = new ArrayList<>();
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            final Sednica sednica = JAXB.unmarshal(byteArrayInputStream, Sednica.class);
            System.out.println("Sendica: " + sednica.toString());
            sednice.add(sednica);
        }

        return sednice;
    }


    public Sednica findById(String redniBroj) throws JAXBException {

        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace s = \"http://www.skustinans.rs/sednice\";\n//s:sednica");

        Sednica sednica = null;
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            sednica = JAXB.unmarshal(byteArrayInputStream, Sednica.class);
            System.out.println("Sendica: " + sednica.toString());
            if(redniBroj.equals(sednica.getRedniBroj()))
            return sednica;
        }
        return sednica;
    }

    public void writeInMarkLogicDB(File file, String id) throws FileNotFoundException {
        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        int br = brojSednice() + 1;
        String docId = "/sednica/" + id + ".xml";

        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = new InputStreamHandle(new FileInputStream(file));

        // Write the document to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \" database.");
        xmlManager.write(docId, handle);

        // Release the client
        client.release();
    }

    // vraca listu sednica od odredjenog predsednika
    public List<Sednica> getByUser(String korisnickoIme){

        DatabaseClient client = Connection.getConnection();

     //   System.out.println("Korisnicko na backendu:" + korisnickoIme);

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace s = \"http://www.skustinans.rs/sednice\";\n//s:sednica");

        final List<Sednica> sedniceUsera = new ArrayList<>();
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            final Sednica sednica = JAXB.unmarshal(byteArrayInputStream, Sednica.class);

         //   System.out.println("korisnicko na sednici:" + sednica.getKorisnickoIme());
            if(sednica.getKorisnickoIme().equals(korisnickoIme)){

                sedniceUsera.add(sednica);

            }
        }
        return sedniceUsera;
    }

}
