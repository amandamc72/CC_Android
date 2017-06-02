package com.campusconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpPathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_path);

        Button mFacebookBtn = (Button) findViewById(R.id.signupFacebookButton);
        Button mEmailBtn = (Button) findViewById(R.id.signupEmailButton);


        mEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpPathActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }
}
