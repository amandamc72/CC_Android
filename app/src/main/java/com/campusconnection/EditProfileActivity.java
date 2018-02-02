package com.campusconnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.campusconnection.fragments.AddPicturesFragment;
import com.campusconnection.model.requests.RemoveRequest;
import com.campusconnection.model.responses.GenericResponse;
import com.campusconnection.model.responses.MemberResponse;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.util.AppUtils;
import com.campusconnection.views.ProfileInterestTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements AddPicturesFragment.OnFragmentInteractionListener {

    private EditText mEditSchool;
    private EditText mEditMajor;
    private EditText mEditMinor;
    private EditText mEditLocation;
    private EditText mEditStanding;
    private EditText mEditAbout;
    private ProfileInterestTags mEditTags;
    private MemberResponse mCurrentMember;
    private ArrayList<String> mUpdatedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.editProfileToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        getCurrentProfileData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mEditSchool = (EditText) findViewById(R.id.editSchoolText);
        mEditMajor = (EditText) findViewById(R.id.editMajorText);
        mEditMinor = (EditText) findViewById(R.id.editMinorText);
        mEditLocation = (EditText) findViewById(R.id.editLocation);
        mEditStanding = (EditText) findViewById(R.id.editStandingText);
        mEditAbout = (EditText) findViewById(R.id.editAboutText);
        mEditTags = (ProfileInterestTags) findViewById(R.id.editInterests);


        if (mCurrentMember != null) {
            if (findViewById(R.id.gridFragmentContainer) != null) {

                // so we don't end up with overlapping fragments.
                if (savedInstanceState != null) {
                    return;
                }
                AddPicturesFragment addPicturesFragment = new AddPicturesFragment().newInstance(mCurrentMember.getPictures());

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.gridFragmentContainer, addPicturesFragment).commit();
            }

            mEditSchool.setText(mCurrentMember.getSchool());
            mEditSchool.setBackgroundColor(getResources().getColor(R.color.light_gray));

            mEditMajor.setText(mCurrentMember.getMajor());
            mEditMajor.setBackgroundColor(getResources().getColor(R.color.light_gray));

            mEditMinor.setText(mCurrentMember.getMinor());
            mEditMinor.setBackgroundColor(getResources().getColor(R.color.light_gray));

            mEditLocation.setText(mCurrentMember.getCity() + ", " + mCurrentMember.getState());
            mEditLocation.setBackgroundColor(getResources().getColor(R.color.light_gray));

            mEditStanding.setText(mCurrentMember.getStanding());
            mEditStanding.setBackgroundColor(getResources().getColor(R.color.light_gray));

            mEditAbout.setText(mCurrentMember.getAbout());
            mEditAbout.setBackgroundColor(getResources().getColor(R.color.light_gray));

            List interestsPreClean = mCurrentMember.getInterests();
            ArrayList<String> interests = new ArrayList<>();

            for (int i = 0; i < interestsPreClean.size(); i++) {
                interests.add(interestsPreClean.get(i).toString().replace("[", "").replace("]", ""));
            }
            mEditTags.setTags(interests);


        } else {
            //TODO handel error;
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        saveProfile();
//    }


    @Override
    public void onBackPressed() {
        saveProfile();
    }

    public void getCurrentProfileData() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mCurrentMember = extras.getParcelable("memberResponse");
            }
        }
    }

    public void saveProfile() {
        List<String> newTags = Arrays.asList(mEditTags.getTags());
        String[] keepEmSeparated = mEditLocation.getText().toString().split(","); //TODO validate that comma
        if(mUpdatedImages != null){
            mCurrentMember.setPictures(mUpdatedImages);
        }
        mCurrentMember.setSchool(mEditSchool.getText().toString());
        mCurrentMember.setStanding(mEditStanding.getText().toString());
        mCurrentMember.setMajor(mEditMajor.getText().toString());
        mCurrentMember.setMinor(mEditMinor.getText().toString());
        mCurrentMember.setCity(keepEmSeparated[0].trim());
        mCurrentMember.setState(keepEmSeparated[1].trim());
        mCurrentMember.setAbout(mEditAbout.getText().toString());
        mCurrentMember.setInterests(newTags);

        ApiInterface ApiService = ApiClient.getClient(this).create(ApiInterface.class);
        Call<GenericResponse> call = ApiService.updateProfile(mCurrentMember);

        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                Boolean error = true;
                if (response.isSuccessful()) {
                    GenericResponse res = response.body();
                    if (!res.getError()) {
                        error = false;
                    }
                }
//                Bundle extras = new Bundle();
//                Intent returnIntent = new Intent();
//                extras.putParcelable("updatedProfile", updatedMember);
//                returnIntent.putExtras(extras);
//                returnIntent.putExtra("result", error);
//                setResult(Activity.RESULT_OK, returnIntent);
//                finish();


                Bundle extras = new Bundle();
                Intent returnIntent = new Intent();
                extras.putParcelable("updatedProfile", mCurrentMember);
                returnIntent.putExtras(extras);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });
    }

    public static void removeInterest(Context context, String name, String isInterest) {
        final Context c = context;
        ApiInterface ApiService = ApiClient.getClient(c).create(ApiInterface.class);
        Call<GenericResponse> call = ApiService.remove(new RemoveRequest(name, isInterest));
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                GenericResponse res = response.body();
                if (res.getError()) {
                    AppUtils.showPopMessage(c, res.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onImagePass(ArrayList<String> imgs) {
        Log.d("D", "DataSET changed!!! ");
        mUpdatedImages = imgs;
    }
}
