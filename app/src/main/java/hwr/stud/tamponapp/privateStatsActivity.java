package hwr.stud.tamponapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class privateStatsActivity extends AppCompatActivity {

    Button toDonation;
    Button toAddExpense;

    Intent donationActivity;
    Intent addExpenseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_stats);

        toDonation = (Button) findViewById(R.id.toDonation);
        donationActivity = new Intent(this, DonationActivity.class);

        toAddExpense = (Button) findViewById(R.id.toAddExpense);
        addExpenseActivity = new Intent(this, AddExpenseActivity.class);

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
