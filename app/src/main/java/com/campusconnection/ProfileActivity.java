package com.campusconnection;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.campusconnection.model.responses.MemberResponse;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.views.ProfileInterestTags;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    private SliderLayout mProfilePicSlider;
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

        mProfilePicSlider = (SliderLayout)findViewById(R.id.profileImageSlider);
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        if (profileId() == 0) {
            getMenuInflater().inflate(R.menu.edit_action_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle extras = new Bundle();
        Intent intent = new Intent(this, EditProfileActivity.class);
        extras.putParcelable("memberResponse", mMemberResponse);
        extras.putInt("id", profileId());
        intent.putExtras(extras);
        //startActivity(intent);
        startActivityForResult(intent, 1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("D", "onActivityResult in add profile activity!!!!");
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //Bundle extras = getIntent().getExtras();
                //Boolean result = data.getBooleanExtra("result", true);
                //Log.d("D", "result  " + result);
                mMemberResponse = (MemberResponse) data.getParcelableExtra("updatedProfile");
                setProfileViews();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void getProfile() {
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        Call<MemberResponse> call = apiService.getProfile(profileId());

        call.enqueue(new Callback<MemberResponse>() {
            @Override
            public void onResponse(Call<MemberResponse> call, Response<MemberResponse> response) {
                if(response.isSuccessful()) {
                    mMemberResponse = response.body();
                    mMemberResponse.consolidateImages();
                    setProfileViews();
                } else {
                    //TODO display not found
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void setProfileViews() {
        ArrayList<String> pictures = mMemberResponse.getPictures();
        mProfilePicSlider.removeAllSliders();
        //Put pictures in image slider
        if (pictures.size() == 1) { //no swipe if one pic
            mProfilePicSlider.setPagerTransformer(false, new BaseTransformer() {

                @Override
                public void onTransform(View view, float position) {
                }

            });
        }
        for(int i = 0; i < pictures.size(); i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(ProfileActivity.this);
            defaultSliderView
                    .image(pictures.get(i))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(ProfileActivity.this);
            mProfilePicSlider.addSlider(defaultSliderView);
        }
        mProfilePicSlider.stopAutoCycle();

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
    public void onSliderClick(BaseSliderView baseSliderView) {
        Log.d("D","onSliderClick  : " + baseSliderView.getUrl());
    }
}
