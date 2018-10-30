package hwr.stud.tamponapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class PrivateStatsActivity extends AppCompatActivity {

    Button toDonation;
    Button toAddExpense;

    ListView expenses;
    ListView donations;

    ArrayAdapter<String> expensesAdapter;
    ArrayAdapter<String> donationsAdapter;

    Intent donationActivity;
    Intent addExpenseActivity;

    String[] donationsArray = {"10 €", "20 €", "30 €", "40 €", "50 €"};
    String[] expensesArray = {"15 €", "25 €", "35 €", "45 €", "55 €"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_stats);

        toDonation = (Button) findViewById(R.id.toDonation);
        donationActivity = new Intent(this, DonationActivity.class);

        toAddExpense = (Button) findViewById(R.id.toAddExpense);
        addExpenseActivity = new Intent(this, AddExpenseActitvity.class);

        donationsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, donationsArray);

        // ListAdapter adapter = new

        expensesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, expensesArray);

        expenses = (ListView) findViewById(R.id._dynamicListViewExpenses);
        expenses.setAdapter(expensesAdapter);

        donations = (ListView) findViewById(R.id._dynamicListViewDonations);
        donations.setAdapter(donationsAdapter);

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
