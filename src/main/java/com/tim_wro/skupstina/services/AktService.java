package com.tim_wro.skupstina.services;

import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Korisnik;
import com.tim_wro.skupstina.model.StanjeAkta;
import com.tim_wro.skupstina.repository.AktRepository;
import com.tim_wro.skupstina.util.DateConverter;
import com.tim_wro.skupstina.util.MetadataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Nina on 16-Jun-17.
 */

@Service
public class AktService {

    private AktRepository aktRepository;
    public static final String RDF_XSL = "src/main/resources/schemes/akt.xsl";

    @Autowired
    public AktService(AktRepository aktRepository) {
        this.aktRepository = aktRepository;

    }
/*

    public Akt create(String rawAct, Korisnik korisnik) {
        try {
            final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(rawAct.getBytes(Charset.defaultCharset()));
            Document doc = documentBuilder.parse(byteArrayInputStream);

            populateIdFor("article", doc);
            populateIdFor("paragraph", doc);
            populateIdFor("item", doc);
            populateIdFor("subItem", doc);
            populateIdFor("ident", doc);

            doc.getDocumentElement().setAttribute("id", UUID.randomUUID().toString());
            doc.getDocumentElement().setAttribute("status", StanjeAkta.U_PROCEDURI.name());
            doc.getDocumentElement().setAttribute("authorId", String.valueOf(korisnik.getId()));
            doc.getDocumentElement().setAttribute("date", DateConverter.printDate(new Date()));

            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
            final StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            final String newXml = writer.getBuffer().toString();

            final ByteArrayOutputStream metadataResult = MetadataExtractor.extractMetadata(newXml, RDF_XSL);

            final Akt akt = JAXB.unmarshal(new StringReader(newXml), Akt.class);

            return aktRepository.save(akt, metadataResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/

    private void populateIdFor(String tagName, Document doc) {
        final NodeList list = doc.getDocumentElement().getElementsByTagName(tagName);
        for (int i = 0; i < list.getLength(); i++) {
            ((Element) list.item(i)).setAttribute("id", Integer.toString(i + 1));
        }
    }

}