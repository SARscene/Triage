package com.sarscene.triage.d4h;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sarscene.triage.d4h.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class AppProfile {
    static final String TAG = AppProfile.class.getName();
    private static final String PREFS_NAME = AppProfile.class.getCanonicalName();
    private static final String KEY_USER_INFO = "user_info";
    private static SharedPreferences prefs;

    public AppProfile(Application instance) {
        prefs = instance.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        prefs.edit().clear().commit();
    }

    public User getUser() {
        String jsonUserInfo = prefs.getString(KEY_USER_INFO, null);
        if (jsonUserInfo == null) {
            // No saved login
            return null;
        }
        try {
            return new User(new JSONObject(jsonUserInfo));
        } catch (JSONException e) {
            Log.w(TAG, "Could not parse saved user info object: " + e.getMessage());
            Log.i(TAG, "Saved user info object: " + jsonUserInfo);
            return null;
        }
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = prefs.edit();
        if (AuthenticationManager.isLoggedIn() && user != null) {
            try {
                editor.putString(KEY_USER_INFO, user.getJSONObject().toString());
            } catch (Exception e) {
                Log.w(TAG, "Could not save login info: " + e.getMessage());
            }
        }
        editor.apply();
    }
}
