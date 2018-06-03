package com.campusconnection;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.campusconnection.fragments.AddPicturesFragment;
import com.campusconnection.model.responses.GenericResponse;
import com.campusconnection.model.requests.SignupRequest;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.util.AppUtils;
import com.campusconnection.util.Constants;
import com.campusconnection.util.DatePickerFragment;
import com.campusconnection.util.FetchAddressIntentService;
import com.campusconnection.util.PreferencesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements AddPicturesFragment.OnFragmentInteractionListener {
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPassword;
    private EditText mLocation;
    private EditText mSchool;
    private EditText mMajor;
    public static EditText mBirthday;
    private RadioGroup mGender;
    private String gender;
    private ProgressBar mProgress;
    private PreferencesUtil prefs;
    private String code;
    private RelativeLayout mAddPhotosLayout;
    private LinearLayout mSignupDataLayout;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private AddressResultReceiver mResultReceiver;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTheme(R.style.AppTheme);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final Bundle instance = savedInstanceState;

        prefs = new PreferencesUtil(this);
        code = prefs.getStringPreference(getString(R.string.codePref));
        Log.d("D", "CODE is: " + code);

        mResultReceiver = new AddressResultReceiver(new Handler());
        mAddPhotosLayout = (RelativeLayout) findViewById(R.id.addPhotosLayout);
        mSignupDataLayout = (LinearLayout) findViewById(R.id.signUpDataLayout);
        mFirstName = (EditText) findViewById(R.id.signupFirstNameInput);
        mLastName = (EditText) findViewById(R.id.signupLastNameInput);
        mPassword = (EditText) findViewById(R.id.signupPasswordInput);
        mLocation = (EditText) findViewById(R.id.signupLocationInput);
        mSchool = (EditText) findViewById(R.id.signupSchoolInput);
        mMajor = (EditText) findViewById(R.id.signupMajorInput);
        mBirthday = (EditText) findViewById(R.id.signupBirthdayInput);
        mGender = (RadioGroup) findViewById(R.id.signupGenderRadioGroup);
        Button nextBtn = (Button) findViewById(R.id.signupNextButton);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mAddPhotosLayout.setVisibility(View.GONE);
        mLocation.setLongClickable(false);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(instance);
            }
        });

        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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

        mLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (mLocation.getRight() - mLocation.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!checkPermissions()) {
                            requestPermissions();
                        } else {
                            getLastLocation();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        mBirthday.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                    return true;
                }
                return false;
            }
        });
    }

    private void signUp(Bundle savedInstanceState) {
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String password = mPassword.getText().toString();
        String school = mSchool.getText().toString();
        String major = mMajor.getText().toString();
        String birthday = mBirthday.getText().toString();
        final Bundle instanceState = savedInstanceState;
        String [] location = mLocation.getText().toString().split(",");
        String city = location[0];
        String state = location[1];

        ArrayList<EditText> textFields = new ArrayList<>(Arrays.asList(mFirstName, mLastName,
                                                            mPassword, mLocation, mSchool,
                                                            mMajor, mBirthday));
        AppUtils.ValidInput validInput = AppUtils.isInputsValid(textFields);
        View focusView;

        if (validInput.getIsBlank()) {
            validInput.getField().setError(getString(R.string.error_field_required));
            focusView = validInput.getField();
            focusView.requestFocus();

        } else {
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            Call<GenericResponse> call = apiService.signup(new SignupRequest(code, firstName, lastName, password,
                    city, state, school, major, birthday, gender));
            //mProgress.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if(response.isSuccessful()) {
                        GenericResponse res = response.body();
                        Boolean error = res.getError();
                        String message = res.getMessage();
                        //mProgress.setVisibility(View.INVISIBLE);
                        if(!error) {
                            if (findViewById(R.id.addPhotosContainer) != null) {
                                // so we don't end up with overlapping fragments.
                                if (instanceState != null) {
                                    return;
                                }
                                AddPicturesFragment addPicturesFragment = new AddPicturesFragment().newInstance(new ArrayList<String>());

                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.addPhotosContainer, addPicturesFragment).commit();
                            }
                            mAddPhotosLayout.setVisibility(View.VISIBLE);
                            mSignupDataLayout.setVisibility(View.GONE);
                        } else {
                            AppUtils.showPopMessage(SignUpActivity.this, message);
                        }
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

    @Override
    public void onImagePass(ArrayList<String> imgs) {
    }



    // Getting user location handling //
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            if (!Geocoder.isPresent()) {
                                Toast.makeText(SignUpActivity.this,
                                        R.string.no_geocoder_available,
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            startIntentService();
                        } else {
                            AppUtils.showPopMessage(SignUpActivity.this, getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(SignUpActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                Constants.REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startLocationPermissionRequest();
                        }
                    });
        } else {
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_PERMISSIONS_REQUEST_CODE
                && grantResults.length != 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if(resultData == null) {
                return;
            }
            String output = resultData.getString(Constants.RESULT_DATA_KEY);
            if(resultCode == 0) {
                if (output != null) {
                    mLocation.setText(output);
                }
            } else {
                Toast.makeText(SignUpActivity.this,
                        output,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
