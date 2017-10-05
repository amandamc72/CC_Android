package com.campusconnection;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements AddPicturesFragment.OnFragmentInteractionListener{

    private EditText mEditSchool;
    private EditText mEditMajor;
    private EditText mEditMinor;
    private EditText mEditLocation;
    private EditText mEditStanding;
    private EditText mEditAbout;
    private ProfileInterestTags mEditTags;
    private MemberResponse mCurrentMember;
    private int myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.editProfileToolbar);
        setSupportActionBar(toolbar);
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


        //Todo for testing
        List list = new ArrayList();

        HashMap<String, String> o = new HashMap<String, String>();
        o.put("relPath", "https://i.imgur.com/suiOWyN.jpg");
        HashMap<String, String> b = new HashMap<String, String>();
        b.put("relPath", "https://i.imgur.com/Nnz9Bwl.jpg");
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("relPath", "http://placehold.it/150x150");


        list.add(o);
        list.add(o);
        list.add(b);
        list.add(p);
        list.add(p);
        list.add(p);

        List w = new ArrayList();
        w.add("Cool");
        w.add("Rad");
        w.add("Nice");
        w.add("Yep");
        List courses = new ArrayList();
        mCurrentMember = new MemberResponse(false,"https://i.imgur.com/suiOWyN.jpg", list,"Amanda",
                                            "New Boston", "EMU", "ok", "CIS", "Comp",
                                            22, "fhysf gdsyfhjds f", w,courses);


        if(mCurrentMember != null) {
            if (findViewById(R.id.gridFragmentContainer) != null) {

                // so we don't end up with overlapping fragments.
                if (savedInstanceState != null) {
                    return;
                }
                AddPicturesFragment addPicturesFragment = new AddPicturesFragment().newInstance(mCurrentMember.formatAllPicsToArray());

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

            for (int i = 0; i < interestsPreClean.size(); i++){
                interests.add(interestsPreClean.get(i).toString().replace("[", "").replace("]", ""));
            }
            mEditTags.setTags(interests);
        } else {
            //TODO handel error;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveProfile();
    }


    @Override
    public void onBackPressed() {
        finish();
        saveProfile();
    }

    public void getCurrentProfileData() {
        if(getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mCurrentMember = extras.getParcelable("memberResponse");
                myId = extras.getInt("id");
            }
        }
    }

    public void saveProfile() {
        List<String> newTags = Arrays.asList(mEditTags.getTags());
        String [] keepEmSeparated = mEditLocation.getText().toString().split(","); //TODO validate that comma
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        MemberResponse updatedMember = new MemberResponse(mEditSchool.getText().toString(),
                mEditMajor.getText().toString(),
                mEditMinor.getText().toString(),
                keepEmSeparated[0].trim(),
                keepEmSeparated[1].trim(),
                mEditStanding.getText().toString(),
                mEditAbout.getText().toString(),
                newTags);
        Call<GenericResponse> call = apiService.updateProfile(myId, updatedMember);

        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
