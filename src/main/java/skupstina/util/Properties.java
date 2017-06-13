package skupstina.util;

import com.marklogic.client.DatabaseClientFactory;

public class Properties {

    public static String user = "root";
    public static String password = "root";
    public static String host = "localhost";
    public static int port = 8000;
    public static DatabaseClientFactory.Authentication authentication = DatabaseClientFactory.Authentication.DIGEST;
    public static boolean proxy = false;
    public static String database = "skupstinaNS";
}
