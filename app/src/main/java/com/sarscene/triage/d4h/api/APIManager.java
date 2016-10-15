package com.sarscene.triage.d4h.api;

import android.util.Log;

import com.reconinstruments.os.connectivity.HUDConnectivityManager;
import com.reconinstruments.os.connectivity.http.HUDHttpRequest;
import com.reconinstruments.os.connectivity.http.HUDHttpResponse;
import com.sarscene.triage.d4h.AuthenticationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIManager {
    static final String TAG = APIManager.class.getName();
    static final String BASE_URL = "https://d4h.live/api/";
    private static HUDConnectivityManager hudManager = null;

    public APIManager() {
        hudManager = new HUDManager().getInstance();
    }

    public HUDHttpResponse sendAuthenticatedRequest(HUDHttpRequest request) {
        Log.d(TAG, "sendAuthenticatedRequest");
        try {
            request.setHeaders(addAuthorizationHeader(request.getHeaders()));

            String requestBody = new String(request.getBody());
            Log.d(TAG, requestBody);
            return hudManager.sendWebRequest(request);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return null;
        }
    }

    public HUDHttpResponse sendUnauthenticatedRequest(HUDHttpRequest request) {
        try {
            return hudManager.sendWebRequest(request);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param headers - the existing list of headers
     * @return headers - modified to add the Authorization header
     */
    private Map<String, List<String>> addAuthorizationHeader(Map<String, List<String>> headers) {
        headers = initHeaders(headers);

        List<String> authKeyValue = new ArrayList<String>();
        authKeyValue.add("Bearer " + AuthenticationManager.getUser().getToken());
        headers.put("Authorization", authKeyValue);

        return headers;
    }

    /**
     * @param headers          - the existing list of headers
     * @param contentTypeValue - D4H API currently supports application/json and multipart/form-data
     * @return headers - modified to add the Content-Type header
     */
    public Map<String, List<String>> addContentTypeHeader(Map<String, List<String>> headers, String contentTypeValue) {
        headers = initHeaders(headers);

        List<String> keyValue = new ArrayList<String>();
        keyValue.add(contentTypeValue);
        headers.put("Content-Type", keyValue);

        return headers;
    }

    public Map<String, List<String>> addHeader(Map<String, List<String>> headers, String key, String value) {
        headers = initHeaders(headers);

        List<String> keyValue = new ArrayList<String>();
        keyValue.add(value);
        headers.put(key, keyValue);

        return headers;
    }

    public Map<String, List<String>> initHeaders(Map<String, List<String>> headers) {
        if (null == headers) {
            headers = new HashMap<String, List<String>>();
        }

        return headers;
    }

    public enum DATABASE {
        APPS {
            @Override
            public String getDbName() {

                return "room_" + this.organisation_name;
            }

        },
        LOG {
            @Override
            public String getDbName() {

                return "events_" + this.organisation_name;
            }
        },
        ORG {
            @Override
            public String getDbName() {

                return "org_" + this.organisation_name;
            }
        };

        protected String organisation_name;

        DATABASE() {
            this.organisation_name = "usar";//Resources.getSystem().getString(d4hlive_organisation);
        }

        abstract public String getDbName();
    }
}
