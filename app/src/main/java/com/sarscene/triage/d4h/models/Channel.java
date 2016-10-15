package com.sarscene.triage.d4h.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Channel extends APIObject implements Parcelable, Serializable {
    @SuppressWarnings({"rawtypes"})
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Channel createFromParcel(Parcel parcel) {
            return new Channel(parcel);
        }

        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
    private String opTime;
    private String serverRev;
    private String serverPersisted;
    private String clientSeq;
    private String idx;
    private String roomDbName;
    private ChannelSettings channelSettings;

    public Channel(JSONObject json) {
        super(json);
        try {
            parse(json);
        } catch (JSONException e) {
            android.util.Log.d(TAG, e.getMessage());
        }
    }

    public Channel(Parcel parcel) {
        super(parcel);
        try {
            JSONObject json = new JSONObject(parcel.readString());
            parse(json);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void parse(JSONObject json) throws JSONException {
        super.parse(json);
        opTime = json.getString("$op_time");
        serverRev = json.getString("$server_rev");
        serverPersisted = json.getString("$server_persisted");
        if (json.has("$client_seq")) {
            clientSeq = json.getString("$client_seq");
        }
        idx = json.getString("idx");
        if (json.has("roomDbName")) {
            roomDbName = json.getString("roomDbName");
        }
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject json = super.getJSONObject();
        json.put("$op_time", opTime);
        json.put("$server_rev", serverRev);
        json.put("$server_persisted", serverPersisted);
        if (null != clientSeq) {
            json.put("$client_seq", clientSeq);
        }
        json.put("idx", idx);
        if (null != roomDbName) {
            json.put("roomDbName", roomDbName);
        }

        return json;
    }

    public String getOpTime() {
        return opTime;
    }

    public String getServerRev() {
        return serverRev;
    }

    public String getServerPersisted() {
        return serverPersisted;
    }

    public String getClientSeq() {
        return clientSeq;
    }

    public String getIdx() {
        return idx;
    }

    public String getRoomDbName() {
        return roomDbName;
    }

    public String getRoomDbNameId() {
        String[] roomDbNameParts = roomDbName.split("_");

        return roomDbNameParts[roomDbNameParts.length - 1];
    }

    public ChannelSettings getChannelSettings() {
        return channelSettings;
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
}
