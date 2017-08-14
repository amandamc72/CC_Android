package com.campusconnection;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusconnection.model.GenericResponse;
import com.campusconnection.model.MemberListResponse;
import com.campusconnection.model.MemberResponse;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.views.ProfileInterestTags;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private ImageView mImage;
    private TextView mName;
    private TextView mAge;
    private TextView mSchool;
    private TextView mMajor;
    private TextView mMinor;
    private TextView mCity;
    private TextView mState;
    private TextView mStanding;
    private TextView mAbout;
    private ProfileInterestTags mInterestsTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mImage = (ImageView) findViewById(R.id.profileImage);
        mName = (TextView) findViewById(R.id.profileName);
        mAge = (TextView) findViewById(R.id.profileAge);
        mSchool = (TextView) findViewById(R.id.profileSchool);
        mMajor = (TextView) findViewById(R.id.profileMajor);
        mMinor = (TextView) findViewById(R.id.profileMinor);
        mCity = (TextView) findViewById(R.id.profileCity);
        mState = (TextView) findViewById(R.id.profileState);
        mStanding = (TextView) findViewById(R.id.profileStanding);
        mAbout = (TextView) findViewById(R.id.profileAbout);
        mInterestsTags = (ProfileInterestTags) findViewById(R.id.profileInterestsTags);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button messageButton = (Button) findViewById(R.id.profileMessageButton);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        getProfile();

    }

    public int profileId() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            return extras != null ? extras.getInt("id") : 0;
        }
        return 0;
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private void getProfile() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MemberResponse> call = apiService.getProfile(profileId());

        call.enqueue(new Callback<MemberResponse>() {
            @Override
            public void onResponse(Call<MemberResponse> call, Response<MemberResponse> response) {
                MemberResponse res = response.body();

                Picasso.with(ProfileActivity.this).load(res.getThumbnail()).into(mImage);
                Log.d("D","thumb is: " +res.getThumbnail());
                mName.setText(res.getName());
                mAge.setText(res.getAge().toString());
                mSchool.setText(res.getSchool());
                mMajor.setText(res.getMajor());
                mMinor.setText(res.getMinor());
                mCity.setText(res.getCity().concat(","));
                mState.setText(res.getState());
                mStanding.setText(res.getStanding());
                mAbout.setText(res.getAbout());

                List interestsPreClean = res.getInterests();
                ArrayList<String> interests = new ArrayList<>();

                for (int i = 0; i < interestsPreClean.size(); i++){
                    interests.add(interestsPreClean.get(i).toString().replace("[", "").replace("]", ""));
                }
                mInterestsTags.setTags(interests);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }



}
