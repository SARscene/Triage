package com.sarscene.triage.d4h.api;

import android.util.Log;

import com.sarscene.triage.d4h.AuthenticationManager;
import com.reconinstruments.os.connectivity.HUDConnectivityManager;
import com.reconinstruments.os.connectivity.IHUDConnectivity;
import com.reconinstruments.os.connectivity.http.HUDHttpRequest;
import com.reconinstruments.os.connectivity.http.HUDHttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIManager {
    static final String TAG = APIManager.class.getName();
    private static HUDConnectivityManager hudManager = null;
    static final String BASE_URL = "https://www.d4h.live/api/";
    static final String ORGANIZATION_NAME = "reconinstruments";

    public enum DATABASE {
        APPS {
            @Override
            public String getDbName() {
                return "room_" + ORGANIZATION_NAME;
            }
        },
        LOG {
            @Override
            public String getDbName() {
                return "events_" + ORGANIZATION_NAME;
            }
        },
        ORG {
            @Override
            public String getDbName() {
                return "org_" + ORGANIZATION_NAME;
            }
        };

        abstract public String getDbName();
    }

    public APIManager() {
        hudManager = new HUDManager().getInstance();
    }

    public HUDHttpResponse sendAuthenticatedRequest(HUDHttpRequest request) {
        Log.d(TAG, "sendAuthenticatedRequest");
        try {
            request.setHeaders(addAuthorizationHeader(request.getHeaders()));

            String requestBody =  new String(request.getBody());
            Log.d(TAG, requestBody);
            return hudManager.sendWebRequest(request);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return null;
        }
    }

    public HUDHttpResponse sendUnauthenticatedRequest(HUDHttpRequest request) {
        HUDHttpResponse response = null;
        try {
            boolean local = hudManager.isHUDConnected();
            boolean local_again = hudManager.hasRemoteWeb();
            IHUDConnectivity.ConnectionState local_again_again = hudManager.getConnectionState();
            boolean hasWeb = hudManager.hasWebConnection();

            response = hudManager.sendWebRequest(request);
        } catch (Exception e) {
            Log.d(TAG, "THINGS!");
        }
        return response;
    }

    /**
     * @param headers - the existing list of headers
     * @return headers - modified to add the Authorization header
     */
    private Map<String, List<String>> addAuthorizationHeader(Map<String, List<String>> headers) {
        headers = initHeaders(headers);

        List<String> authKeyValue = new ArrayList<>();
        authKeyValue.add("Bearer " + AuthenticationManager.getUser().getToken());
        headers.put("Authorization", authKeyValue);

        return headers;
    }

    /**
     * @param headers - the existing list of headers
     * @param contentTypeValue - D4H API currently supports application/json and multipart/form-data
     * @return headers - modified to add the Content-Type header
     */
    public Map<String, List<String>> addContentTypeHeader(Map<String, List<String>> headers, String contentTypeValue) {
        headers = initHeaders(headers);

        List<String> keyValue = new ArrayList<>();
        keyValue.add(contentTypeValue);
        headers.put("Content-Type", keyValue);

        return headers;
    }

    public Map<String, List<String>> addHeader(Map<String, List<String>> headers, String key, String value) {
        headers = initHeaders(headers);

        List<String> keyValue = new ArrayList<>();
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
}
