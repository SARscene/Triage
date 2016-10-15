package com.sarscene.triage.d4h.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.reconinstruments.os.connectivity.HUDConnectivityManager;
import com.reconinstruments.ui.carousel.CarouselActivity;
import com.reconinstruments.ui.carousel.CarouselItem;
import com.sarscene.triage.R;
import com.sarscene.triage.d4h.models.CasualtyStatus;
import com.sarscene.triage.d4h.models.Channel;

public class TriageActivity extends CarouselActivity {

    static final String TAG = TriageActivity.class.getName();
    public static Channel channel;
    public String photoPath;
    HUDConnectivityManager hudConnectivityManager;
    private boolean mAlreadyStarted;
    private Uri data;

    public static void pushToDashboard(Context context, CarouselItem item) {
        Log.e(TAG, "pushtodashboard");
        //TODO: TypeManager.log(channel, "This is a log");
        //todo return status to parent so parent can save and restart at capture screen
        Toast.makeText(context, "Sending status \"" + item.toString() + "\" to ICS...", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Success.", Toast.LENGTH_SHORT).show();
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

    public void fetchChannel() {
        Bundle extras = getIntent().getExtras();
        Intent intentIncoming = getIntent();
        if (extras != null) {
            channel = intentIncoming.getParcelableExtra("channel");
        }
    }

    private void fetchPhotoPath() {

        Bundle extras = getIntent().getExtras();
        Intent intentIncoming = getIntent();
        if (extras != null) {
            this.data = intentIncoming.getParcelableExtra("data");
        }

        /*
        Bundle extras = getIntent().getExtras();
        Intent intentIncoming = getIntent();
        if (extras != null) {
            //TODO: return actual file path
            // return intentIncoming.getParcelableExtra(PhotoFileObserver.PHOTO_USER_FULL_PATH);
            return "/sdcard/dcim/camera/2016-10-15 02:58 IMG_20161015_085829.jpg";
        } else {
            return null;
        }
        */
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchPhotoPath();
        fetchChannel();
        promptStatus();
    }

    private void promptStatus() {
        Log.e(TAG, "Prompting victim status...");
        setContentView(R.layout.carousel_host_stat);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Casualty Status");
        getCarousel().setContents(
                new ListItem(CasualtyStatus.MINOR),
                new ListItem(CasualtyStatus.DELAYED),
                new ListItem(CasualtyStatus.IMMEDIATE),
                new ListItem(CasualtyStatus.DECEASED)
        );
    }

    /**
     * Requests the current location. Accuracy must be fine.
     * TODO: handle situations where fine accuracy cannot be obtained (ie inside buildings)
     */
    private void requestLocation() {
        Log.e(TAG, "Requesting current location...");
        LocationManager locManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //TODO: locManager.requestSingleUpdate(criteria, this);
    }

    static class ListItem extends CarouselItem {
        String label;
        CasualtyStatus status;
        TextView valueText;

        public ListItem(CasualtyStatus status) {
            this.status = status;
            this.label = this.status.name();
        }

        @Override
        public int getLayoutId() {
            return R.layout.carousel_item_stat;
        }
        @Override
        public void updateView(View view) {
            this.valueText = (TextView) view.findViewById(R.id.label);
            this.valueText.setText(this.status.name());
            view.setBackgroundColor(this.status.getCardColor());
            this.valueText.setTextColor(this.status.getTextColor());
            this.valueText.setVisibility(View.VISIBLE);
        }

        @Override
        public void updateViewForPosition(View view, POSITION position) {
            this.valueText.setVisibility(View.VISIBLE);
        }

        public void onClick(Context context) {
            pushToDashboard(context, this);
        }

        public String toString() {
            return this.status.name();
        }
    }

}
