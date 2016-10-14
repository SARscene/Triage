package com.sarscene.triage.d4h.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * {
 *  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9iJ..."
 *  "user": {
 *   "_id": "user~0ifzohxbb!ifzohxbb4wgynag",
 *   "$doctype": "user",
 *   "name": "John Doe",
 *   "username": "jdoe",
 *   "email": "john.doe@example.com"
 *  }
 * }
 */
public class User implements Serializable {
    static final String TAG = User.class.getName();
    private String token;
    private UserData userData;

    public User() {

    }

    public User(JSONObject json) {
        try {
            parse(json);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void parse(JSONObject json) throws JSONException {
        token = json.getString("token");
        userData = new UserData(json.getJSONObject("user"));
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("token", token);

        JSONObject _userData = userData.getJSONObject();
        json.put("user", _userData);

        return json;
    }

    public String getToken() {
        return token;
    }

    public UserData getUserData() {
        return userData;
    }
}
