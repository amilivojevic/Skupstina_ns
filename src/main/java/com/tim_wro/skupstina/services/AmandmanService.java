package com.tim_wro.skupstina.services;


import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Amandman;
import com.tim_wro.skupstina.model.StavkaAmandmana;
import com.tim_wro.skupstina.model.TipIzmene;
import com.tim_wro.skupstina.model.Sednica;
import com.tim_wro.skupstina.util.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmandmanService {

    @Autowired
    private SednicaService sednicaService;

    public void writeInMarkLogicDB(File file, String id) throws FileNotFoundException {
        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        String docId = "/amandman/" + id + ".xml";

        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = new InputStreamHandle(new FileInputStream(file));

        // Write the document to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \" database.");
        xmlManager.write(docId, handle);

        // Release the client
        client.release();
    }
    public List<Amandman> getAll(){
        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace a = \"http://www.skustinans.rs/amandmani\";\n//a:amandman");

        final List<Amandman> amd = new ArrayList<>();
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            final Amandman act = JAXB.unmarshal(byteArrayInputStream, Amandman.class);
            System.out.println("Amd: " + act.toString());
            amd.add(act);
        }

        return amd;
    }

    public Amandman getOne(String imeAmandmana) throws JAXBException {
        DatabaseClient client = Connection.getConnection();
        final XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // A JAXB handle to receive the document's content.
        JAXBContext context = JAXBContext.newInstance("com.tim_wro.skupstina.model");
        JAXBHandle<Amandman> handle = new JAXBHandle<Amandman>(context);


        // A metadata handle for metadata retrieval
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        // A document URI identifier.
        String docId = "/amandman/" + imeAmandmana;

        xmlManager.read(docId, metadata,handle);

        Amandman amd = handle.get();

        // Release the client
        client.release();

        return amd;
    }

    public void applyAmandman(Amandman amd, Document aktDoc) {

        for (StavkaAmandmana stavka : amd.getStavke().getStavkaAmandmana()) {
            if (stavka.getTipIzmene() == TipIzmene.BRISANJE) {

/*
                for ( int br=0; br< aktDoc.getElementsByTagName("b:book").getLength(); br++) {
                    NamedNodeMap attributes = doc.getElementsByTagName("b:book").item(br).getAttributes();
                    Node attr = attributes.getNamedItem("category");
                    if(attr != null && attr.getTextContent().equals("CHILDREN")) {
                        System.out.println("****** " + attr.getTextContent());
                        doc.getElementsByTagName("b:book").item(br).removeChild(
                                doc.getElementsByTagName("b:title").item(1)
                        );
                    }
                }*/



            } else if (stavka.getTipIzmene() == TipIzmene.DODAVANJE) {
                System.out.println("JOS UVEK NE RADI");
            } else if (stavka.getTipIzmene() == TipIzmene.IZMENA) {
                System.out.println("JOS UVEK NE RADI");
            }

        }
    }

    public List<Amandman> getBySednicaRedniBroj(String id) throws JAXBException {

        Sednica sednica = sednicaService.findById(id);
        System.out.println("glupa sednica " + sednica.toString());

        List<Akt> aktiSednice = sednica.getAkt();

        List<Amandman> amandmaniOdSednice = new ArrayList<>();

        for(Akt a : aktiSednice){
            amandmaniOdSednice.addAll(a.getAmandman());
        }

        System.out.println("amandmani od te sednice " + amandmaniOdSednice);

        return amandmaniOdSednice;

    }
}
