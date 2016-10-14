package com.sarscene.triage.d4h.api;

import android.util.Log;

import com.sarscene.triage.d4h.models.User;
import com.reconinstruments.os.connectivity.http.HUDHttpRequest;
import com.reconinstruments.os.connectivity.http.HUDHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class AuthenticationManager {
    static final String TAG = AuthenticationManager.class.getName();
    static final String AUTH_PATH = "authenticate";

    AuthenticationManager() {
    }

    public static Boolean login(String username, String password) {
        Boolean responseState = false;
        try {
            APIManager apiManager = new APIManager();

            HUDHttpRequest request = new HUDHttpRequest(
                HUDHttpRequest.RequestMethod.POST,
                getAuthenticateURL()
            );
            request.setBody(buildLoginRequestBody(username, password));

            Map<String, List<String>> headers = request.getHeaders();
            headers = apiManager.addContentTypeHeader(headers, "application/json");
            request.setHeaders(headers);
            HUDHttpResponse response = apiManager.sendUnauthenticatedRequest(request);
            if (200 == response.getResponseCode() && response.hasBody()) {
                JSONObject jsonResponse = new JSONObject(response.getBodyString());
                com.sarscene.triage.d4h.AuthenticationManager.setUser(new User(jsonResponse));
                responseState = true;
            } else {
                Log.e(TAG, "Response has no body!");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return responseState;
    }

    public static byte[] buildLoginRequestBody(String username, String password)
            throws JSONException {
        JSONObject requestBody = new JSONObject();

        requestBody.put("username", username);
        requestBody.put("password", password);

        return requestBody.toString().getBytes();
    }

    public static String getAuthenticateURL() {
        return APIManager.BASE_URL + AUTH_PATH;
    }
}
