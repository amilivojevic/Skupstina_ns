package com.tim_wro.skupstina.util;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.services.AktService;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class FOPReporter {

        private FopFactory fopFactory;

        private TransformerFactory transformerFactory;

        public static final String XSL_FILE = "data/xslt/akt_xsl.xsl"; // HTML
        //public static final String XML_FILE = "data/akt.xml";
        public static final String FO_FILE = "data/xslt/akt_fo.xsl";  // PDF

        public static final String INPUT_FILE = "data/tt/bookstore.xml";
        //public static final String XSL_FILE = "data/tt/bookstore.xsl";

        public FOPReporter() throws SAXException, IOException {

            // Initialize FOP factory object
            //
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("data/fop.xconf").getFile());

            fopFactory = FopFactory.newInstance(file);

            // Setup the XSLT transformer factory
            transformerFactory = new TransformerFactoryImpl();
        }

        public String generateHTML(String inputXml) throws TransformerException {

            ClassLoader classLoader = getClass().getClassLoader();
            File xslFile = new File(classLoader.getResource(XSL_FILE).getFile());

            // Initialize Transformer instance
            StreamSource transformSource = new StreamSource(xslFile);
            Transformer transformer = transformerFactory.newTransformer(transformSource);

            StreamSource xml = new StreamSource(new StringReader(inputXml));

            StringWriter writer = new StringWriter();
            Result result = new StreamResult(writer);

            transformer.transform(xml, result);

            return writer.toString();

        }

        public byte[] generatePDF(String inputXml) throws Exception {
            //public void generatePDF(String inputXml) throws Exception { // FOR TEST

            // Point to the XSL-FO file
            ClassLoader classLoader = getClass().getClassLoader();
            File xslFile = new File(classLoader.getResource(FO_FILE).getFile());
          //  File xslFile = new File(FO_FILE);

            XMLStreamReader xmlSR = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(inputXml));

            // Create transformation source
            StreamSource transformSource = new StreamSource(xslFile);

            // Initialize the transformation subject
            //StreamSource source = new StreamSource(new File(INPUT_FILE));
            StAXSource source = new StAXSource(xmlSR);

            // Initialize user agent needed for the transformation
            FOUserAgent userAgent = fopFactory.newFOUserAgent();

            // Create the output stream to store the results
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            // Initialize the XSL-FO transformer object
            Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);

            // Construct FOP instance with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

            // Resulting SAX events
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            xslFoTransformer.transform(source, res);

            return outStream.toByteArray();

        }




}
