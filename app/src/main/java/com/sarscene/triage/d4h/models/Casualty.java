package com.sarscene.triage.d4h.models;

import java.io.Serializable;

public class Casualty implements Serializable {
    static final String TAG = Casualty.class.getName();

    String picturePath;
    Double lat, lon;


    Casualty(String picturePath, Double lat, Double lon) {
        this.picturePath = picturePath;
        this.lat = lat;
        this.lon = lon;
    }

    boolean setStatus(CasualtyStatus newStatus) {

        return true;
    }
}
