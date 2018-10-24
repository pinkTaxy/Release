package hwr.stud.tamponapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button toLogIn;
    Intent intentLogIn;

    Button toSignUp;
    Intent intentSignUp;

    Button toContent;
    Intent intentContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toLogIn = (Button) findViewById(R.id.toLogIn);
        intentLogIn = new Intent(this, LoginActivity.class);

        toSignUp = (Button) findViewById(R.id.toSignUp);
        intentSignUp = new Intent(this, SignupActivity.class);

        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentLogIn);
            }
        });

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentSignUp);
            }
        });

    }

}
