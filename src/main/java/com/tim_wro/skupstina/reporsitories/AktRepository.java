package com.tim_wro.skupstina.reporsitories;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.util.Properties;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import java.io.ByteArrayOutputStream;

/**
 * Created by Nina on 16-Jun-17.
 */

@Component
public class AktRepository {

    private static DatabaseClient client;

    public static final String ACTS_COLLECTION = "fools/acts/collection";

    public Akt save(Akt akt, ByteArrayOutputStream metadataResult) {
        client = DatabaseClientFactory.newClient(Properties.host,
                Properties.port, Properties.database, Properties.user,
                Properties.password, DatabaseClientFactory.Authentication.DIGEST);

        final XMLDocumentManager documentManager = client.newXMLDocumentManager();

        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Akt.class);
            final JAXBHandle<Akt> handle = new JAXBHandle<>(jaxbContext);
            handle.set(akt);

            final DocumentMetadataHandle documentMetadataHandle = new DocumentMetadataHandle();
            documentMetadataHandle.getCollections().add(ACTS_COLLECTION);

            documentManager.write("/acts/" + akt.getId(), documentMetadataHandle, handle);

            saveMetadata(metadataResult, client);

            client.release();
            return akt;
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
        graphManager.merge("pof/act/metadata", stringHandle);
    }
}
