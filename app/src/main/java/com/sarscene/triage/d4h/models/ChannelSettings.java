package com.sarscene.triage.d4h.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ChannelSettings extends APIObject implements Parcelable, Serializable {
    private ChannelSettingsModules modules;

    public ChannelSettings(JSONObject json) {
        super(json);
        try {
            parse(json);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public ChannelSettings(Parcel parcel) {
        super(parcel);
        try {
            JSONObject json = new JSONObject(parcel.readString());
            parse(json);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * {
     *  "_id": "room_settings~",
     *  "_rev": "3-e0935563570ea71434ac144401a753a0",
     *  "$doctype": "room_settings",
     *  "name": "Oil Spill - 2016/08/11",
     *  "modules": {
     *   "library": true,
     *   "h_info_manager~roles": false,
     *   "task_tpl~test": true,
     *   "personnel": true
     *  }
     * }
     *
     * @param json - server api response
     * @throws JSONException
     */
    public void parse(JSONObject json) throws JSONException {
        super.parse(json);
        modules = new ChannelSettingsModules(json.getJSONObject("modules"));
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject json = super.getJSONObject();
        json.put("modules", modules.getJSONObject());

        return json;
    }

    @Override
    public int describeContents() {
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
        public ChannelSettings createFromParcel(Parcel parcel) {
            return new ChannelSettings(parcel);
        }

        public ChannelSettings[] newArray(int size)
        {
            return new ChannelSettings[size];
        }
    };
}
