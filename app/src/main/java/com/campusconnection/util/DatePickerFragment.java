package com.campusconnection.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.campusconnection.R;
import java.util.Calendar;

import static com.campusconnection.SignUpActivity.mBirthday;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    //TODO improve!! Looks like shit!
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(),this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        month = month + 1;
        String formattedMonth = String.valueOf(month);
        String formattedDay = String.valueOf(day);
        if (month < 10)
            formattedMonth = "0" + month;
        if (day < 10)
            formattedDay = "0" + day;
        mBirthday.setText(formattedMonth + "/" +  formattedDay + "/" + year);
    }
}
