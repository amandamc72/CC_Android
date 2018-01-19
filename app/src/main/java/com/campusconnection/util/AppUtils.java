package com.campusconnection.util;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

import android.text.TextUtils;
import android.support.v7.app.AlertDialog;

import com.campusconnection.R;

public class AppUtils {

    public static boolean isEmailValid(String email) {
        return email.contains("@") && email.endsWith(".edu");
    }

    public static class ValidInput {
        private Boolean isBlank;
        private Boolean isValidEmail;
        private EditText field;

        public ValidInput(boolean isBlank, boolean isValidEmail, EditText field){
            this.setIsBlank(isBlank);
            this.setIsValidEmail(isValidEmail);
            this.setField(field);
        }

        public Boolean getIsBlank() {
            return isBlank;
        }

        public void setIsBlank(Boolean blank) {
            isBlank = blank;
        }

        public Boolean getIsValidEmail() {
            return isValidEmail;
        }

        public void setIsValidEmail(Boolean valid) {
            isValidEmail = valid;
        }

        public EditText getField() {
            return field;
        }

        public void setField(EditText field) {
            this.field = field;
        }
    }


    public static ValidInput isInputsValid(ArrayList<EditText> inputs){

        for (int i = 0; i < inputs.size(); i++){
            EditText uiField = inputs.get(i);

            uiField.setError(null);
            String enteredText = uiField.getText().toString();

            if (TextUtils.isEmpty(enteredText)) {
                return new ValidInput(true, false, uiField);
            }
            if(uiField.getId() == R.id.loginEmailInput
                    || uiField.getId() == R.id.registerEmailInput){
                if(!isEmailValid(enteredText)){
                    return new ValidInput(false, true, uiField);
                }
            }
        }
        return new ValidInput(false, false, null);
    }

    public static void showPopMessage(Context context, String message) {
        AlertDialog.Builder alertResponse = new AlertDialog.Builder(context);
        alertResponse.setMessage(message);
        alertResponse.setPositiveButton(R.string.popup_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        alertResponse.show();
    }

    public static String dateToAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


}
