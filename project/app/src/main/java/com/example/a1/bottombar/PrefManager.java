package com.example.a1.bottombar;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "punctuationApp";

    private static final String APP_MODE = "mode";
    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTime";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setPref(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setPref(int mode, boolean isFirstTime) {
        editor.putInt(APP_MODE, mode);
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setPref(int mode) {
        editor.putInt(APP_MODE, mode);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
    public byte getCurrentMode() {return (byte) pref.getInt(APP_MODE, 0);}

}