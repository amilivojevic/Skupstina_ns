package com.tim_wro.skupstina.services;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;
import com.tim_wro.skupstina.dto.FirstVotingDTO;
import com.tim_wro.skupstina.dto.SecondVotingDTO;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Amandman;
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
import javax.xml.bind.Marshaller;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class SednicaService {

    private SednicaRepository sednicaRepository;
    public static final String RDF_XSL = "src/main/resources/xsl/sednica.xsl";

    @Autowired
    public SednicaService(SednicaRepository sednicaRepository) {
        this.sednicaRepository = sednicaRepository;
    }

    @Autowired
    public UserService userService;

    @Autowired
    public AktService aktService;

    @Autowired
    public AmandmanService amandmanService;

    public void updateSednica(Sednica sednica) throws JAXBException {

        DatabaseClient client = Connection.getConnection();

        System.out.println("stanje sednice " + sednica.getStanje());
        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        String docId = "/sednica/" + sednica.getId() + ".xml";
        System.out.println("uzeo sednicu " + sednica.getId());

        // Defining namespace mappings
        EditableNamespaceContext namespaces = new EditableNamespaceContext();
        namespaces.put("a", "http://www.skustinans.rs/akti");
        namespaces.put("s", "http://www.skustinans.rs/sednice");
       // namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

        // Assigning namespaces to patch builder
        DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
        patchBuilder.setNamespaces(namespaces);

        ////////////////////////////////
        String patch2 = "";

        //marshalling
        JAXBContext jaxbContext = null;
        try {

            jaxbContext = JAXBContext.newInstance(Sednica.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(sednica, sw);

            patch2 = sw.toString();
            System.out.println("patch 2" + patch2);
      //      if(aktiSednice.size() != 0) {
      //          patch2  = patch2.substring(patch2.indexOf("<ns2:akt"), patch2.indexOf("</sednica>"));
      //      }
            patch2 = patch2.substring(patch2.indexOf("<sednica"));

            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        // Defining XPath context
        String contextXPath2 = "/s:sednica";
        DocumentPatchHandle patchHandle;

        // insert fragment
        //  patchBuilder.insertFragment(contextXPath2, DocumentPatchBuilder.Position.BEFORE, patch2);
        patchBuilder.replaceFragment(contextXPath2, patch2);

        patchHandle = patchBuilder.build();

        xmlManager.patch(docId, patchHandle);

        client.release();

        /////////////////////////////

    }


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

    public String getSednicaIDByAktID(String aktID){
        List<Sednica> lista = getAll();
        for(Sednica s : lista){
            for(Akt a : s.getAkt()){
                if (a.getId().equals(aktID)) {
                    return s.getId();
                }
            }
        }
        return null;
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
            if(redniBroj != null && redniBroj.equals(sednica.getRedniBroj()))
            return sednica;
        }
        return sednica;
    }

    public void writeInMarkLogicDB(File file, String id) throws FileNotFoundException {
        DatabaseClient client = Connection.getConnection();

        String collId = "sednice";

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define a URI value for a document.
        int br = brojSednice() + 1;
        String docId = "/sednica/" + id + ".xml";

        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = new InputStreamHandle(new FileInputStream(file));

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(collId);

        // Write the document to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \" database.");
        xmlManager.write(docId, metadata, handle);

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

    // vraca true ako je akt izglasan, false ako ne
    public boolean checkIfIzglasan(FirstVotingDTO firstVotingDTO) {

        try {

            Akt akt = aktService.getById(firstVotingDTO.getAktID());
            Sednica sednica = null;

            sednica = findById(firstVotingDTO.getSednicaID());
            List<Akt> aktiSednice = sednica.getAkt();
            for(Akt a : aktiSednice){
                if(a.getId().equals(akt.getId())){
                    a.setZa(BigInteger.valueOf(firstVotingDTO.getZa()));
                    a.setProtiv(BigInteger.valueOf(firstVotingDTO.getProtiv()));
                    a.setSuzdrzani(BigInteger.valueOf(firstVotingDTO.getSuzdrzani()));
                    sednica.setBrojPrisutnih(BigInteger.valueOf(firstVotingDTO.getBrojPrisutnih()));

                    int kvalifikovanaVecinaInt = sednica.getBrojPrisutnih().intValue();
                    int za = a.getZa().intValue();

                    updateSednica(sednica);


                   return ((double)za) > ((double) kvalifikovanaVecinaInt / 2.0);

                }
            }

            throw new IllegalArgumentException("Los akt id");


        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }

    }

    // vraca true ako je akt izglasan, false ako ne
    public boolean checkIfIzglasanThird(FirstVotingDTO firstVotingDTO) {

        try {

            Akt akt = aktService.getById(firstVotingDTO.getAktID());
            Sednica sednica = null;

            sednica = findById(firstVotingDTO.getSednicaID());
            List<Akt> aktiSednice = sednica.getAkt();
            for(Akt a : aktiSednice){
                if(a.getId().equals(akt.getId())){
                    a.setZa(BigInteger.valueOf(firstVotingDTO.getZa()));
                    a.setProtiv(BigInteger.valueOf(firstVotingDTO.getProtiv()));
                    a.setSuzdrzani(BigInteger.valueOf(firstVotingDTO.getSuzdrzani()));

                    int kvalifikovanaVecinaInt = sednica.getBrojPrisutnih().intValue();
                    int za = a.getZa().intValue();

                    updateSednica(sednica);


                    return ((double)za) > ((double) kvalifikovanaVecinaInt / 2.0);

                }
            }

            throw new IllegalArgumentException("Los akt id");


        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }

    }

    // vraca true ako je amandman izglasan, false ako ne
    public boolean checkIfIzglasanAmandman(SecondVotingDTO secondVotingDTO) {

        try {

            Amandman amandman = amandmanService.findById(secondVotingDTO.getAmandmanID());

            amandman.setZa(BigInteger.valueOf(secondVotingDTO.getZa()));
            amandman.setProtiv(BigInteger.valueOf(secondVotingDTO.getProtiv()));
            amandman.setSuzdrzani(BigInteger.valueOf(secondVotingDTO.getSuzdrzani()));

            amandmanService.updateAmandman(amandman);
            Sednica sednica = findById(secondVotingDTO.getSednicaID());
            int kvalifikovanaVecinaInt = sednica.getBrojPrisutnih().intValue();
            int za = amandman.getZa().intValue();

            return ((double)za) > ((double) kvalifikovanaVecinaInt / 2.0);


        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void deleteFromDB(Sednica sednica) throws FileNotFoundException {

        DatabaseClient client = Connection.getConnection();

        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        String docId = "/sednica/" + sednica.getId() + ".xml";

        xmlManager.delete(docId);

        client.release();

    }

}
