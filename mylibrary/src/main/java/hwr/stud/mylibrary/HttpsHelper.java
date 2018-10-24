package hwr.stud.mylibrary;

import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsHelper {
    public static String setBasicAuth(String username, String password) {
        String authStr = username + ":" + password;
        Log.i("[BasicAuth]","Original String is " + authStr);

        // encode data on your side using BASE64
        byte[] authStrArr = authStr.getBytes();
        String authEncoded = android.util.Base64.encodeToString(authStrArr, android.util.Base64.NO_WRAP);
        return "Basic " + authEncoded;
        //loginConnection.setRequestProperty("Authorization", "Basic " + authEncoded);
    }

    public static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static HttpsURLConnection establishHttpsConnectionAllCert(
            String loginURLString,
            String usernameString,
            String passwordString
    ) {
        URL loginURL = null;
        try {
            loginURL = new URL(loginURLString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        try {
            HttpsURLConnection loginConnection = (HttpsURLConnection) loginURL.openConnection();
            loginConnection.setHostnameVerifier(DO_NOT_VERIFY);
            Log.i("[HttpsURLConnection]", "Connection was established");
            return loginConnection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public final static boolean isSuccess(JSONObject responseBody) {

        boolean isSuccess;

        try {
            if (responseBody.getString("success") == "true") {
                isSuccess = true;
            } else {
                isSuccess = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }
}


