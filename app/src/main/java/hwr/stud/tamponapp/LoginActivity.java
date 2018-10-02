package hwr.stud.tamponapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signIn;
    TextView loggedInStatus;

    String usernameString;
    String passwordString;
    String loginURLString;

    Boolean loggedIn = false;

    Intent intentStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        intentStats = new Intent(this, StatsActivity.class);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);
        loggedInStatus = (TextView) findViewById(R.id.loggedInStatus);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read username and password from form
                usernameString = username.getText().toString();
                passwordString = password.getText().toString();

                // Create loginURLString with params
                loginURLString = "http://192.168.178.54:8080/login"; //?un=" + usernameString + "&pw=" + passwordString;

                // talk to REST Service, done in separate worker thread
                // to be changed to Https
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // open connection
                            URL loginURL = new URL(loginURLString);
                            HttpURLConnection loginConnection = (HttpURLConnection) loginURL.openConnection();

                            // set methode to POST
                            loginConnection.setRequestMethod("POST");
                            loginConnection.setDoOutput(true);
                            loginConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            loginConnection.setRequestProperty("Accept", "application/json");
                            loginConnection.setChunkedStreamingMode(0);


                            // construct request body
                            JSONObject loginJSON = new JSONObject();
                            loginJSON.put("un", usernameString);
                            loginJSON.put("pw", passwordString);

                            // set content length
                            // loginConnection.setRequestProperty("Content-Length", String.valueOf(loginJSON.toString().length()));

                            // write requestbody
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(loginConnection.getOutputStream());
                            outputStreamWriter.write(loginJSON.toString());
                            //////// STREAMS IMMER FLUSH()!!!!!!
                            outputStreamWriter.flush();

                            // test request
                            Log.i("[loginJSON]", loginJSON.toString());

                            // handle response as json
                            if (loginConnection.getResponseCode() == 200) {
                                InputStream responseBody = loginConnection.getInputStream();
                                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                                JsonReader jsonReader = new JsonReader(responseBodyReader);

                                // check for  login success
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String key = jsonReader.nextName();
                                    if (key.equals("success")) {
                                        if (jsonReader.nextString().equals("true")) {
                                            loggedIn = true;
                                            startActivity(intentStats);
                                            break;
                                        } else {
                                            loggedIn = false;
                                        }
                                    } else {
                                        jsonReader.skipValue();
                                    }
                                }
                                jsonReader.endObject();
                                Log.i("[jsonReader]", jsonReader.toString());
                                jsonReader.close();
                            }
                            loginConnection.disconnect();

                            // Exception handling has yet to be done!!
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


    }
}
