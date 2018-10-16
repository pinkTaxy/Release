package hwr.stud.tamponapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SignupActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText name;
    EditText email;
    EditText sex;
    Button signUp;

    String usernameString;
    String passwordString;
    String nameString;
    String emailString;
    String sexString;

    String signUpURLString;

    Intent intentSignIn;
    Intent intentSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        sex = (EditText) findViewById(R.id.sex);
        signUp = (Button) findViewById(R.id.signUp);

        intentSignIn = new Intent(this, LoginActivity.class);
        intentSignUp = new Intent(this, SignupActivity.class);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read username and password from form
                usernameString = username.getText().toString();
                passwordString = password.getText().toString();
                nameString = name.getText().toString();
                emailString = email.getText().toString();
                sexString = sex.getText().toString();

                // Create loginURLString with params
                signUpURLString = "https://192.168.178.26:8080/signup"; //?un=" + usernameString + "&pw=" + passwordString;

                // talk to REST Service, done in separate worker thread
                // to be changed to Https
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // open connection
                            URL signUpURL = new URL(signUpURLString);
                            HttpsURLConnection SignUpConnection = (HttpsURLConnection) signUpURL.openConnection();

                            // set methode to POST
                            SignUpConnection.setRequestMethod("POST");
                            SignUpConnection.setDoOutput(true);
                            SignUpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            SignUpConnection.setRequestProperty("Accept", "application/json");
                            SignUpConnection.setChunkedStreamingMode(0);


                            // construct request body
                            JSONObject signupJSON = new JSONObject();
                            signupJSON.put("un", usernameString);
                            signupJSON.put("pw", passwordString);
                            signupJSON.put("name", nameString);
                            signupJSON.put("email", emailString);
                            signupJSON.put("sex", sexString);


                            // set content length
                            // loginConnection.setRequestProperty("Content-Length", String.valueOf(loginJSON.toString().length()));

                            // write requestbody
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(SignUpConnection.getOutputStream());
                            outputStreamWriter.write(signupJSON.toString());
                            //////// STREAMS IMMER FLUSH()!!!!!!
                            outputStreamWriter.flush();

                            // test request
                            Log.i("[signUpJSON]", signupJSON.toString());

                            // handle response as json
                            if (SignUpConnection.getResponseCode() == 200) {
                                InputStream responseBody = SignUpConnection.getInputStream();
                                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                                JsonReader jsonReader = new JsonReader(responseBodyReader);

                                // check for  SignUp success
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String key = jsonReader.nextName();
                                    if (key.equals("success")) {
                                        if (jsonReader.nextString().equals("true")) {
                                            startActivity(intentSignIn);
                                            break;
                                        } else {
                                            startActivity(intentSignUp);
                                        }
                                    } else {
                                        jsonReader.skipValue();
                                    }
                                }
                                jsonReader.endObject();
                                Log.i("[jsonReader]", jsonReader.toString());
                                jsonReader.close();
                            }
                            SignUpConnection.disconnect();

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
