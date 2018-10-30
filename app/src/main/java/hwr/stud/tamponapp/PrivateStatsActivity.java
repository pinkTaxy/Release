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

import hwr.stud.mylibrary.ExpenseListItem;
import hwr.stud.mylibrary.ExpensesListAdapter;

public class PrivateStatsActivity extends AppCompatActivity {

    Button toDonation;
    Button toAddExpense;

    ListView expenses;
    ListView donations;

    ExpensesListAdapter expensesAdapter;

    List<ExpenseListItem> expenseItemsList;

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

        // donationsAdapter = new ExpensesListAdapter();

        // ListAdapter adapter = new

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
