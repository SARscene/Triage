package com.sarscene.triage.d4h.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * "user": {
 * "_id": "user~0ifzohxbb!ifzohxbb4wgynag",
 * "$doctype": "user",
 * "name": "John Doe",
 * "username": "jdoe",
 * "email": "john.doe@example.com"
 * }
 */
public class UserData extends APIObject implements Serializable {
    static final String TAG = UserData.class.getName();
    private String username;
    private String email;

    public UserData(JSONObject json) {
        super(json);
        try {
            parse(json);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void parse(JSONObject json) throws JSONException {
        super.parse(json);
        username = json.getString("username");
        email = json.getString("email");
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("email", email);

        return json;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
