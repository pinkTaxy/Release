package hwr.stud.tamponapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddExpenseActitvity extends AppCompatActivity {

    Button addExpense;
    Intent privateStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_acitvity);

        addExpense = (Button) findViewById(R.id.addExpense);
        privateStats = new Intent(this, AddExpenseActitvity.class);

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Do camera stuff here

                startActivity(privateStats);
            }
        });

    }
}
