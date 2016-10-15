package com.sarscene.triage.d4h.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.reconinstruments.os.connectivity.HUDConnectivityManager;
import com.reconinstruments.ui.R;
import com.reconinstruments.ui.carousel.CarouselActivity;
import com.reconinstruments.ui.carousel.CarouselItem;
import com.reconinstruments.ui.carousel.CarouselViewPager;
import com.sarscene.triage.PhotoFileObserver;
import com.sarscene.triage.d4h.api.HUDManager;
import com.sarscene.triage.d4h.models.Casualty;
import com.sarscene.triage.d4h.models.CasualtyStatus;

public class TriageActivity extends CarouselActivity {

    static final String TAG = TriageActivity.class.getName();
    HUDConnectivityManager hudConnectivityManager;
    private boolean mAlreadyStarted;
    private Uri data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        Intent intentIncoming = getIntent();
        if (extras != null) {
            this.data = intentIncoming.getParcelableExtra("data");
        }

        hudConnectivityManager = HUDManager.getInstance();
        Toast.makeText(this.getApplicationContext(), "Triage mode", Toast.LENGTH_SHORT).show();
        this.requestLocation();
        setContentView(R.layout.carousel_host);
        this.promptStatus();
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
            //TODO: return actual file path
            // return intentIncoming.getParcelableExtra(PhotoFileObserver.PHOTO_USER_FULL_PATH);
            return "/sdcard/dcim/camera/2016-10-15 02:58 IMG_20161015_085829.jpg";
        } else {
            return null;
        }
    }

    private void promptStatus() {
        Log.e(TAG, "Prompting victim status...");
        //Todo: replace with carousel or whatever is bigger
        /*CarouselViewPager title = (CarouselViewPager)findViewById(R.id.title);
        title.set*/
        getCarousel().setContents(
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
        Log.e(TAG, "Requesting current location...");
        LocationManager locManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //TODO: locManager.requestSingleUpdate(criteria, this);
    }

    public class ListItem extends CarouselItem {
        CasualtyStatus status;
        CarouselViewPager carouselViewPager;
        TextView label;

        ListItem(String text, CasualtyStatus status) {
            super();
            this.status = status;
            this.carouselViewPager = getCarousel();
            this.label = new TextView(getApplicationContext());
        }

        public void onClick(Context context) {
            //todo return status to parent
            Toast.makeText(context, "Triage mode is:" + this.status.name(), Toast.LENGTH_SHORT).show();
        }

        public int getLayoutId() {
            return R.layout.carousel_item_title;
        }

        @Override
        public void updateView(View view) {
            Toast.makeText(getApplicationContext(), "Updateview TriageActivity", Toast.LENGTH_SHORT).show();
            view.setBackgroundColor(this.status.getCardColor());
            view.setContentDescription(this.status.name());
            this.label.setText(this.status.name());
            this.label.setBackgroundColor(this.status.getCardColor());
            this.label.setTextColor(this.status.getTextColor());
        }

        @Override
        public void updateViewForPosition(View view, POSITION position) {
            if (position == POSITION.CENTER)
                label.setVisibility(View.VISIBLE);
            else
                label.setVisibility(View.GONE);
        }
    }
}
