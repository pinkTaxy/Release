package hwr.stud.tamponapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import hwr.stud.mylibrary.DonationListAdapter;
import hwr.stud.mylibrary.DonationListItem;
import hwr.stud.mylibrary.ExpenseListItem;
import hwr.stud.mylibrary.ExpensesListAdapter;
import hwr.stud.mylibrary.HttpBasicAuth;
import hwr.stud.mylibrary.HttpsUtility;

public class PrivateStatsActivity extends AppCompatActivity {

    Button toDonation;
    Button toAddExpense;

    ListView expenses;
    ListView donations;

    ExpensesListAdapter expensesAdapter;
    DonationListAdapter donationsAdapter;

    List<ExpenseListItem> expenseItemsList;
    List<DonationListItem> donationItemsList;

    Intent donationActivity;
    Intent addExpenseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_stats);

        toDonation = (Button) findViewById(R.id.toDonation);
        donationActivity = new Intent(this, DonationActivity.class);

        toAddExpense = (Button) findViewById(R.id.toAddExpense);
        addExpenseActivity = new Intent(this, AddExpenseActitvity.class);

        expenseItemsList = new ArrayList<ExpenseListItem>();
        donationItemsList = new ArrayList<DonationListItem>();

        HttpsUtility.trustAllCertificates();

        AsyncTask.execute(new Runnable() {
                              @Override
                              public void run() {

                                  try {
                                      URL url = new URL("https://192.168.178.54:443/stats");
                                      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                                      SharedPreferences pref = getApplicationContext().getSharedPreferences("Creds", 0); // 0 - for private mode
                                      Log.i("[Shared Preferences]", pref.getString("password", null));
                                      Log.i("[Shared Preferences]", pref.getString("username", null));


                                      connection.setRequestProperty(
                                              "Authorization",
                                              "Basic " +
                                                      new HttpBasicAuth().getAuthString(
                                                              pref.getString("username", null),
                                                              pref.getString("password", null)
                                                      ));

                                      Log.i("[loginConnection]", "Request methode set to POST");
                                      Log.i("[setBasicAuth]", "Basic Auth was set.");

                                      if (connection.getResponseCode() == 200) {
                                          InputStream inputStream = connection.getInputStream();
                                          InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                                          JsonReader jsonReader = new JsonReader(inputStreamReader);

                                          String amount = "";
                                          String date = "";
                                          String description = "";

                                          jsonReader.beginObject();
                                          while (jsonReader.hasNext()) {
                                              String nextName = jsonReader.nextName();
                                              if (nextName == "expenseItems") {
                                                  jsonReader.beginObject();
                                                  while (jsonReader.hasNext()) {
                                                      String nextNameExpenseItem = jsonReader.nextName();

                                                      if (nextNameExpenseItem == "amount")
                                                          amount = jsonReader.nextString();
                                                      if (nextNameExpenseItem == "date")
                                                          date = jsonReader.nextString();
                                                      if (nextNameExpenseItem == "description")
                                                          description = jsonReader.nextString();
                                                  }
                                                  jsonReader.endObject();
                                                  expenseItemsList.add(new ExpenseListItem(amount, date, description));
                                              }

                                              if (jsonReader.nextName() == "donationStats") {
                                                  jsonReader.beginObject();
                                                  while (jsonReader.hasNext()) {
                                                      String nextNameDonationItem = jsonReader.nextName();

                                                      if (nextNameDonationItem == "amount")
                                                          amount = jsonReader.nextString();
                                                      if (nextNameDonationItem == "date")
                                                          date = jsonReader.nextString();
                                                  }
                                                  jsonReader.endObject();
                                                  donationItemsList.add(new DonationListItem(amount, date));
                                              }
                                          }
                                          jsonReader.endObject();
                                      } else {
                                          Log.i("[HttpsUrlConnection]", Integer.toString(connection.getResponseCode()));

                                          expenseItemsList = new ArrayList<ExpenseListItem>();
                                          expenseItemsList.add(new ExpenseListItem("50 €", "01.02.2004", "Blabla Tampon."));
                                          expenseItemsList.add(new ExpenseListItem("34 €", "02.02.2004", "Blablaasdasd Tampon."));
                                          expenseItemsList.add(new ExpenseListItem("63 €", "03.02.2004", "Blablaasdasdasd Tampon."));
                                          expenseItemsList.add(new ExpenseListItem("1 €", "04.02.2004", "Blabla Tampon.asdasas"));
                                          expenseItemsList.add(new ExpenseListItem("50 €", "05.02.2004", "Blabla Tampon."));
                                          expenseItemsList.add(new ExpenseListItem("56 €", "06.02.2004", "Blabla Tamponasdasd."));
                                          expenseItemsList.add(new ExpenseListItem("54 €", "07.02.2004", "Blabla Tampon."));


                                          donationItemsList = new ArrayList<DonationListItem>();
                                          donationItemsList.add(new DonationListItem("50 €", "01.02.2004"));
                                          donationItemsList.add(new DonationListItem("533 €", "01.03.2004"));
                                          donationItemsList.add(new DonationListItem("12 €", "01.04.2004"));
                                          donationItemsList.add(new DonationListItem("4 €", "01.05.2004"));
                                          donationItemsList.add(new DonationListItem("56 €", "01.06.2004"));
                                          donationItemsList.add(new DonationListItem("3 €", "01.07.2004"));
                                          donationItemsList.add(new DonationListItem("50 €", "01.08.2004"));
                                          donationItemsList.add(new DonationListItem("54 €", "01.09.2004"));
                                          donationItemsList.add(new DonationListItem("87 €", "01.10.2004"));


                                      }
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                  }
                              }
                          });


        expensesAdapter = new ExpensesListAdapter(this, R.layout.view_expense_list_item, (ArrayList<ExpenseListItem>) expenseItemsList);
        donationsAdapter = new DonationListAdapter(this, R.layout.view_donation_list_item, (ArrayList<DonationListItem>) donationItemsList);


        donations = (ListView) findViewById(R.id._dynamicListViewDonations);
        donations.setAdapter(donationsAdapter);

        expenses = (ListView) findViewById(R.id._dynamicListViewExpenses);
        expenses.setAdapter(expensesAdapter);

        donations = (ListView) findViewById(R.id._dynamicListViewDonations);
        // donations.setAdapter(donationsAdapter);

        toDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(donationActivity);
            }
        });

        toAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(addExpenseActivity);
            }
        });

    }
}
