package com.sarscene.triage.d4h.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.reconinstruments.os.connectivity.HUDConnectivityManager;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.sarscene.triage.PhotoFileObserver;
import com.sarscene.triage.d4h.api.HUDManager;

public class TriageActivity extends SimpleListActivity {

    static final String TAG = TriageActivity.class.getName();
    HUDConnectivityManager hudConnectivityManager;
    private boolean mAlreadyStarted;
    private PhotoFileObserver mPhotoFileObserver;
    private String photoFullPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        Intent intentIncoming = getIntent();
        if (extras != null) {
            this.photoFullPath = intentIncoming.getParcelableExtra(PhotoFileObserver.PHOTO_USER_FULL_PATH);
        }

        hudConnectivityManager = HUDManager.getInstance();
        Log.e(TAG, "Prompting victim status...");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // no user = launch again and login
        Log.e(TAG, "onResume...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
