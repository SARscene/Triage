package com.sarscene.triage.d4h.api;

import com.sarscene.triage.d4h.models.Casualty;

import java.util.ArrayList;

public class CasualtyManager {
    static final String TAG = CasualtyManager.class.getName();
    private final CasualtyManager casualtyManager;
    private ArrayList<Casualty> casualties = new ArrayList<Casualty>();

    public CasualtyManager() {
        this.casualtyManager = new CasualtyManager();
    }

}
