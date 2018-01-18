package com.campusconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.campusconnection.model.responses.GenericResponse;
import com.campusconnection.model.responses.SettingsBody;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.util.PreferencesUtil;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    private SettingsBody mSettingsResponse;
    private EditText mSchoolText;
    private Switch mMaleSwitch;
    private Switch mFemaleSwitch;
    private RangeSeekBar mSettingsDefaultAgeBar;
    private Switch mMessagesSwitch;
    private Switch mMatchesSwitch;
    private Switch mLikesSwitch;
    private Switch mEventsSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);

        getDefaultSettings();

        mSchoolText = (EditText) findViewById(R.id.settings_school_input);
        mMaleSwitch = (Switch) findViewById(R.id.switch_male);
        mFemaleSwitch = (Switch) findViewById(R.id.switch_female);
        mSettingsDefaultAgeBar = (RangeSeekBar)findViewById(R.id.settingsAgeInput);
        mMessagesSwitch = (Switch) findViewById(R.id.switch_messages_noti);
        mMatchesSwitch = (Switch) findViewById(R.id.switch_matches_noti);
        mLikesSwitch = (Switch) findViewById(R.id.switch_likes_noti);
        mEventsSwitch = (Switch) findViewById(R.id.switch_events_noti);
        Button logoutBtn = (Button) findViewById(R.id.settings_logout_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogout();
            }
        });

    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        postDefaultSettings();
//    }


    @Override
    public void onBackPressed() {
        finish();
        postDefaultSettings();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    public int getProfileId() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                return extras.getInt("id");
            }
        }
        return -1;
    }

    public void getDefaultSettings() {
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        Call<SettingsBody> call = apiService.getSettings();

        call.enqueue(new Callback<SettingsBody>() {
            @Override
            public void onResponse(Call<SettingsBody> call, Response<SettingsBody> response) {

                mSettingsResponse = response.body();
                if(mSettingsResponse == null)
                    mSettingsResponse = new SettingsBody(true, "", 0,0,0,0,0,0,0,0); //TODO get copy from local storage?

                mSchoolText.setText(mSettingsResponse.getSchool());

                mMaleSwitch.setChecked(mSettingsResponse.getMale() == 1);
                mFemaleSwitch.setChecked(mSettingsResponse.getFemale() == 1);

                mSettingsDefaultAgeBar.setSelectedMaxValue(mSettingsResponse.getAgeHigh());
                mSettingsDefaultAgeBar.setSelectedMinValue(mSettingsResponse.getAgeLow());

                mMessagesSwitch.setChecked(mSettingsResponse.getMessages() == 1);
                mMatchesSwitch.setChecked(mSettingsResponse.getMatches() == 1);
                mLikesSwitch.setChecked(mSettingsResponse.getLikes() == 1);
                mEventsSwitch.setChecked(mSettingsResponse.getEvents() == 1);
            }

            @Override
            public void onFailure(Call<SettingsBody> call, Throwable t) {
                mSettingsResponse = new SettingsBody(true, "", 0,0,0,0,0,0,0,0);
            }
        });

    }

    public void postDefaultSettings() {

        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        SettingsBody updatedSettings = new SettingsBody(mSchoolText.getText().toString(),
                mMaleSwitch.isChecked() ? 1 : 0,
                mFemaleSwitch.isChecked() ? 1 : 0,
                (int) mSettingsDefaultAgeBar.getSelectedMinValue(),
                (int) mSettingsDefaultAgeBar.getSelectedMaxValue(),
                mMessagesSwitch.isChecked() ? 1 : 0,
                mLikesSwitch.isChecked() ? 1 : 0,
                mMatchesSwitch.isChecked() ? 1 : 0,
                mEventsSwitch.isChecked() ? 1 : 0);
        ;
        Call<GenericResponse> call = apiService.postSettings(updatedSettings);

        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });

    }

    public void doLogout() {
        PreferencesUtil prefs = new PreferencesUtil(this);
        prefs.setStringPreference(getString(R.string.jwtPref), "");
        prefs.setBooleanPreference(getString(R.string.isLoggedPref), false);

        Intent i = new Intent(SettingsActivity.this, SplashScreenActivity.class);
        startActivity(i);
    }
}
