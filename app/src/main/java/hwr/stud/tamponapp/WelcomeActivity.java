package hwr.stud.tamponapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    //TODO: add responsive font size!!!!!!!!111!!!!

    Button toLogIn;
    Intent intentLogIn;

    Button toSignUp;
    Intent intentSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        toLogIn = (Button) findViewById(R.id.toLogin);
        intentLogIn = new Intent(this, LoginActivity.class);

        toSignUp = (Button) findViewById(R.id.toSignup);
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
