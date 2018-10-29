package hwr.stud.mylibrary;

import android.util.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

public class HttpsJsonResponse {

    public static JSONObject getResponse(HttpsURLConnection connection) throws IOException {

        InputStream inputStream = connection.getInputStream();

    }
}
