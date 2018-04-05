package com.campusconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.campusconnection.fragments.AddPicturesFragment;

import java.util.ArrayList;

public class SignUpPhotosActivity extends AppCompatActivity implements AddPicturesFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_photos);


        if (findViewById(R.id.addPhotosContainer) != null) {

            // so we don't end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            AddPicturesFragment addPicturesFragment = new AddPicturesFragment().newInstance(new ArrayList<String>());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.addPhotosContainer, addPicturesFragment).commit();
        }
    }

    @Override
    public void onImagePass(ArrayList<String> imgs) {
    }
}
