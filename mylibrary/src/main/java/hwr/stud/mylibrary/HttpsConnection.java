package hwr.stud.mylibrary;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsConnection {

    public static HttpsURLConnection getConnection(URL url, String basicAuthString) throws IOException {

        HttpsURLConnection connection;

        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setChunkedStreamingMode(0);
        connection.setConnectTimeout(5000);
        connection.setRequestProperty("Authorization", "Basic " + basicAuthString);

        return connection;
    }
}
