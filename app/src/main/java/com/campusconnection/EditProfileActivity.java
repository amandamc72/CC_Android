package com.campusconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.campusconnection.model.GenericResponse;
import com.campusconnection.model.MemberResponse;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.views.ProfileInterestTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText mEditSchool;
    private EditText mEditMajor;
    private EditText mEditMinor;
    private EditText mEditLocation;
    private EditText mEditStanding;
    private EditText mEditAbout;
    private ProfileInterestTags mEditTags;
    private MemberResponse mCurrentMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.editProfileToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);

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


        mCurrentMember = getCurrentProfileData();

        if (findViewById(R.id.gridFragmentContainer) != null) {

            // so we don't end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

//            AddPicturesFragment addPicturesFragment = new AddPicturesFragment().newInstance(mCurrentMember.getAllPics());
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.memberViewFragmentContainer, addPicturesFragment).commit();
        }

        mEditSchool.setText(mCurrentMember.getSchool());
        mEditMajor.setText(mCurrentMember.getMajor());
        mEditMinor.setText(mCurrentMember.getMinor());
        mEditLocation.setText(mCurrentMember.getCity() + ", " + mCurrentMember.getState());
        mEditAbout.setText(mCurrentMember.getAbout());
        mEditTags.setTags(mCurrentMember.getInterests());
    }

    @Override
    public void onPause() {
        super.onPause();
        saveProfile();
    }


    @Override
    public void onBackPressed() {
        saveProfile();
    }

    public MemberResponse getCurrentProfileData() {
        if(getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                return (MemberResponse) extras.getParcelable("memberResponse");
            }
        }
        return null;
    }

    public void saveProfile() {
//        List<String> newTags = Arrays.asList(mEditTags.getTags());
//        String [] keepEmSeparated = mEditLocation.getText().toString().split(","); //TODO validate that comma
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        MemberResponse updatedMember = new MemberResponse(mEditSchool.getText().toString(),
//                mEditMajor.getText().toString(),
//                mEditMinor.getText().toString(),
//                keepEmSeparated[0].trim(),
//                keepEmSeparated[1].trim(),
//                mEditStanding.getText().toString(),
//                mEditAbout.getText().toString(),
//                newTags);
//        Call<GenericResponse> call = apiService.updateProfile(mCurrentMember.getId(), updatedMember);
//
//        call.enqueue(new Callback<GenericResponse>() {
//            @Override
//            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<GenericResponse> call, Throwable t) {
//
//            }
//        });
    }
}
