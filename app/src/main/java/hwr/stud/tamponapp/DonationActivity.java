package hwr.stud.tamponapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import hwr.stud.mylibrary.HttpBasicAuth;
import hwr.stud.mylibrary.HttpsConnection;
import hwr.stud.mylibrary.HttpsPostRequest;
import hwr.stud.mylibrary.HttpsUtility;

public class DonationActivity extends AppCompatActivity {

    Button donate;

    EditText donationAmount;

    Intent privateStatsAcitvity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        donationAmount = (EditText) findViewById(R.id.donationAmount);

        donate = (Button) findViewById(R.id.submitDonation);
        privateStatsAcitvity = new Intent(this, PrivateStatsActivity.class);

        String donationValue = donationAmount.getText().toString();
        Log.i("[Donation Value]", donationValue.toString());

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: do donation

                //startActivity(privateStatsAcitvity);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        HttpsUtility.trustAllCertificates();

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("Creds", 0); // 0 - for private mode

                        URL url;

                        try {
                            url = new URL("https://192.168.178.26:443/donation");

                            Log.i("[Shared Preferences]", pref.getString("password", null));
                            Log.i("[Shared Preferences]", pref.getString("username", null));

                            HttpsUtility.trustAllCertificates();

                            HttpsURLConnection connection = HttpsConnection.getConnection(
                                    url,
                                    HttpBasicAuth.getAuthString(
                                            pref.getString("username", null),
                                            pref.getString("password", null)));

                            JSONObject requestBody = new JSONObject();
                            requestBody.put("donationValue", donationValue.toString());

                            HttpsPostRequest.sendRequest(connection, requestBody);

                            // handle response as json
                            if (connection.getResponseCode() == 200) {
                                InputStream responseBody = connection.getInputStream();
                                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                                JsonReader jsonReader = new JsonReader(responseBodyReader);

                                // check for  SignUp success
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String key = jsonReader.nextName();
                                    if (key.equals("success")) {
                                        if (jsonReader.nextString().equals("true")) {
                                            startActivity(privateStatsAcitvity);
                                        }
                                    } else {
                                        jsonReader.skipValue();
                                    }
                                }
                                jsonReader.endObject();
                                Log.i("[jsonReader]", jsonReader.toString());
                                jsonReader.close();
                            }
                            connection.disconnect();

                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        });
    }
}
