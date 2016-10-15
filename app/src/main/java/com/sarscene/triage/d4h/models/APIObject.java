package com.sarscene.triage.d4h.models;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * {
 * "_id": "channel_slot~001",
 * "$doctype": "channel_slot",
 * "$server_rev": "2-f6f19411cfbc278eb0cf427c5883163b",
 * "idx": 1,
 * "roomDbName": "room_d4h_r8moppzm7p"
 * }
 */
public class APIObject implements Serializable {
    static final String TAG = APIObject.class.getName();
    private String id;
    private String rev;
    private String doctype;
    private String name;

    public APIObject(JSONObject json) {
        try {
            parse(json);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public APIObject(Parcel parcel) {
    }

    public void parse(JSONObject json) throws JSONException {
        id = json.getString("_id");
        if (json.has("_rev")) {
            rev = json.getString("_rev");
        }
        doctype = json.getString("$doctype");

        if (json.has("name")) {
            name = json.getString("name");
        }
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("_id", id);
        if (null != rev) {
            json.put("_rev", rev);
        }
        json.put("$doctype", doctype);
        if (null != name) {
            json.put("name", name);
        }

        return json;
    }

    public String getId() {
        return id;
    }

    public String getDoctype() {
        return doctype;
    }

    public String getName() {
        return name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeString(this.getJSONObject().toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
