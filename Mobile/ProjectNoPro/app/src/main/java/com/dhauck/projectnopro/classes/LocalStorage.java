package com.dhauck.projectnopro.classes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by david on 6/16/2016.
 */
public class LocalStorage {
    public static final String PREFS_NAME = "MyPrefsFile";

    public static void setAccessToken(String token, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(token);
    }
}
