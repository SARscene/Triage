package com.sarscene.triage.d4h.activities;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

public class TriageManager extends Activity {
    static final String TAG = TriageActivity.class.getName();

    public TriageManager() {

        //Intent intentIncoming = getIntent();
        //Bundle extras = intentIncoming.getExtras();

        //String path = extras.getString("path");
        //com.sarscene.triage.d4h.models.Channel channel = (com.sarscene.triage.d4h.models.Channel) extras.get("channel");

        buildPayload();
        //if (pushToLIVE(channel)) {
        Toast.makeText(getApplicationContext(), "Pushed.", Toast.LENGTH_SHORT).show();
        //} else {
        //    Toast.makeText(getApplicationContext(), "Push failed.", Toast.LENGTH_SHORT).show();
        //}
    }

    private void buildPayload() {
        //todo
    }

    private boolean pushToLIVE(com.sarscene.triage.d4h.models.Channel channel) {
        boolean success = false;
        try {
            com.sarscene.triage.d4h.api.TypeManager.log(channel, "This is a log from triagemanager");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return success;
    }
}
