package com.sarscene.triage.d4h.api;

import android.util.Log;

import com.sarscene.triage.d4h.models.Channel;
import com.sarscene.triage.d4h.models.ChannelSettings;
import com.reconinstruments.os.connectivity.http.HUDHttpRequest;
import com.reconinstruments.os.connectivity.http.HUDHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChannelManager {
    static final String TAG = TypeManager.class.getName();
    static final String CHANNEL_PATH = "request/";

    ChannelManager() {
    }

    public static ArrayList<Channel> listChannels() {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        try {
            APIManager apiManagerSingleton = new APIManager();

            HUDHttpRequest request = new HUDHttpRequest(
                    HUDHttpRequest.RequestMethod.POST,
                    getListChannelPath()
            );
            request.setBody(getListChannelRequestBody()); //message

            request.setHeaders(apiManagerSingleton.addContentTypeHeader(request.getHeaders(), "application/json"));
            HUDHttpResponse response = apiManagerSingleton.sendAuthenticatedRequest(request);
            if (response.hasBody()) {
                // obtain the response
                JSONObject jsonResponse = new JSONObject(response.getBodyString());
                // get the array
                JSONArray jsonChannels = jsonResponse.optJSONArray("documents");


                // iterate over the array and retrieve single person instances
                for(int i = 0; i < jsonChannels.length(); i++){
                    JSONObject jsonChannel = jsonChannels.getJSONObject(i);

                    Channel channel = new Channel(jsonChannel);
                    channels.add(channel);
                }
            } else {
                Log.d(TAG, "Response has no body!");
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }

        return channels;
    }

    public static ChannelSettings getChannelSettings() {
        ChannelSettings channelSettings = null;
        try {
            APIManager apiManagerSingleton = new APIManager();

            HUDHttpRequest request = new HUDHttpRequest(
                    HUDHttpRequest.RequestMethod.POST,
                    getListChannelPath()
            );
            request.setBody(getListChannelRequestBody()); //message
            request.setHeaders(apiManagerSingleton.addContentTypeHeader(request.getHeaders(), "application/json"));
            HUDHttpResponse response = apiManagerSingleton.sendAuthenticatedRequest(request);
            if (response.hasBody()) {
                // obtain the response
                JSONObject jsonResponse = new JSONObject(response.getBodyString());
                channelSettings = new ChannelSettings(jsonResponse);
            } else {
                Log.d(TAG, "Response has no body!");
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }

        return channelSettings;
    }

    public static String getListChannelPath() {
        return APIManager.BASE_URL + CHANNEL_PATH + APIManager.DATABASE.ORG.getDbName();
    }

    public static String getChannelPath(Channel channel) {
        return APIManager.BASE_URL + CHANNEL_PATH + channel.getRoomDbName();
    }

    private static byte[] getListChannelRequestBody() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("type", "channel_slot");
        body.put("action", "list");

        return body.toString().getBytes();
    }

    private static byte[] getChannelRequestBody() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("type", "room_settings");
        body.put("action", "get");

        return body.toString().getBytes();
    }
}
