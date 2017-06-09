package com.campusconnection.util;

public class AppUtils {

    public static boolean isEmailValid(String email) {
        return email.contains("@") && email.endsWith(".edu");
    }

    //TODO write common text input validation method
}
