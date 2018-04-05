package com.campusconnection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.campusconnection.fragments.AddPicturesFragment;
import com.campusconnection.model.responses.GenericResponse;
import com.campusconnection.model.requests.SignupRequest;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.util.AppUtils;
import com.campusconnection.util.DatePickerFragment;
import com.campusconnection.util.PreferencesUtil;

import java.util.ArrayList;
import java.util.Arrays;

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
    private ProgressBar mProgress;
    private PreferencesUtil prefs;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTheme(R.style.AppTheme);

        prefs = new PreferencesUtil(this);
        code = prefs.getStringPreference(getString(R.string.codePref));
        Log.d("D","CODE is: " +  code);


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
        Button nextBtn = (Button) findViewById(R.id.signupNextButton);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
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

    private void signUp() {
        Intent intent = new Intent(SignUpActivity.this, SignUpPhotosActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
//        String firstName = mFirstName.getText().toString();
//        String lastName = mLastName.getText().toString();
//        String password = mPassword.getText().toString();
//        String city = mCity.getText().toString();
//        String state = mState.getSelectedItem().toString();
//        String school = mSchool.getText().toString();
//        String major = mMajor.getText().toString();
//        String minor = mMinor.getText().toString();
//        String birthday = mBithday.getText().toString();
//
//        ArrayList<EditText> textFields = new ArrayList<>(Arrays.asList(mFirstName, mLastName,
//                                                            mPassword, mCity, mSchool,
//                                                            mMajor, mBithday));
//        AppUtils.ValidInput validInput = AppUtils.isInputsValid(textFields);
//        View focusView;
//
//        //TODO validate radio button and state selector
//        if (validInput.getIsBlank()) {
//            validInput.getField().setError(getString(R.string.error_field_required));
//            focusView = validInput.getField();
//            focusView.requestFocus();
//
//        } else {
//            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
//            Call<GenericResponse> call = apiService.signup(new SignupRequest(code, firstName, lastName, password,
//                    city, state, school, major, minor, birthday, gender));
//            //mProgress.setVisibility(View.VISIBLE);
//
//            call.enqueue(new Callback<GenericResponse>() {
//                @Override
//                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//                    GenericResponse res = response.body();
//                    Boolean error = res.getError();
//                    String message = res.getMessage();
//                    //mProgress.setVisibility(View.INVISIBLE);
//                    if(!error) {
//                        Intent intent = new Intent(SignUpActivity.this, SignUpPhotosActivity.class);
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
//                    } else {
//                        AppUtils.showPopMessage(SignUpActivity.this, message);
//                    }
//                }
//                @Override
//                public void onFailure(Call call, Throwable t) {
//                    call.cancel();
//                    //mProgress.setVisibility(View.INVISIBLE);
//                }
//            });
//        }
    }
}
