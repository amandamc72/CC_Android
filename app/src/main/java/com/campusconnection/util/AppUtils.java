package com.campusconnection.util;

import android.widget.EditText;

import java.util.ArrayList;
import android.text.TextUtils;

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


}
