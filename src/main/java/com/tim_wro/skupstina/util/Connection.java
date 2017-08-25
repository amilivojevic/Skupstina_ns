package com.tim_wro.skupstina.util;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;

import java.io.IOException;

public class Connection {

    public static DatabaseClient getConnection(){
        DatabaseClient client;
        Util.ConnectionProperties props = null;
        try {
            props = Util.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
        return client;
    }
}
