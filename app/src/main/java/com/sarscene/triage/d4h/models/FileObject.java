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
public class FileObject extends APIObject implements Serializable {
    static final String TAG = UserData.class.getName();
    private String rev;
    private String mimetype;
    private String size;
    private String channel;

    public FileObject(JSONObject json) {
        super(json);
        try {
            parse(json);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void parse(JSONObject json) throws JSONException {
        super.parse(json);
        rev = json.getString("_rev");
        mimetype = json.getString("mimetype");
        size = json.getString("size");
        channel = json.getString("channel");
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("_rev", rev);
        json.put("mimetype", mimetype);
        json.put("size", size);
        json.put("channel", channel);

        return json;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getSize() {
        return size;
    }

    public String getChannel() {
        return channel;
    }
}
