package com.dhauck.projectnopro.classes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by david on 6/16/2016.
 */
public class LocalStorage {
    public static final String PREFS_NAME = "MyPrefsFile";

    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
    }

    public static void setAccessToken(String token) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("tokentag", token);
    }

    public static String getAccessToken() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("tokentag", null);
    }
}
