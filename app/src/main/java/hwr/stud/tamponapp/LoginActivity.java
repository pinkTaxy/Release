package hwr.stud.tamponapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import hwr.stud.mylibrary.HttpDigestAuth;
import hwr.stud.mylibrary.HttpsHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button signIn;
    private TextView loggedInStatus;

   /* private final String usernameString = null;
    private final String passwordString = null;
    private final String loginURLString = null;*/

    private Boolean loggedIn = false;

    private Intent intentStats;
    private Intent intentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        intentStats = new Intent(this, StatsActivity.class);
        intentLogin = new Intent(this, LoginActivity.class);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);
        loggedInStatus = (TextView) findViewById(R.id.loggedInStatus);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read username and password from form
                final String usernameString = username.getText().toString();
                final String passwordString = password.getText().toString();

                // Create loginURLString with params
                final String loginURLString = "https://192.168.178.26:8080/login"; //?un=" + usernameString + "&pw=" + passwordString;

                // talk to REST Service, done in separate worker thread
                // to be changed to Https
                loginPOSTRequest(loginURLString, usernameString, passwordString);
            }

            private void loginPOSTRequest(
                    final String loginURLString,
                    final String usernameString,
                    final String passwordString) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        HttpsHelper.trustAllHosts();

                        HttpsURLConnection loginConnection = HttpsHelper.establishHttpsConnectionAllCert(
                                loginURLString,
                                usernameString,
                                passwordString
                        );
                        Log.i("[establishHttpsConnection()]", "success.");

                        try {
                            loginConnection.setRequestMethod("POST");
                            loginConnection.setDoOutput(true);
                            loginConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            loginConnection.setRequestProperty("Accept", "application/json");
                            loginConnection.setChunkedStreamingMode(0);
                            loginConnection.setConnectTimeout(5000);
                            loginConnection.setRequestProperty("Authorization", HttpsHelper.setBasicAuth(usernameString, passwordString));
                            Log.i("[loginConnection]", "Request methode set to POST");
                            Log.i("[setBasicAuth]", "Basic Auth was set.");
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                            Log.i("[JSON]", "No Response from Server!");
                        }

                        // construct request body
                        JSONObject loginJSON = new JSONObject();
                        try {
                            loginJSON.put("un", usernameString);
                            loginJSON.put("pw", passwordString);
                            Log.i("[JSONObject]", "Request Body was created");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("[??]", "wtf?");
                        // write requestbody
                        try {
                            Log.i("[try]", "jup.");
                            //int errorNo = loginConnection.getErrorStream().read();
                            //String error = String.valueOf(errorNo);
                            //Log.i("[errorLog]", error);
                            OutputStream outputStream = loginConnection.getOutputStream();
                            Log.i("[getOutputSteam]", "success.");
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                            Log.i("[OutputStreamWriter]", "success");
                            try {
                                outputStreamWriter.write(loginJSON.toString());
                                outputStreamWriter.flush(); // Streams IMMER flushen!!
                                Log.i("[outputStreamWriter]", "was flushed.");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // test request
                            Log.i("[loginJSON]", loginJSON.toString());

                            if(isLoginSuccess(loginConnection)) {startActivity(intentStats);}
                            else {startActivity(intentLogin);}


                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("[StreamWriter]", "failed.");
                        }
                        loginConnection.disconnect();
                    }
                });

            }

   /*         @Nullable
            HttpsURLConnection establishHttpsConnection(
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
            }*/

            Boolean isLoginSuccess(HttpsURLConnection loginConnection) {

                Boolean isLoggedIn = false;

                JsonReader jsonReader = null;
                try {
                    if (loginConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                        InputStream responseBody = loginConnection.getInputStream();
                       // if(responseBody.available() > 0) {
                            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                            jsonReader = new JsonReader(responseBodyReader);
                       // } else {Log.i("[responseBody]","responseBody is not available.");}
                        Log.i("[isLoginSuccess]", "succeeded.");
                    }

                    if (jsonReader != null) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String key = jsonReader.nextName();
                            if (key.equals("success")) {
                                if (jsonReader.nextString().equals("true")) {
                                    isLoggedIn = true;
                                    // startActivity(intentStats);
                                    break;
                                } else {
                                    isLoggedIn = false;
                                }
                            } else {
                                jsonReader.skipValue();
                            }
                        }
                        jsonReader.endObject();
                        Log.i("[jsonReader]", jsonReader.toString());
                        jsonReader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    isLoggedIn = false;
                }
                return isLoggedIn;
            }
        });


    }

/*    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };*/

   /* private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
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
    }*/

/*    protected void setBasicAuth(HttpsURLConnection loginConnection, String username, String password) {
        String authStr = username + ":" + password;
        Log.i("[BasicAuth]","Original String is " + authStr);

        // encode data on your side using BASE64
        byte[] authStrArr = authStr.getBytes();
        String authEncoded = android.util.Base64.encodeToString(authStrArr, android.util.Base64.NO_WRAP);
        loginConnection.setRequestProperty("Authorization", "Basic "+authEncoded);
    }*/
}
