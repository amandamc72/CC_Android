package com.campusconnection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.campusconnection.model.responses.GenericResponse;
import com.campusconnection.model.requests.LoginRequest;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.util.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    private EditText mEmail;
    private EditText mPassword;
    private ProgressBar mProgress;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(R.style.AppTheme);

        mProgress = (ProgressBar) findViewById(R.id.loginProgressBar);
        mProgress.setVisibility(View.INVISIBLE);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mEmail = (EditText) findViewById(R.id.loginEmailInput);
        mPassword = (EditText) findViewById(R.id.loginPassInput);
        mPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mLoginBtn = (Button) findViewById(R.id.loginButton);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               attemptLogin();
            }
        });
        Button mJoinBtn = (Button) findViewById(R.id.signupButton);
        mJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void attemptLogin() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        ArrayList<EditText> fields = new ArrayList<>(Arrays.asList(mEmail, mPassword));
        AppUtils.ValidInput validInput = AppUtils.isInputsValid(fields);
        View focusView;

        if (validInput.getIsBlank()){
            validInput.getField().setError(getString(R.string.error_field_required));
            focusView = validInput.getField();
            focusView.requestFocus();

        } else if (validInput.getIsValidEmail()){
            validInput.getField().setError(getString(R.string.error_invalid_email));
            focusView = validInput.getField();
            focusView.requestFocus();

        } else {
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            Call<GenericResponse> call = apiService.checkLogin(new LoginRequest(email, password));
            mProgress.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    GenericResponse res = response.body();
                    Boolean error = res.getError();
                    String message = res.getMessage();
                    String JWT = res.getCode();
                    mProgress.setVisibility(View.INVISIBLE);
                    if (!error) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("jwt", JWT);
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                    } else {
                        AppUtils.showPopMessage(LoginActivity.this, message);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    call.cancel();
                    mProgress.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
