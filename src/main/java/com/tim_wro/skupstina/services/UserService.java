package com.tim_wro.skupstina.services;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import com.tim_wro.skupstina.util.Properties;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by Sandra on 6/14/2017.
 */
@Service
public class UserService {

    private static DatabaseClient client;

    public void register(Korisnik korisnik) {

        client = DatabaseClientFactory.newClient(Properties.host, Properties.port,
                Properties.database, Properties.user, Properties.password,
                Properties.authentication);

        XMLDocumentManager manager = client.newXMLDocumentManager();
        try {
            JAXBContext context = JAXBContext.newInstance(Korisnik.class);
            JAXBHandle<Korisnik> handle = new JAXBHandle<>(context);

            handle.set(korisnik);

            manager.write("/users/" + korisnik.getId(), handle);
            client.release();
        }
        catch (JAXBException e) {
            e.printStackTrace();
            client.release();
        }
    }
}
