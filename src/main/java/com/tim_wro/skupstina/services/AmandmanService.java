package com.tim_wro.skupstina.services;


import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;
import com.tim_wro.skupstina.util.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class AmandmanService {

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
}
