package com.campusconnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.campusconnection.util.PreferencesUtil;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setTheme(R.style.AppTheme);

        PreferencesUtil prefs = new PreferencesUtil(this);
        Boolean loggedIn = prefs.getBooleanPreference(getString(R.string.isLoggedPref));
        Intent i;
        Log.d("D","are we logged in? " +  loggedIn);
        if(loggedIn) {
            i = new Intent(this, HomeActivity.class);
        } else {
            i = new Intent(this, LoginActivity.class);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        finish();
    }

}
