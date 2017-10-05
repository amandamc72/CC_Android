package com.campusconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.campusconnection.model.GenericResponse;
import com.campusconnection.model.SearchRequest;
import com.campusconnection.model.SettingsBody;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    private SettingsBody mSettingsResponse;
    private EditText schoolText;
    private Switch maleSwitch;
    private Switch femaleSwitch;
    private RangeSeekBar settingsDefaultAgeBar;
    private Switch messagesSwitch;
    private Switch matchesSwitch;
    private Switch likesSwitch;
    private Switch eventsSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);

        getDefaultSettings();

        schoolText = (EditText) findViewById(R.id.settings_school_input);
        maleSwitch = (Switch) findViewById(R.id.switch_male);
        femaleSwitch = (Switch) findViewById(R.id.switch_female);
        settingsDefaultAgeBar = (RangeSeekBar)findViewById(R.id.settingsAgeInput);
        messagesSwitch = (Switch) findViewById(R.id.switch_messages_noti);
        matchesSwitch = (Switch) findViewById(R.id.switch_matches_noti);
        likesSwitch = (Switch) findViewById(R.id.switch_likes_noti);
        eventsSwitch = (Switch) findViewById(R.id.switch_events_noti);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        postDefaultSettings();
    }


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
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SettingsBody> call = apiService.getSettings(getProfileId());

        call.enqueue(new Callback<SettingsBody>() {
            @Override
            public void onResponse(Call<SettingsBody> call, Response<SettingsBody> response) {

                mSettingsResponse = response.body();
                if(mSettingsResponse == null)
                    mSettingsResponse = new SettingsBody(true, "", 0,0,0,0,0,0,0,0); //TODO get copy from local storage?

                schoolText.setText(mSettingsResponse.getSchool());

                maleSwitch.setChecked(mSettingsResponse.getMale() == 1);
                femaleSwitch.setChecked(mSettingsResponse.getFemale() == 1);

                settingsDefaultAgeBar.setSelectedMaxValue(mSettingsResponse.getAgeHigh());
                settingsDefaultAgeBar.setSelectedMinValue(mSettingsResponse.getAgeLow());

                messagesSwitch.setChecked(mSettingsResponse.getMessages() == 1);
                matchesSwitch.setChecked(mSettingsResponse.getMatches() == 1);
                likesSwitch.setChecked(mSettingsResponse.getLikes() == 1);
                eventsSwitch.setChecked(mSettingsResponse.getEvents() == 1);
            }

            @Override
            public void onFailure(Call<SettingsBody> call, Throwable t) {
                mSettingsResponse = new SettingsBody(true, "", 0,0,0,0,0,0,0,0);
            }
        });

    }

    public void postDefaultSettings() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        SettingsBody updatedSettings = new SettingsBody(schoolText.getText().toString(),
                maleSwitch.isChecked() ? 1 : 0,
                femaleSwitch.isChecked() ? 1 : 0,
                (int) settingsDefaultAgeBar.getSelectedMinValue(),
                (int) settingsDefaultAgeBar.getSelectedMaxValue(),
                messagesSwitch.isChecked() ? 1 : 0,
                likesSwitch.isChecked() ? 1 : 0,
                matchesSwitch.isChecked() ? 1 : 0,
                eventsSwitch.isChecked() ? 1 : 0);
        ;
        Call<GenericResponse> call = apiService.postSettings(1001, updatedSettings); //TODO change back end to send back user id

        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });

    }
}
