package com.campusconnection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.campusconnection.model.GenericResponse;
import com.campusconnection.model.LoginRequest;
import com.campusconnection.model.SignupRequest;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.util.DatePickerFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPassword;
    private EditText mCity;
    private Spinner mState;
    private EditText mSchool;
    private EditText mMajor;
    private EditText mMinor;
    private EditText mBithday;
    private RadioGroup mGender;
    private String gender;
    private AlertDialog.Builder alertResponse;
    private ProgressBar mProgress;
    private SharedPreferences prefs;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTheme(R.style.AppTheme);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        code = prefs.getString(getString(R.string.code_key), "");
        Log.d("D","CODE is: " +  code);

        alertResponse = new AlertDialog.Builder(this);

        mFirstName = (EditText) findViewById(R.id.signupFirstNameInput);
        mLastName = (EditText) findViewById(R.id.signupLastNameInput);
        mPassword = (EditText) findViewById(R.id.signupPasswordInput);
        mCity = (EditText) findViewById(R.id.signupCityInput);
        mState = (Spinner) findViewById(R.id.signupStateSelectInput);
        mSchool = (EditText) findViewById(R.id.signupSchoolInput);
        mMajor = (EditText) findViewById(R.id.signupMajorInput);
        mMinor = (EditText) findViewById(R.id.signupMinorInput);
        mBithday = (EditText) findViewById(R.id.signupBirthdayInput);
        mGender = (RadioGroup) findViewById(R.id.signupGenderRadioGroup);
        Button mSignUpBtn = (Button) findViewById(R.id.signupSubmitBtn);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });



        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId){
                    case R.id.signupFemaleRadioBtn:
                        gender = "female";
                        break;
                    case R.id.signupMaleRadioBtn:
                        gender = "male";
                        break;
                    default:
                        gender = null;
                        break;
                }
            }
        });

        mBithday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(stateAdapter);
    }

    private void attemptSignUp() {
        mFirstName.setError(null);
        mLastName.setError(null);
        mPassword.setError(null);
        mCity.setError(null);
        mSchool.setError(null);
        mMajor.setError(null);
        mMinor.setError(null);
        mBithday.setError(null);

        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String password = mPassword.getText().toString();
        String city = mCity.getText().toString();
        String state = mState.getSelectedItem().toString();
        String school = mSchool.getText().toString();
        String major = mMajor.getText().toString();
        String minor = mMinor.getText().toString();
        String birthday = mBithday.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(city)) {
            mCity.setError(getString(R.string.error_field_required));
            focusView = mCity;
            cancel = true;
        }
        if (mState.getSelectedItemPosition() == 0) {
            focusView = mState;
            cancel = true;
        }
        if (TextUtils.isEmpty(school)) {
            mSchool.setError(getString(R.string.error_field_required));
            focusView = mSchool;
            cancel = true;
        }
        if (TextUtils.isEmpty(major)) {
            mMajor.setError(getString(R.string.error_field_required));
            focusView = mMajor;
            cancel = true;
        }
        if (TextUtils.isEmpty(birthday)) {
            mBithday.setError(getString(R.string.error_field_required));
            focusView = mBithday;
            cancel = true;
        }
        if (TextUtils.isEmpty(gender)) {
            focusView = mGender;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GenericResponse> call = apiService.signup(new SignupRequest(code, firstName, lastName, password,
                    city, state, school, major, minor, birthday, gender));
            //mProgress.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    GenericResponse res = response.body();
                    Boolean error = res.getError();
                    String message = res.getMessage();
                    //mProgress.setVisibility(View.INVISIBLE);
                    if(!error){
                        //TODO Goto home activity
                        Log.d("D","IT WORKED!!!!!!!!!");
                    }
                    else{
                        alertResponse.setMessage(message);
                        alertResponse.setPositiveButton(R.string.popup_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });
                        alertResponse.show();
                    }
                }
                @Override
                public void onFailure(Call call, Throwable t) {
                    call.cancel();
                    //mProgress.setVisibility(View.INVISIBLE);
                }
            });
        }

    }
}
