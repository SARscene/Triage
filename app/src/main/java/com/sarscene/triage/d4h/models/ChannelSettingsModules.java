package com.sarscene.triage.d4h.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ChannelSettingsModules extends APIObject implements Parcelable, Serializable {
    private Boolean library;
    private Boolean hasInfoManagerRoles;
    private Boolean taskTemplate;
    private Boolean personnel;

    public ChannelSettingsModules(JSONObject json) {
        super(json);
        try {
            parse(json);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public ChannelSettingsModules(Parcel parcel) {
        super(parcel);
        try {
            JSONObject json = new JSONObject(parcel.readString());
            parse(json);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * "modules": {
     *   "library": true,
     *   "h_info_manager~roles": false,
     *   "task_tpl~test": true,
     *   "personnel": true
     * }
     *
     * @param json
     * @throws JSONException
     */
    public void parse(JSONObject json) throws JSONException {
        super.parse(json);
        if (json.has("library")) {
            library = json.getBoolean("library");
        }
        if (json.has("h_info_manager~roles")) {
            hasInfoManagerRoles = json.getBoolean("h_info_manager~roles");
        }
        if (json.has("task_tpl~test")) {
            taskTemplate = json.getBoolean("task_tpl~test");
        }
        if (json.has("personnel")) {
            personnel = json.getBoolean("personnel");
        }
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject json = super.getJSONObject();
        json.put("library", library);
        json.put("h_info_manager~roles", hasInfoManagerRoles);
        json.put("task_tpl~test", taskTemplate);
        json.put("personnel", personnel);

        return json;
    }

    public Boolean getLibrary() {
        return library;
    }

    public Boolean getHasInfoManagerRoles() {
        return hasInfoManagerRoles;
    }

    public Boolean getTaskTemplate() {
        return taskTemplate;
    }

    public Boolean getPersonnel() {
        return personnel;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeString(this.getJSONObject().toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @SuppressWarnings({"rawtypes"})
    public static final Creator CREATOR = new Creator() {
        public ChannelSettingsModules createFromParcel(Parcel parcel) {
            return new ChannelSettingsModules(parcel);
        }

        public ChannelSettingsModules[] newArray(int size)
        {
            return new ChannelSettingsModules[size];
        }
    };
}
