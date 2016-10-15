package com.sarscene.triage.d4h.models;

import android.location.Location;

import java.io.Serializable;

public class Casualty implements Serializable {
    static final String TAG = Casualty.class.getName();
    String picturePath;
    CasualtyStatus status;
    Location location;


    public void setStatus(CasualtyStatus status) {
        this.status = status;
    }


    public Casualty(String picturePath) {
        this.picturePath = picturePath;
    }
}
