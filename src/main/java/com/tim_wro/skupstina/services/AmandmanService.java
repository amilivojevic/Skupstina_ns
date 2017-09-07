package com.tim_wro.skupstina.services;


import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DOMHandle;
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
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmandmanService {

    @Autowired
    private SednicaService sednicaService;

    private static TransformerFactory transformerFactory;

    static {
        transformerFactory = TransformerFactory.newInstance();
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
        String docId = "/amandman/" + imeAmandmana + ".xml";

        xmlManager.read(docId, metadata,handle);

        Amandman amd = handle.get();

        // Release the client
        client.release();

        return amd;
    }

    public void applyAmandman(Amandman amd) throws FileNotFoundException {

        //dobavljanje akt Document objekta
        DatabaseClient client = Connection.getConnection();
        final XMLDocumentManager xmlManager = client.newXMLDocumentManager();
        DOMHandle content = new DOMHandle();

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        String sednicaID = sednicaService.getSednicaIDByAktID(amd.getAktID());
        xmlManager.read("/sednica/"+sednicaID+".xml", metadata, content);
        Document sednicaDoc = content.get();



        for (StavkaAmandmana stavka : amd.getStavke().getStavkaAmandmana()) {
            if (stavka.getTipIzmene() == TipIzmene.BRISANJE) {


                for ( int br=0; br< sednicaDoc.getElementsByTagName("ns2:"+stavka.getTagIzmene()).getLength(); br++) {
                    NamedNodeMap attributes = sednicaDoc.getElementsByTagName("ns2:"+stavka.getTagIzmene()).item(br).getAttributes();
                    Node attr = attributes.getNamedItem("id");
                    if(attr != null && attr.getTextContent().equals(stavka.getIdPodakta())) {
                        System.out.println("****** " + attr.getTextContent() + "   br = " + br);

                        //transform(sednicaDoc.getElementsByTagName("ns2:"+stavka.getTagIzmene()).item(br), System.out);
                        sednicaDoc.getElementsByTagName("ns2:"+stavka.getTagIzmene()).item(br).getParentNode().removeChild(
                                sednicaDoc.getElementsByTagName("ns2:"+stavka.getTagIzmene()).item(br)
                        );
                    }
                }



            } else if (stavka.getTipIzmene() == TipIzmene.DODAVANJE) {
                System.out.println("JOS UVEK NE RADI");
            } else if (stavka.getTipIzmene() == TipIzmene.IZMENA) {
                System.out.println("JOS UVEK NE RADI");
            }

        }

        //prikaz nove sednice (promenjen akt) na System.out
        transform(sednicaDoc.getElementsByTagName("sednica").item(0), System.out);

        //kreiranje novog xml-a
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        File updated = new File("file.xml");
        Result output = new StreamResult(updated);
        Source input = new DOMSource(sednicaDoc);

        try {
            transformer.transform(input, output);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        //overwrite
        sednicaService.writeInMarkLogicDB(updated,sednicaID);

        client.release();


        System.out.println("[INFO] End.");
    }

    /**
     * Serializes DOM tree to an arbitrary OutputStream.
     *
     * @param node a node to be serialized
     * @param out an output stream to write the serialized
     * DOM representation to
     *
     */
    private static void transform(Node node, OutputStream out) {
        try {

            // Kreiranje instance objekta zaduzenog za serijalizaciju DOM modela
            Transformer transformer = transformerFactory.newTransformer();

            // Indentacija serijalizovanog izlaza
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Nad "source" objektom (DOM stablo) vrši se transformacija
            DOMSource source = new DOMSource(node);

            // Rezultujući stream (argument metode)
            StreamResult result = new StreamResult(out);

            // Poziv metode koja vrši opisanu transformaciju
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
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
