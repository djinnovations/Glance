package dj.example.main.utils;

import android.content.SharedPreferences;
import android.util.Log;

import dj.example.main.activities.MyApplication;

/**
 * Created by User on 08-07-2017.
 */

public class MyPrefManager {

    private static final String TAG = "MyPrefManager";
    private SharedPreferences pref;
    private static MyPrefManager mPrefManager;

    private SharedPreferences.Editor editor;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    private final String PREF_NAME = "finder_primary_pref_file";
    public static final String KEY_LOGIN_STATUS = "login_stat";
    public static final String KEY_LOGIN_MODE = "login_mode";

    public static final String MODE_NORMAL = "normal";
    public static final String MODE_SOCIAL = "social";

    private MyPrefManager() {
        pref = MyApplication.getInstance().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setIsLoginDone(String signInMode, boolean status){
        editor.putBoolean(KEY_LOGIN_STATUS, status);
        editor.putString(KEY_LOGIN_MODE, signInMode);
        editor.commit();
        Log.d(TAG, "setIsLoginDone - MyPrefManager: " + status);
    }

    public static MyPrefManager getInstance() {
        if (mPrefManager == null) {
            mPrefManager = new MyPrefManager();
        }
        return mPrefManager;
    }

    public boolean getLoginStatus(){
        boolean txt = pref.getBoolean(KEY_LOGIN_STATUS, false);
        Log.d(TAG, "getLoginStatus - MyPrefManager: " + txt);
        return txt;
    }

    public void clearLoginParams(){
        editor.putBoolean(KEY_LOGIN_STATUS, false);
        editor.commit();
        Log.d(TAG, "clearLoginParams - MyPrefManager: " + "cleared success");
    }
}
