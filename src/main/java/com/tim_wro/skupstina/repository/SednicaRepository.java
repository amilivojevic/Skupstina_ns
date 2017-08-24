package com.tim_wro.skupstina.repository;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.tim_wro.skupstina.util.Properties;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import java.io.ByteArrayOutputStream;

@Component
public class SednicaRepository {

    private static DatabaseClient client;

    public static final String SEDNICE_COLLECTION = "fools/sednice/collection";

    public Sednica save(Sednica sednica, ByteArrayOutputStream metadataResult) {
        client = DatabaseClientFactory.newClient(Properties.host,
                Properties.port, Properties.database, Properties.user,
                Properties.password, DatabaseClientFactory.Authentication.DIGEST);

        final XMLDocumentManager documentManager = client.newXMLDocumentManager();

        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Sednica.class);
            final JAXBHandle<Sednica> handle = new JAXBHandle<>(jaxbContext);
            handle.set(sednica);

            final DocumentMetadataHandle documentMetadataHandle = new DocumentMetadataHandle();
            documentMetadataHandle.getCollections().add(SEDNICE_COLLECTION);

            documentManager.write("/sednice/" + sednica.getId(), documentMetadataHandle, handle);

            saveMetadata(metadataResult, client);

            client.release();
            return sednica;
        } catch (Exception e) {
            e.printStackTrace();
            client.release();
            return null;
        }
    }

    private void saveMetadata(ByteArrayOutputStream metadataResult, DatabaseClient client) {
        final GraphManager graphManager = client.newGraphManager();
        final String content = metadataResult.toString();

        final StringHandle stringHandle = new StringHandle(content).withMimetype(RDFMimeTypes.RDFXML);
        graphManager.merge("pof/sednica/metadata", stringHandle);
    }
}
