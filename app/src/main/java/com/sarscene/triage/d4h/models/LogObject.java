package com.sarscene.triage.d4h.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 *  "user": {
 *   "_id": "user~0ifzohxbb!ifzohxbb4wgynag",
 *   "$doctype": "user",
 *   "name": "John Doe",
 *   "username": "jdoe",
 *   "email": "john.doe@example.com"
 *  }
 */
public class LogObject extends APIObject implements Serializable {
    static final String TAG = LogObject.class.getName();
    private String eventDate;
    private String logType;
    private String logSubtype;
    private String message;

    public LogObject(JSONObject json) {
        super(json);
        try {
            parse(json);
        } catch (JSONException e) {
            android.util.Log.d(TAG, e.getMessage());
        }
    }

    public void parse(JSONObject json) throws JSONException {
        super.parse(json);
        eventDate = json.getString("eventDate");
        logType = json.getString("logType");
        logSubtype = json.getString("logSubtype");
        message = json.getString("message");
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject json = super.getJSONObject();
        json.put("eventDate", eventDate);
        json.put("logType", logType);
        json.put("logSubtype", logSubtype);
        json.put("message", message);

        return json;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getMessage() {
        return message;
    }

    public String getLogType() {
        return logType;
    }

    public String getLogSubtype() {
        return logSubtype;
    }
}
