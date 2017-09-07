package com.tim_wro.skupstina.services;


import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Amandman;
import com.tim_wro.skupstina.model.StavkaAmandmana;
import com.tim_wro.skupstina.model.TipIzmene;
import com.tim_wro.skupstina.model.Sednica;
import com.tim_wro.skupstina.util.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AmandmanService {

    @Autowired
    private SednicaService sednicaService;


    public void updateAmandman(Amandman amandman) throws JAXBException {

        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        String docId = "/amandman/" + amandman.getId() + ".xml";

        // Defining namespace mappings
        EditableNamespaceContext namespaces = new EditableNamespaceContext();
        namespaces.put("amd", "http://www.skustinans.rs/amandmani");

        // Assigning namespaces to patch builder
        DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
        patchBuilder.setNamespaces(namespaces);

        String patch = "";

        //marshalling
        JAXBContext jaxbContext = null;
        try {

            jaxbContext = JAXBContext.newInstance(Amandman.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(amandman, sw);

            patch = sw.toString();
            System.out.println("patch " + patch);

            patch = patch.substring(patch.indexOf("<amandman"));

            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        // Defining XPath context
        String contextXPath = "/amd:amandman";
        DocumentPatchHandle patchHandle;

        // insert fragment
        patchBuilder.replaceFragment(contextXPath, patch);

        patchHandle = patchBuilder.build();

        xmlManager.patch(docId, patchHandle);

        client.release();

    }

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


    public void applyAmandman(Amandman amd, Akt akt) {
        for (StavkaAmandmana stavka : amd.getStavke().getStavkaAmandmana()) {
            if (stavka.getTipIzmene() == TipIzmene.BRISANJE) {

            } else if (stavka.getTipIzmene() == TipIzmene.DODAVANJE) {
                System.out.println("");
            } else if (stavka.getTipIzmene() == TipIzmene.IZMENA) {

            }

        }
    }

    public List<Amandman> getBySednicaRedniBroj(String id) throws JAXBException {

        Sednica sednica = sednicaService.findById(id);
        System.out.println("glupa sednica " + sednica.toString());

        List<Akt> aktiSednice = sednica.getAkt();

        List<Amandman> amandmaniOdSednice = new ArrayList<>();

        for(Akt a: aktiSednice){
            if(a.getAmandmanID() != null && a.getAmandmanID().size() > 0){

                for(String amandmanID : a.getAmandmanID()){
                    Amandman amandman = findById(amandmanID);
                    amandmaniOdSednice.add(amandman);
                }
            }
        }

        System.out.println("amandmani od te sednice " + amandmaniOdSednice);

        return amandmaniOdSednice;

    }

    public Amandman findById(String id) throws JAXBException {

        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace amd = \"http://www.skustinans.rs/amandmani\";\n//amd:amandman");

        Amandman amandman = null;
        final EvalResultIterator eval = call.eval();
        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            amandman = JAXB.unmarshal(byteArrayInputStream, Amandman.class);
       //     System.out.println("Sendica: " + amandman.toString());
            if (id != null && id.equals(amandman.getId()))
                return amandman;
        }
        return amandman;
    }

    // vraca listu amandamana od odredjenog korisnika
    public List<Amandman> getByUser(String korisnickoIme){

        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace amd = \"http://www.skustinans.rs/amandmani\";\n//amd:amandman");

        final List<Amandman> amandmaniUsera = new ArrayList<>();
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            final Amandman amandman = JAXB.unmarshal(byteArrayInputStream, Amandman.class);

            if(amandman.getKreirao().equals(korisnickoIme)){
                amandmaniUsera.add(amandman);
            }
        }
        System.out.println("Amandmani usera " + amandmaniUsera);
        return new ArrayList<Amandman>(amandmaniUsera);
    }


    public void deleteFromDB(Amandman amandman) throws FileNotFoundException {

        DatabaseClient client = Connection.getConnection();

        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        String docId = "/amandman/" + amandman.getId() + ".xml";

        xmlManager.delete(docId);

        client.release();
    }
}
