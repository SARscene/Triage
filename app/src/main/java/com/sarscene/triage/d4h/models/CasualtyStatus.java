package com.sarscene.triage.d4h.models;

import java.util.HashMap;

public class CasualtyStatus {

    HashMap<String, Boolean> decisionFlow;

    public enum STATUS {
        MINOR, DELAYED, IMMEDIATE, DECEASED;
    }
}
