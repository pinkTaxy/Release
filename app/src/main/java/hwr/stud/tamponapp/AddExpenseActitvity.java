package hwr.stud.tamponapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import hwr.stud.mylibrary.HttpBasicAuth;
import hwr.stud.mylibrary.HttpsConnection;
import hwr.stud.mylibrary.HttpsPostRequest;
import hwr.stud.mylibrary.HttpsUtility;

import static hwr.stud.mylibrary.HttpsConnection.getConnection;

public class AddExpenseActitvity extends AppCompatActivity {

    private static final int REQUEST_CAPTURE_IMAGE = 100;

    Button addExpense;
    Button takeFoto;

    Intent privateStats;

    ImageView expenseImageView;

    Bitmap imageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_acitvity);

        addExpense = (Button) findViewById(R.id.addExpense);
        privateStats = new Intent(this, PrivateStatsActivity.class);

        expenseImageView = (ImageView) findViewById(R.id.expenseImage);
        expenseImageView.setVisibility(View.INVISIBLE);

        takeFoto = (Button) findViewById(R.id.takeFoto);

        takeFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCameraIntent();

                // startActivity(privateStats);
            }

            private void openCameraIntent() {
                Intent pictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE
                );
                if(pictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(pictureIntent,
                            REQUEST_CAPTURE_IMAGE);
                }
            }

        });

        addExpense.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(imageBitmap != null) {

                    JSONObject requestBody = new JSONObject();


                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {

                            HttpsUtility.trustAllCertificates();

                            try {
                                requestBody.put("imageName", Long.toString(System.currentTimeMillis()));
                                requestBody.put("file", imageBitmap);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            URL url = null;
                            try {
                                url = new URL("https://192.168.178.26:443/upload");
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                            HttpsURLConnection connection = null;
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("Creds", 0); // 0 - for private mode
                            Log.i("[Shared Preferences]", pref.getString("password", null));
                            Log.i("[Shared Preferences]", pref.getString("username", null));

                            try {
                                connection = getConnection(url, new HttpBasicAuth().getAuthString(pref.getString("username", null), pref.getString("password", null)));
                                HttpsPostRequest.sendRequest(connection, requestBody);
                                Log.i("[HttpsPostRequest]", "was sendt.");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    });


                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("[onActivityResult]", "was entered.");

        // setContentView(R.layout.activity_add_expense_acitvity);

        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            Log.i("[onActivityResult]", "ok.");

            if (data != null && data.getExtras() != null) {
                Log.i("[data != null]", "entered");
                imageBitmap = (Bitmap) data.getExtras().get("data");
                expenseImageView.setVisibility(View.VISIBLE);
                expenseImageView.setImageBitmap(imageBitmap);
                Log.i("[expenseImageView]", Integer.toString(imageBitmap.getByteCount()));
                Log.i("[expenseImageView]", "was set.");

            }
        }
    }
}
