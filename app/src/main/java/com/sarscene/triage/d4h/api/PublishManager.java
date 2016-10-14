package com.sarscene.triage.d4h.api;

import android.util.Log;

import com.sarscene.triage.d4h.models.Channel;
import com.sarscene.triage.d4h.models.File;
import com.sarscene.triage.d4h.models.LogObject;
import com.reconinstruments.os.connectivity.http.HUDHttpRequest;
import com.reconinstruments.os.connectivity.http.HUDHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PublishManager {
    static final String TAG = PublishManager.class.getName();
    static final String PUBLISH_PATH = "publish/";

    PublishManager() {
    }

    public enum PublishType {
        LOG {
            @Override
            public String getMessageType() {
                return "log";
            }
        },
        CHAT {
            @Override
            public String getMessageType() {
                return "chat";
            }
        };

        abstract public String getMessageType();
    }

    public static LogObject chat(Channel channel, String message) throws JSONException {
        return publish(channel, buildPublishBody(PublishType.CHAT, message, null));
    }

    public static LogObject log(Channel channel, String message) throws JSONException {
        return publish(channel, buildPublishBody(PublishType.LOG, message, null));
    }

    public static LogObject logWithAttachment(Channel channel, String message, File file) throws JSONException {
        return publish(channel, buildPublishBody(PublishType.LOG, message, file));
    }

    private static LogObject publish(Channel channel, byte[] requestBody) {
        LogObject log = null;
        try {
            APIManager apiManagerSingleton = new APIManager();

            HUDHttpRequest request = new HUDHttpRequest(
                    HUDHttpRequest.RequestMethod.POST,
                    getPublishPath(channel)
            );
            request.setBody(requestBody); //message
            request.setHeaders(apiManagerSingleton.addContentTypeHeader(request.getHeaders(), "application/json"));
            HUDHttpResponse response = apiManagerSingleton.sendAuthenticatedRequest(request);
            if (response.hasBody()) {
                JSONObject jsonResponse = new JSONObject(response.getBodyString());
                log = new LogObject(jsonResponse);
            } else {
                Log.d(TAG, "Response has no body!");
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }

        return log;
    }

    /** {
     * "type": "activity_log",
     * "action": "save",
     * "document": {
     *  "logType": "post",
     *  "logSubtype": "chat",
     *  "message": "Hello, world."
     *  }
     * }
     */
    private static byte[] buildPublishBody(PublishType publishType, String message, File attachment) throws JSONException {
        JSONObject document = buildDocumentBody(publishType, message, attachment);

        JSONObject requestBody = new JSONObject();
        requestBody.put("type", "activity_log");
        requestBody.put("action", "save");
        requestBody.put("document", document);

        return requestBody.toString().getBytes();
    }

    private static JSONObject buildDocumentBody(PublishType publishType, String message, File attachment) throws JSONException {
        JSONObject document = new JSONObject();
        document.put("logType", "post");
        document.put("logSubtype", publishType.getMessageType());
        document.put("message", message);

        if (null != attachment) {
//            "attachments": [{
//                "_id": "file~0ikfk7xli!ikfk7xlid6dqxgq",
//                        "_rev": "1-d5712c0ff9251ea3f77450dfc48b92c2",
//                        "name": "sample.csv",
//                        "mimetype": "text/csv",
//                        "size": 9,
//                        "channel": "events_d4h_r8moppzm7p"
//            }]

            JSONArray attachments = new JSONArray();
            attachments.put(attachment.getJSONObject());
        }

        return document;
    }

    public static String getPublishPath(Channel channel) {
        return APIManager.BASE_URL + PUBLISH_PATH + APIManager.DATABASE.LOG.getDbName() + "_" + channel.getRoomDbNameId();
    }
}
