package com.campusconnection;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import com.campusconnection.model.MemberListResponse;
import com.campusconnection.model.SearchRequest;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeFragment.OnFragmentInteractionListener, RecyclerViewFragment.OnFragmentInteractionListener {


    private RecyclerViewFragment mRecyclerViewFragment;
    private SwipeFragment mSwipeFragment;
    private MemberListResponse mMembersResponse;

    private boolean on;
    private boolean off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getMembersListAdapter(savedInstanceState);

        on = true;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (on) {
                    Log.d("D","ON");
                    off = true;
                    on = false;
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.memberViewFragmentContainer, mSwipeFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (off){
                    Log.d("D","OFF");
                    on = true;
                    off = false;
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.memberViewFragmentContainer, mRecyclerViewFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);

        CircleImageView myInfoPicView = (CircleImageView) headerview.findViewById(R.id.my_info_nav_header_pic);

        myInfoPicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                drawerIntentNav(intent);
            }
        });
    }

    public boolean isSearchList() {
      if (getIntent() != null) {
          Bundle extras = getIntent().getExtras();
          return extras != null && extras.getBoolean("isSearch");
      }
      return false;
    }

    public SearchRequest getUserSearch() {
        Bundle extras = getIntent().getExtras();
        return (SearchRequest) extras.getParcelable("searchRequest");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_info) {
            Intent intent = new Intent(this, ProfileActivity.class);
            drawerIntentNav(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            drawerIntentNav(intent);
        } else if (id == R.id.nav_social) {
            //TODO
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            drawerIntentNav(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void drawerIntentNav(Intent intent) {
        intent.putExtra("id", 1001); //todo My id for testing need to get from phone storage
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    public void getMembersListAdapter(Bundle savedInstanceState) {
        int offset = 0; //TODO so something with this
        final Bundle state = savedInstanceState;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MemberListResponse> call;

        if(isSearchList()) {
            call = apiService.createSearch(getUserSearch());
        } else {
            call = apiService.getMembers(offset);
        }

        call.enqueue(new Callback<MemberListResponse>() {
            @Override
            public void onResponse(Call<MemberListResponse> call, Response<MemberListResponse> response) {
                mMembersResponse = response.body(); //// TODO: handel null case
                if (findViewById(R.id.memberViewFragmentContainer) != null) {

                    // so we don't end up with overlapping fragments.
                    if (state != null) {
                        return;
                    }

                    mRecyclerViewFragment = new RecyclerViewFragment().newInstance(mMembersResponse);
                    mSwipeFragment = new SwipeFragment().newInstance(mMembersResponse);

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.memberViewFragmentContainer, mRecyclerViewFragment).commit();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
