package com.sarscene.triage;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationUpdater {
    static final String TAG = LocationHandler.class.getName();

    private class LocationHandler implements LocationListener {

        private final String TAG = LocationHandler.class.getSimpleName();
        private Context mContext;

        private LocationManager mLocationManager;

        private static final int MINIMUM_TIME_BETWEEN_UPDATES = 300000; // 5 minutes in millis
        private static final int MINIMUM_DISTANCE_BETWEEN_UPDATES = 100; //meters

        public LocationHandler(Context context) {
            this.mContext = context;
            this.mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }

        public void registerForUpdates() {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_BETWEEN_UPDATES, this);
        }

        public void unregisterForUpdates() {
            mLocationManager.removeUpdates(this);
        }

        @Override
        public void onLocationChanged(Location loc) {
            Log.v(TAG, "Got a location update!");
            Log.v(TAG, "latitude: " + loc.getLatitude());
            Log.v(TAG, "longitude: " + loc.getLongitude());
            Log.v(TAG, "timestamp: " + System.currentTimeMillis() / 1000);
            updatePosition(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }

    private void updatePosition(Location location) {
        Log.d(TAG, "Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
    }
}
