package hwr.stud.tamponapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

public class ContentActivity  extends AppCompatActivity {
    Button login = (Button) findViewById(R.id.bu_login);
    LinearLayout header = new LinearLayout(this);
    TextView tv_donations_all = findViewById(R.id.tv_value_all);
    TextView tv_donations_own = findViewById(R.id.tv_value_own);
    ListView lv_items = findViewById(R.id.lv_items);
    RelativeLayout t = R.layout.list_item_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        OnButtonPressed();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("message_return", "This data is returned when user click back menu in target activity.");
        setResult(RESULT_OK, intent);
        finish();
    }

    private LinearLayout contentItem (){

        LinearLayout la = new LinearLayout(this);



        return la;
    }

    private void addItem(String text){


    }
    private class ListItemAdapter extends ArrayAdapter<RelativeLayout>{


    }
    private void OnButtonPressed(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }





}
