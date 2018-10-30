package hwr.stud.tamponapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import hwr.stud.mylibrary.DonationListAdapter;
import hwr.stud.mylibrary.DonationListItem;
import hwr.stud.mylibrary.ExpenseListItem;
import hwr.stud.mylibrary.ExpensesListAdapter;

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
        expenseItemsList.add(new ExpenseListItem("50 €", "01.02.2004", "Blabla Tampon."));
        expenseItemsList.add(new ExpenseListItem("34 €", "02.02.2004", "Blablaasdasd Tampon."));
        expenseItemsList.add(new ExpenseListItem("63 €", "03.02.2004", "Blablaasdasdasd Tampon."));
        expenseItemsList.add(new ExpenseListItem("1 €", "04.02.2004", "Blabla Tampon.asdasas"));
        expenseItemsList.add(new ExpenseListItem("50 €", "05.02.2004", "Blabla Tampon."));
        expenseItemsList.add(new ExpenseListItem("56 €", "06.02.2004", "Blabla Tamponasdasd."));
        expenseItemsList.add(new ExpenseListItem("54 €", "07.02.2004", "Blabla Tampon."));


        expensesAdapter = new ExpensesListAdapter(this, R.layout.view_expense_list_item, (ArrayList<ExpenseListItem>) expenseItemsList);

        expenses = (ListView) findViewById(R.id._dynamicListViewExpenses);
        expenses.setAdapter(expensesAdapter);

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

        donationsAdapter = new DonationListAdapter(this, R.layout.view_donation_list_item, (ArrayList<DonationListItem>) donationItemsList);

        donations = (ListView) findViewById(R.id._dynamicListViewDonations);
        donations.setAdapter(donationsAdapter);

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
