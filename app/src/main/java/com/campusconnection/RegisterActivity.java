package com.campusconnection;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTheme(R.style.AppTheme);

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
            String email = mEmail.getText().toString();
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            Call<GenericResponse> call = apiService.register(new RegisterRequest(email));
            mProgress.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if(response.isSuccessful()) {
                        GenericResponse res = response.body();
                        String message = res.getMessage();
                        PreferencesUtil prefs = new PreferencesUtil(RegisterActivity.this);
                        prefs.setStringPreference((getString(R.string.codePref)), res.getCode());
                        mProgress.setVisibility(View.INVISIBLE);
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
}
