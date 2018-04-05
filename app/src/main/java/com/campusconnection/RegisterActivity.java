package com.campusconnection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.campusconnection.model.requests.RegisterRequest;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.util.AppUtils;
import com.campusconnection.util.PreferencesUtil;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail;
    private ProgressBar mProgress;
    private PreferencesUtil prefs;

    //TODO check status on act load NEED TO TEST!!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTheme(R.style.AppTheme);


        prefs = new PreferencesUtil(this);
        String code = prefs.getStringPreference(getString(R.string.codePref));
        Log.d("D","CODE is: " +  code);

        if (!TextUtils.isEmpty(code))
            checkStatus(code);

        mProgress = (ProgressBar) findViewById(R.id.registerProgressBar);
        mProgress.setVisibility(View.INVISIBLE);

        mEmail = (EditText) findViewById(R.id.registerEmailInput);
        mEmail.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    register();
                    return true;
                }
                return false;
            }
        });

        Button mRegisterBtn = (Button) findViewById(R.id.registerEmailButton);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String email = mEmail.getText().toString();

        ArrayList<EditText> fields = new ArrayList<>(Arrays.asList(mEmail));
        AppUtils.ValidInput validInput = AppUtils.isInputsValid(fields);
        View focusView;

        if (validInput.getIsBlank()) {
            validInput.getField().setError(getString(R.string.error_field_required));
            focusView = validInput.getField();
            focusView.requestFocus();

        } else if (validInput.getIsValidEmail()){
            validInput.getField().setError(getString(R.string.error_invalid_email));
            focusView = validInput.getField();
            focusView.requestFocus();

        } else {
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            Call<GenericResponse> call = apiService.register(new RegisterRequest(email));
            mProgress.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if(response.isSuccessful()) {
                        GenericResponse res = response.body();
                        String message = res.getMessage();

                        Log.d("D","Recieved Code is: " + res.getCode());

                        prefs.setStringPreference((getString(R.string.codePref)), res.getCode());
                        mProgress.setVisibility(View.INVISIBLE);
                        //TODO want response message in text field not pop up
                        AppUtils.showPopMessage(RegisterActivity.this, message);
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

    private void checkStatus(String code) {
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        Call<GenericResponse> call = apiService.checkStatus(code);

        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                GenericResponse res = response.body();
                Log.d("D","RESponse is: " + res);
                String status = res.getMessage();

                Log.d("D","STATUS is: " + status);
                if(status.equals("verified")){
                    Intent i = new Intent(RegisterActivity.this, SignUpPathActivity.class);
                    startActivity(i);
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }
}
