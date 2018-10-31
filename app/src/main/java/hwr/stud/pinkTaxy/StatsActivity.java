package hwr.stud.pinkTaxy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        TextView statsView = (TextView) findViewById(R.id.statsView);
        statsView.setText("blabla lablablablablablabla");
        }
}
