package com.sarscene.triage.d4h.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.reconinstruments.os.connectivity.HUDConnectivityManager;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.StandardListItem;
import com.sarscene.triage.PhotoFileObserver;
import com.sarscene.triage.d4h.api.HUDManager;
import com.sarscene.triage.d4h.models.Casualty;
import com.sarscene.triage.d4h.models.CasualtyStatus;

public class TriageActivity extends SimpleListActivity {

    static final String TAG = TriageActivity.class.getName();
    HUDConnectivityManager hudConnectivityManager;
    private boolean mAlreadyStarted;
    private PhotoFileObserver mPhotoFileObserver;
    private Casualty casualty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String photoPath = this.getPhotoPath();

        Toast.makeText(this.getApplicationContext(), "Triage mode", Toast.LENGTH_SHORT).show();

        this.promptStatus();
        this.requestLocation();
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

    private String getPhotoPath() {
        Bundle extras = getIntent().getExtras();
        Intent intentIncoming = getIntent();
        if (extras != null) {
            return intentIncoming.getParcelableExtra(PhotoFileObserver.PHOTO_USER_FULL_PATH);
        } else {
            return null;
        }
    }

    private void promptStatus() {
        //Todo: replace with carousel or whatever is bigger
        setContents(
                new ListItem(CasualtyStatus.MINOR.name(), CasualtyStatus.MINOR),
                new ListItem(CasualtyStatus.DELAYED.name(), CasualtyStatus.DELAYED),
                new ListItem(CasualtyStatus.IMMEDIATE.name(), CasualtyStatus.IMMEDIATE),
                new ListItem(CasualtyStatus.DECEASED.name(), CasualtyStatus.DECEASED)
        );
    }

    /**
     * Requests the current location. Accuracy must be fine.
     * TODO: handle situations where fine accuracy cannot be obtained (ie inside buildings)
     */
    private void requestLocation() {
        LocationManager locManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //TODO: locManager.requestSingleUpdate(criteria, this);
    }

    public class ListItem extends StandardListItem {
        CasualtyStatus status;

        ListItem(String text, CasualtyStatus status) {
            super(text);
            this.status = status;
        }

        public void onClick(Context context) {
            //todo return status to parent
            Toast.makeText(context, "Triage mode is:" + this.status.name(), Toast.LENGTH_SHORT).show();
        }
    }
}
