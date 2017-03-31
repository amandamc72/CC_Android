package com.campusconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.campusconnection.model.GenericResponse;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(R.style.AppTheme);

        mEmailView = (EditText) findViewById(R.id.loginEmailInput);
        mPasswordView = (EditText) findViewById(R.id.loginPassInput);
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.loginProgressBar);
        mProgress.setVisibility(View.INVISIBLE);

        Button mLoginBtn = (Button) findViewById(R.id.loginButton);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GenericResponse> call = apiService.checkLogin(email, password);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                GenericResponse res = response.body();
                Boolean error = res.getError();
                String message = res.getMessage();

            } 

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });

    }
}
