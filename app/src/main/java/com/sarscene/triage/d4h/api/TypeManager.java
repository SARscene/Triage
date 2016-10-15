package com.sarscene.triage.d4h.api;

import android.util.Log;

import com.reconinstruments.os.connectivity.http.HUDHttpRequest;
import com.reconinstruments.os.connectivity.http.HUDHttpResponse;
import com.sarscene.triage.d4h.models.Channel;
import com.sarscene.triage.d4h.models.FileObject;
import com.sarscene.triage.d4h.models.LogObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TypeManager {
    static final String TAG = TypeManager.class.getName();
    static final String PUBLISH_PATH = "publish/";
    static final String INFO_ITEM_PATH = "info_item/";

    TypeManager() {
    }

    public static LogObject chat(Channel channel, String message) throws JSONException {
        return publish(channel, buildPublishBody(SubType.CHAT, message, null));
    }

    public static LogObject log(Channel channel, String message) throws JSONException {
        return publish(channel, buildPublishBody(SubType.LOG, message, null));
    }

    public static LogObject logWithAttachment(Channel channel, String message, FileObject file) throws JSONException {
        return publish(channel, buildPublishBody(SubType.LOG, message, file));
    }

    public static LogObject triage(Channel channel, String message) throws JSONException {
        return publishInfoItem(channel, buildInfoItem(SubType.TRIAGE, message));
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

    private static LogObject publishInfoItem(Channel channel, byte[] requestBody) {
        LogObject log = null;
        try {
            APIManager apiManagerSingleton = new APIManager();

            HUDHttpRequest request = new HUDHttpRequest(
                    HUDHttpRequest.RequestMethod.POST,
                    getInfoItemPath(channel)
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

    /**
     * "type":"info_item",
     * "subtype":"triage",
     * "action":"save",
     * "document":{
     * "name":"c,c,c,c",
     * }
     */
    private static byte[] buildInfoItem(SubType subType, String message) throws JSONException {
        JSONObject document = buildInfoItemDocumentBody(subType, message);

        JSONObject requestBody = new JSONObject();
        requestBody.put("type", "info_item");
        requestBody.put("subtype", SubType.TRIAGE.getSubType());
        requestBody.put("action", "save");
        requestBody.put("document", document);

        return requestBody.toString().getBytes();
    }

    private static JSONObject buildInfoItemDocumentBody(SubType subType, String name) throws JSONException {
        JSONObject document = new JSONObject();
        document.put("$doctype", "info_item");
        document.put("$subtype", subType.getSubType());
        document.put("name", name);

        return document;
    }

    /**
     * {
     * "type": "activity_log",
     * "action": "save",
     * "document": {
     * "logType": "post",
     * "logSubtype": "chat",
     * "message": "Hello, world."
     * }
     * }
     */
    private static byte[] buildPublishBody(SubType subType, String message, FileObject attachment) throws JSONException {
        JSONObject document = buildPublishDocumentBody(subType, message, attachment);

        JSONObject requestBody = new JSONObject();
        requestBody.put("type", "activity_log");
        requestBody.put("action", "save");
        requestBody.put("document", document);

        return requestBody.toString().getBytes();
    }

    private static JSONObject buildPublishDocumentBody(SubType subType, String message, FileObject attachment) throws JSONException {
        JSONObject document = new JSONObject();
        document.put("logType", "post");
        document.put("logSubtype", subType.getSubType());
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
        String value = APIManager.BASE_URL + Type.PUBLISH.getTypePath() + APIManager.DATABASE.LOG.getDbName() + "_" + channel.getRoomDbNameId();
        return value;
    }

    public static String getInfoItemPath(Channel channel) {
        String value = APIManager.BASE_URL + Type.PUBLISH.getTypePath() + APIManager.DATABASE.APPS.getDbName() + "_" + channel.getRoomDbNameId();
        return value;
    }

    public enum Type {
        PUBLISH {
            @Override
            public String getType() {
                return "publish";
            }

            @Override
            public String getTypePath() {
                return getType() + "/";
            }
        },
        INFO_ITEM {
            @Override
            public String getType() {
                return "info_item";
            }

            @Override
            public String getTypePath() {
                return getType() + "/";
            }
        };

        abstract public String getType();

        abstract public String getTypePath();
    }

    public enum SubType {
        LOG {
            @Override
            public String getSubType() {
                return "log";
            }
        },
        CHAT {
            @Override
            public String getSubType() {
                return "chat";
            }
        },
        TRIAGE {
            @Override
            public String getSubType() {
                return "triage";
            }
        };

        abstract public String getSubType();
    }
}

