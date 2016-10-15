package com.sarscene.triage.d4h.api;

import android.util.Log;

import com.reconinstruments.os.connectivity.http.HUDHttpRequest;
import com.reconinstruments.os.connectivity.http.HUDHttpResponse;
import com.sarscene.triage.d4h.models.Channel;
import com.sarscene.triage.d4h.models.FileObject;
import com.sarscene.triage.d4h.models.MultiPartUploadRequest;
import com.sarscene.triage.util.SimpleMultipartEntity;

import org.json.JSONObject;

import java.io.FileInputStream;

public class FileManager {
    static final String TAG = FileManager.class.getName();
    static final String UPLOAD_PATH = "upload/";

    FileManager() {
    }

    public static FileObject upload(Channel channel, String filepath) {
        FileObject d4hfile = null;
        try {
            MultiPartUploadRequest multiPartUploadRequest = new MultiPartUploadRequest(filepath);
            String[] filepathParts = filepath.split("/");
            String filepathFilename = filepathParts[filepathParts.length - 1];
            SimpleMultipartEntity entity = new SimpleMultipartEntity();

            APIManager apiManagerSingleton = new APIManager();

            HUDHttpRequest request = new HUDHttpRequest(
                    HUDHttpRequest.RequestMethod.POST,
                    //getUploadPath(channel)
                    "http://postbin.link/NJL4aaXHb"
                    //"http://bin.mailgun.net/5a5a7bb5"
            );
//            requestHeaders = apiManagerSingleton.addHeader(requestHeaders, "Content-Type", "multipart/form-data; boundary=" + multiPartUploadRequest.getBoundary());
//            request.setBody(multiPartUploadRequest.getBody());
            request.setHeaders(apiManagerSingleton.addContentTypeHeader(request.getHeaders(), "multipart/form-data; boundary=" + entity.getBoundary()));
            entity.writeFirstBoundaryIfNeeds();
            java.io.File file = new java.io.File(filepath);
            FileInputStream fileInputStream = new FileInputStream(file);
            entity.addPart("filedata", filepathFilename, fileInputStream);
            entity.writeLastBoundaryIfNeeds();
            request.setBody(entity.getContent().toString().getBytes());

            HUDHttpResponse response = apiManagerSingleton.sendAuthenticatedRequest(request);
            if (response.hasBody()) {
                JSONObject jsonResponse = new JSONObject(response.getBodyString());
                d4hfile = new FileObject(jsonResponse);
            } else {
                Log.d(TAG, "Response has no body!");
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }

        return d4hfile;
    }

    public static String getUploadPath(Channel channel) {
        return APIManager.BASE_URL + UPLOAD_PATH + APIManager.DATABASE.LOG.getDbName() + "_" + channel.getRoomDbNameId();
    }
}
