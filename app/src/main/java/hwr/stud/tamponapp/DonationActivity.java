package hwr.stud.tamponapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DonationActivity extends AppCompatActivity {

    Button donate;

    Intent privateStatsAcitvity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        donate = (Button) findViewById(R.id.donate);

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: do donation

                startActivity(privateStatsAcitvity);
            }
        });
    }
}
