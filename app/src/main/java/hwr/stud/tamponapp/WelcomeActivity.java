package hwr.stud.tamponapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import hwr.stud.mylibrary.HttpsUtility;

public class WelcomeActivity extends AppCompatActivity {

    //TODO: add responsive font size!!!!!!!!111!!!!

    Button toLogIn;
    Intent intentLogIn;

    Button toSignUp;
    Intent intentSignUp;

    TextView globalStats;
    TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        toLogIn = (Button) findViewById(R.id.toLogin);
        intentLogIn = new Intent(this, LoginActivity.class);

        toSignUp = (Button) findViewById(R.id.toSignup);
        intentSignUp = new Intent(this, SignupActivity.class);

        // TextViews vorbereiten
        globalStats = (TextView) findViewById(R.id.globalSum);
        infoText = (TextView) findViewById(R.id.infotext);

        // Anfrage nach Texten

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                HttpsUtility.trustAllCertificates();

                try {
                    URL url = new URL("https://192.168.178.54:443/welcome");
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        JsonReader jsonReader = new JsonReader(inputStreamReader);
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            if (jsonReader.nextName() == "infotext")
                                infoText.setText(jsonReader.nextString());
                            if (jsonReader.nextName() == "globalStats")
                                globalStats.setText(jsonReader.nextString());
                        }
                    } else {
                        Log.i("[HttpsUrlConnection]", Integer.toString(connection.getResponseCode()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentLogIn);
            }
        });

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentSignUp);
            }
        });
    }
}
