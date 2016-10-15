package com.sarscene.triage.d4h.api;

import com.sarscene.triage.d4h.models.Casualty;

import java.util.ArrayList;

public class CasualtyManager {
    static final String TAG = CasualtyManager.class.getName();
    private ArrayList<Casualty> casualties = new ArrayList<Casualty>();

    CasualtyManager() {
    }

    boolean promptPicture() {
        boolean success = false;
        //TODO
        return success;
    }

    String onPictureTaken(String filepath) {
        //TODO
        return filepath;
    }

    boolean requestCurrentLocation() {

        return true;
    }


}
