package hwr.stud.mylibrary;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class HttpsURLConnectionBuilder {


    private final URL url;
    private static HttpsURLConnection httpsURLConnection;

    public HttpsURLConnectionBuilder(URL url, boolean trustAllHosts) {

        this.url = url;

        if (trustAllHosts) {
            HttpsHelper.trustAllHosts();
            final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            };
        }
    }

    @SuppressLint("LongLogTag")
    public HttpsURLConnection build() {
        try {
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("[HttpsURLConnectionBuilder]", "Failed to get connection.");
            httpsURLConnection = null;
        }

        return httpsURLConnection;
    }

}
