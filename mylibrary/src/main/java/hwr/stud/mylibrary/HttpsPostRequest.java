package hwr.stud.mylibrary;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;

import javax.net.ssl.HttpsURLConnection;


public class HttpsPostRequest {

    private final HttpsURLConnection connection;
    private JSONObject requestBody;
    private final String username;
    private final String password;
    private JSONObject responseBody;
    private int httpResponseCode = 0;

    public HttpsPostRequest (HttpsURLConnection connection, JSONObject requestBody, String username, String password) {


        this.connection = connection;
        this.requestBody = requestBody;
        this.username = username;
        this.password = password;

        // set Methode to POST
        try {
            connection.setRequestMethod ("POST");
            connection.setDoOutput (true);
            connection.setRequestProperty ("Content-Type", "application/josn; charset=UTF-8");
            connection.setRequestProperty ("Accept", "applicatopn/json");
            connection.setChunkedStreamingMode (0);
            connection.setConnectTimeout (10000);
            connection.setRequestProperty ("Authorization", HttpsHelper.setBasicAuth(username, password));
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.i("[HttpsPostRequest]", "Failed to set request properties.");
            requestBody = null;
        }

    }

    public JSONObject getResponse () {

        final OutputStream outputStream;
        try {
            outputStream = connection.getOutputStream();
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            // send POST request, FLUSH!!
            outputStreamWriter.write(requestBody.toString());
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("[HttpsPostRequest]", "Failed to write request.");
            requestBody = null;
        }

        // handle response
        try {
            httpResponseCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            responseBody = null;
        }


        if (httpResponseCode != 200) {
            try {
                final InputStream errorStream = connection.getErrorStream();
                responseBody = JsonHelper.convertInputStreamToJSONObject(errorStream);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                final InputStream inputStream = connection.getInputStream();
                responseBody = JsonHelper.convertInputStreamToJSONObject(inputStream);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    protected int getResponseCode() {
        return httpResponseCode;
    }
}
