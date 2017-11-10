package com.campusconnection.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtil {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public PreferencesUtil(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }

    public void setStringPreference(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringPreference(String key) {
        return prefs.getString(key, "");
    }

    public void setBooleanPreference(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBooleanPreference(String key) {
        return prefs.getBoolean(key, false);
    }

}
