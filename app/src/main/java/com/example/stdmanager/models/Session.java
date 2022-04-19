package com.example.stdmanager.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private final SharedPreferences prefs;

    public Session(Context context) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * set a session with its keyword and value
     *
     * @param key that keyword of session
     * @param value that value of session
     */
    public void set(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    /**
     *
     * @param key which is session's keyword
     * @return the value of session having @key
     */
    public String get(String key) {
        return prefs.getString(key,"");
    }
}