package hwr.stud.mylibrary;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.net.ssl.HttpsURLConnection;

public class HttpsPostRequest {

    public static int sendRequest(HttpsURLConnection connection, JSONObject requestBody)
            throws IOException {

        int responseCode = 404;


            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(requestBody.toString());
            responseCode = connection.getResponseCode();


        return responseCode;
    }
}
