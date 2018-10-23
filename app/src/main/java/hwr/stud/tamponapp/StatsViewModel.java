package hwr.stud.tamponapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import hwr.stud.mylibrary.HttpsPostRequest;
import hwr.stud.mylibrary.HttpsURLConnectionBuilder;

public class StatsViewModel extends ViewModel {

    private MutableLiveData<JSONArray> expenseItems;

    public LiveData<JSONArray> getExpenseItems() {
        if (expenseItems == null) {

            // asynchronous loading of expenseItems
            loadExpenseItems();
        }
        return expenseItems;
    }

    private void loadExpenseItems() {

        String username = "Jona";
        String password = "topsecret";

        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject().put("username", username);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("[loadExpenseItem()]", "Failed to build requestBody.");
        }

        try {

            final URL url = new URL("https://192.168.178.26:443/stats");
            final HttpsURLConnection connection = new HttpsURLConnectionBuilder(url, true).build();
            final HttpsPostRequest httpsPostRequest = new HttpsPostRequest(connection, requestBody, username, password);
            final JSONObject responseBody = httpsPostRequest.getResponse();

            if (responseBody.getString("success").equals("true")) {
                expenseItems.setValue(responseBody.getJSONArray("expenseItems"));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("[loadExpenseItems()]", "Failed to open Connection to Server");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
