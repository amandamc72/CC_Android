package com.campusconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.campusconnection.model.SearchRequest;
import org.florescu.android.rangeseekbar.RangeSeekBar;



public class SearchActivity extends AppCompatActivity {

    private EditText mSearchSchool;
    private EditText mSearchMajor;
    private EditText mSearchMinor;
    private RangeSeekBar mSearchAgeRange;
    private RadioGroup mSearchGender;
    private String mSearchGenderValue;
    private EditText mSearchCity;
    private Spinner mSearchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        mSearchSchool = (EditText) findViewById(R.id.searchSchoolInput);
        mSearchMajor = (EditText) findViewById(R.id.searchMajorInput);
        mSearchMinor = (EditText) findViewById(R.id.searchMinorInput);
        mSearchAgeRange = (RangeSeekBar) findViewById(R.id.searchAgeInput);
        mSearchGender = (RadioGroup) findViewById(R.id.searchGenderRadioGroup);
        mSearchCity = (EditText) findViewById(R.id.searchCityInput);
        mSearchState = (Spinner) findViewById(R.id.searchStateInput);
        Button mSearchBtn = (Button) findViewById(R.id.searchBtn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.searchToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
        });


        mSearchAgeRange.setSelectedMinValue(18);
        mSearchAgeRange.setSelectedMaxValue(55);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        mSearchGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId){
                    case R.id.searchAnyGenderBtn:
                        mSearchGenderValue = "";
                        break;
                    case R.id.searchMaleGenderBtn:
                        mSearchGenderValue = "male";
                        break;
                    case R.id.searchFemaleGenderBtn:
                        mSearchGenderValue = "female";
                        break;
                    default:
                        mSearchGenderValue = "";
                        break;
                }
            }
        });

        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchState.setAdapter(stateAdapter);
    }


    public void search () {
        String school = mSearchSchool.getText().toString();
        String major = mSearchMajor.getText().toString();
        String minor = mSearchMinor.getText().toString();
        int ageLow = (int) mSearchAgeRange.getSelectedMinValue();
        int ageHigh = (int) mSearchAgeRange.getSelectedMaxValue();
        String city = mSearchCity.getText().toString();
        String state = mSearchState.getSelectedItem().toString();

        Intent intent = new Intent(this, HomeActivity.class);
        Bundle extras = new Bundle();
        extras.putBoolean("isSearch", true);
        extras.putParcelable("searchRequest", new SearchRequest(0, school, major, minor, ageLow, ageHigh, mSearchGenderValue, city, state));
        intent.putExtras(extras);
        startActivity(intent);
    }
}
