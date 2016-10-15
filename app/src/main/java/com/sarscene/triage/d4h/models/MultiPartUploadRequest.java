package com.sarscene.triage.d4h.models;

import android.util.Log;

import java.io.*;

/**
 * Created by amcrober on 2016-06-13.
 */
public class MultiPartUploadRequest {
    static final String TAG = MultiPartUploadRequest.class.getName();
    String twoHyphens = "--";
    String lineEnd = "\r\n";
    String requestBody = "";
    String requestBoundary = "";

    public MultiPartUploadRequest(String filepath) throws IOException {
        requestBoundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        buildMultipartRequestBody(filepath);
    }

    public byte[] getBody() {
        Log.d(TAG, requestBody);
        return requestBody.getBytes();
    }

    public String getBoundary() {
        return requestBoundary;
    }

    private void buildMultipartRequestBody(String filepath) throws IOException {
        try {
            java.io.File file = new java.io.File(filepath);
            FileInputStream fileInputStream = new FileInputStream(file);

            String[] filepathParts = filepath.split("/");
            String filepathFilename = filepathParts[filepathParts.length-1];

            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder
                    .append(twoHyphens).append(requestBoundary).append(lineEnd)
                    .append("Content-Disposition: form-data; name=\"filedata\"; filename=\"").append(filepathFilename).append("\"").append(lineEnd)
                    .append("Content-Type: image/jpeg").append(lineEnd).append(lineEnd);

            Reader reader = new InputStreamReader(fileInputStream, "UTF-8");
            char[] buffer = new char[1024];
            int amountRead = reader.read(buffer);
            while (amountRead > 0) {
                requestBuilder.append(buffer, 0, amountRead);
                amountRead = reader.read(buffer);
            }
            requestBuilder
                    .append(lineEnd).append(twoHyphens).append(requestBoundary).append(twoHyphens).append(lineEnd);
            requestBody = requestBuilder.toString();
        } catch (Exception e) {
            Log.e("MultipartRequest", "Multipart Form Upload Error");
            e.printStackTrace();
        }
    }
}
