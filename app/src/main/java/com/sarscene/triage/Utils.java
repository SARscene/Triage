package com.sarscene.triage;

import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

public class Utils {
    static final String TAG = Utils.class.getName();

    static String getFileAsBase64(File file) {
        try {
            byte[] fileContent = new byte[((int) file.length())];
            int fileSize = new FileInputStream(file).read(fileContent);
            Log.d(TAG, "Read " + fileSize + " total bytes");
            return Base64.encodeToString(fileContent, 0);
        } catch (Exception e) {
            return BuildConfig.FLAVOR;
        }
    }
}
