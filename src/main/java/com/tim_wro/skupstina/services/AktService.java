package com.tim_wro.skupstina.services;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;
import com.tim_wro.skupstina.model.*;
import com.tim_wro.skupstina.repository.AktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim_wro.skupstina.util.*;
import org.w3c.dom.Document;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Nina on 16-Jun-17.
 */

@Service
public class AktService {


    public static final String RDF_XSL = "src/main/resources/schemes/akt.xsl";
    private static TransformerFactory transformerFactory;



    @Autowired
    private SednicaService sednicaService;


    public void updateAkt(Akt akt) throws JAXBException {

        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        String docId = "/akt/" + akt.getId() + ".xml";

        // Defining namespace mappings
        EditableNamespaceContext namespaces = new EditableNamespaceContext();
     //   namespaces.put("a", "http://www.skustinans.rs/akti");
      //  namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

        // Assigning namespaces to patch builder
        DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
        patchBuilder.setNamespaces(namespaces);

        String patch = "";

        //marshalling
        JAXBContext jaxbContext = null;
        try {

            jaxbContext = JAXBContext.newInstance(Akt.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(akt, sw);

            patch = sw.toString();
            System.out.println("patch " + patch);

            patch = patch.substring(patch.indexOf("<akt"));

            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        // Defining XPath context
        String contextXPath = "/a:akt";
        DocumentPatchHandle patchHandle;

        // insert fragment
        patchBuilder.replaceFragment(contextXPath, patch);

        patchHandle = patchBuilder.build();

        xmlManager.patch(docId, patchHandle);

        client.release();

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

    public Document getAktDocument(String aktID){
        DatabaseClient client = Connection.getConnection();
        final XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        DOMHandle content = new DOMHandle();

        // A metadata handle for metadata retrieval
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        xmlManager.read("/amandman/"+aktID+".xml", metadata, content);

        // Retrieving a document node form DOM handle.
        Document doc = content.get();
        return doc;
    }

    public void writeInMarkLogicDB(File file, String id) throws FileNotFoundException {
        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        int br = brojAkata() + 1;
        //String docId = "/akt/akt" + br + ".xml";
        String docId = "/akt/" + id + ".xml";

        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = new InputStreamHandle(new FileInputStream(file));

        // Write the document to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \" database.");
        xmlManager.write(docId, handle);

        // Release the client
        client.release();
    }
    public void deleteFromDB(Akt akt) throws FileNotFoundException {
        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();


        String docId = "/akt/" + akt.getId() + ".xml";

        // Create an input stream handle to hold XML content.
     //   InputStreamHandle handle = new InputStreamHandle(new FileInputStream(file));

        // Write the document to the database
   //     System.out.println("[INFO] Inserting \"" + docId + "\" to \" database.");
     //   xmlManager.write(docId, handle);
        // Document deletion
      //  System.out.println("[INFO] Removing \"" + docId + "\" from \"" + docId.database + "\" database.");
        xmlManager.delete(docId);

//
        // Release the client
        client.release();
    }


    public List<Akt> getBySednicaRedniBroj(String id) throws JAXBException{

        Sednica sednica = sednicaService.findById(id);
        System.out.println("glupa sednica " + sednica.toString());

        List<Akt> aktiOdSednice = sednica.getAkt();
        System.out.println("akti od te sednice " + aktiOdSednice);

        return aktiOdSednice;
    }

    // vraca listu akata od odredjenog korisnika
    public List<Akt> getByUser(String korisnickoIme){

        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace a = \"http://www.skustinans.rs/akti\";\n//a:akt");

        final Set<Akt> aktiUsera = new HashSet<>();
        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            final Akt akt = JAXB.unmarshal(byteArrayInputStream, Akt.class);

            if(akt.getKreirao().equals(korisnickoIme)){
                aktiUsera.add(akt);
            }
        }
        System.out.println("Akti usera " + aktiUsera);
        return new ArrayList<Akt>(aktiUsera);
    }

    // vraca akt na osnovu ida
    public Akt getById(String id){

        DatabaseClient client = Connection.getConnection();

        final ServerEvaluationCall call = client.newServerEval();

        call.xquery("declare namespace a = \"http://www.skustinans.rs/akti\";\n//a:akt");

        final EvalResultIterator eval = call.eval();

        for (EvalResult evalResult : eval) {
            final String s = evalResult.getAs(String.class);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
            final Akt akt = JAXB.unmarshal(byteArrayInputStream, Akt.class);

            if(akt.getId().equals(id)){
                return akt;
            }
        }
        return null;

    }

    private void updateIdAndBrojCLAN(Clan c){
        if(c.getStav() != null){
            int stavBr =0;
            for(Stav s : c.getStav()){
                stavBr += 1;
                s.setId("stav" + (stavBr));

                if(s.getTacka() != null){
                    int tackaBr = 0;
                    for(Tacka t : s.getTacka()){
                        tackaBr += 1;
                        t.setBr(tackaBr);
                        t.setId("tacka" + (tackaBr));

                        if(t.getPodtacka() != null){
                            int podtackaBr = 0;
                            for(Podtacka pt : t.getPodtacka()){
                                podtackaBr += 1;
                                pt.setBr(podtackaBr);
                                pt.setId("podtacka" + (podtackaBr));

                                if(pt.getAlineja() != null){
                                    int alinejaBr = 0;
                                    for(Alineja a : pt.getAlineja()){
                                        alinejaBr +=1;
                                        a.setId("alineja" + alinejaBr);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public void updateIdAndBroj(Akt akt){

        if(akt.getClan() != null){
            int clanId = 0;
            for(Clan c : akt.getClan()){
                clanId += 1;
                c.setBr(clanId);
                c.setId("clan" + (clanId));

                updateIdAndBrojCLAN(c);
            }
        }

        if(akt.getDeo() != null){
            int deoBr = 0;
            for(Deo d : akt.getDeo()){
                deoBr += 1;
                d.setBr(deoBr);
                d.setId("deo" + (deoBr));

                if(d.getGlava() != null){
                    int glavaBr = 0;
                    for(Glava g : d.getGlava()){
                        glavaBr += 1;
                        g.setBr(glavaBr);
                        g.setId("glava" + (glavaBr));

                        //*******
                        if(g.getClan() != null){
                            int clanId = 0;
                            for(Clan c : g.getClan()){
                                clanId +=1;
                                c.setBr(clanId);
                                c.setId("clan" + (clanId));

                                updateIdAndBrojCLAN(c);
                            }
                        }
                        //*******


                        if(g.getOdeljak() != null){

                            int odeljakId = 0;
                            for(Odeljak odeljak : g.getOdeljak()){
                                odeljakId += 1;
                                odeljak.setBr(odeljakId);
                                odeljak.setId("odeljak" + odeljakId);

                                if(odeljak.getClan() != null){
                                    int clanId = 0;
                                    for(Clan c : odeljak.getClan()){
                                        clanId +=1;
                                        c.setBr(clanId);
                                        c.setId("clan" + (clanId));

                                        updateIdAndBrojCLAN(c);
                                    }
                                }

                                if(odeljak.getPododeljak() != null){
                                    int pododeljakId = 0;
                                    for(Pododeljak pod : odeljak.getPododeljak()){
                                        pododeljakId += 1;
                                        pod.setId("pododeljak" + pododeljakId);

                                        if(pod.getClan() != null){
                                            int clanId = 0;
                                            for(Clan c : pod.getClan()){
                                                clanId +=1;
                                                c.setBr(clanId);
                                                c.setId("clan" + (clanId));

                                                updateIdAndBrojCLAN(c);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }


}