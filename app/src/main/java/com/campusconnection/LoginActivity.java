package com.campusconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.campusconnection.util.PreferencesUtil;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private ProgressBar mProgress;
    private PreferencesUtil mPrefs;
    private TextView mForgotPasswordText;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(R.style.AppTheme);
        mPrefs = new PreferencesUtil(this);

        mForgotPasswordText = (TextView) findViewById(R.id.forgotPasswordText);
        mLoginBtn = (Button) findViewById(R.id.loginButton);
        Button joinBtn = (Button) findViewById(R.id.signupButton);
        mProgress = (ProgressBar) findViewById(R.id.loginProgressBar);
        mEmail = (EditText) findViewById(R.id.loginEmailInput);
        mPassword = (EditText) findViewById(R.id.loginPassInput);

        mPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               login();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = mPrefs.getStringPreference(getString(R.string.codePref));
                mEmail.setVisibility(View.INVISIBLE);
                mPassword.setVisibility(View.INVISIBLE);
                mLoginBtn.setVisibility(View.INVISIBLE);
                mForgotPasswordText.setVisibility(View.INVISIBLE);
                mProgress.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(code))
                    checkStatus(code);
                else {
                    Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mEmail.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.VISIBLE);
        mLoginBtn.setVisibility(View.VISIBLE);
        mForgotPasswordText.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.INVISIBLE);
    }


    private void login() {
        ArrayList<EditText> fields = new ArrayList<>(Arrays.asList(mEmail, mPassword));
        AppUtils.ValidInput validInput = AppUtils.isInputsValid(fields);
        View focusView;

        if (validInput.getIsBlank()) {
            validInput.getField().setError(getString(R.string.error_field_required));
            focusView = validInput.getField();
            focusView.requestFocus();

        } else if (validInput.getIsValidEmail()) {
            validInput.getField().setError(getString(R.string.error_invalid_email));
            focusView = validInput.getField();
            focusView.requestFocus();

        } else {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            Call<GenericResponse> call = apiService.checkLogin(new LoginRequest(email, password));
            mProgress.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if(response.isSuccessful()) {
                        GenericResponse res = response.body();
                        Boolean error = res.getError();
                        String message = res.getMessage();
                        String JWT = res.getCode();
                        mProgress.setVisibility(View.INVISIBLE);
                        if (!error) {
                            mPrefs.setStringPreference(getString(R.string.jwtPref), JWT);
                            mPrefs.setBooleanPreference(getString(R.string.isLoggedPref), true);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                            finish();
                        } else {
                            AppUtils.showPopMessage(LoginActivity.this, message);
                        }
                    } else {
                        onFailure(call, null);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    call.cancel();
                    mProgress.setVisibility(View.INVISIBLE);
                    AppUtils.showPopMessage(LoginActivity.this, "Could not complete request");
                }
            });
        }
    }

    private void checkStatus(String code) {
        Log.d("D", "CODE is: " + code);
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        Call<GenericResponse> call = apiService.checkStatus(code);

        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                GenericResponse res = response.body();
                String status = res.getMessage();
                Log.d("D", "res is: " + res);
                Class actToRouteTo;
                if(status.equals("verified")) {
                    actToRouteTo = SignUpActivity.class;
                } else {
                    actToRouteTo = RegisterActivity.class;
                }
                Intent i = new Intent(LoginActivity.this, actToRouteTo);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }
}
