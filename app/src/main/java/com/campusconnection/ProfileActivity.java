package com.campusconnection;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private TextView mNameAndAge;
    private TextView mSchool;
    private TextView mMajor;
    private TextView mMinor;
    private TextView mLocation;
    private TextView mStanding;
    private TextView mAbout;
    private ProfileInterestTags mInterestsTags;
    private MemberResponse mMemberResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mImage = (ImageView) findViewById(R.id.profileImage);
        mNameAndAge = (TextView) findViewById(R.id.profileNameAndAge);
        mSchool = (TextView) findViewById(R.id.profileSchool);
        mMajor = (TextView) findViewById(R.id.profileMajor);
        mMinor = (TextView) findViewById(R.id.profileMinor);
        mLocation = (TextView) findViewById(R.id.profileLocation);
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
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_action_bar, menu); //TODO only show this when im viewing my own profile
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle extras = new Bundle();
        Intent intent = new Intent(this, EditProfileActivity.class);
        extras.putParcelable("memberResponse", mMemberResponse);
        extras.putInt("id", profileId());
        intent.putExtras(extras);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void getProfile() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MemberResponse> call = apiService.getProfile(profileId());

        call.enqueue(new Callback<MemberResponse>() {
            @Override
            public void onResponse(Call<MemberResponse> call, Response<MemberResponse> response) {

                mMemberResponse = response.body();
                Picasso.with(ProfileActivity.this).load(mMemberResponse.getThumbnail()).into(mImage);
                mNameAndAge.setText(mMemberResponse.getName() + ", " + mMemberResponse.getAge().toString());
                mSchool.setText(mMemberResponse.getSchool());
                mMajor.setText(mMemberResponse.getMajor());
                mMinor.setText(mMemberResponse.getMinor());
                mLocation.setText(mMemberResponse.getCity() + ", " + mMemberResponse.getState());
                mStanding.setText(mMemberResponse.getStanding());
                mAbout.setText(mMemberResponse.getAbout());

                List interestsPreClean = mMemberResponse.getInterests();
                ArrayList<String> interests = new ArrayList<>();

                for (int i = 0; i < interestsPreClean.size(); i++) {
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
