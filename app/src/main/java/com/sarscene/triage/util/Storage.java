package com.sarscene.triage.util;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class Storage {

    public static final String DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
    public static final String JPEG = ".jpg";

    public static SimpleDateFormat dateFormat;
    static {
        dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static ContentValues getPhotoData(long dateTaken) {
        String title = "IMG_"+dateFormat.format(dateTaken);
        String filename = title + JPEG;
        String path = DIRECTORY + '/' + filename;
        ContentValues photoValues = new ContentValues(5);
        photoValues.put(MediaStore.Images.ImageColumns.TITLE, title);
        photoValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, filename);
        photoValues.put(MediaStore.Images.ImageColumns.DATE_TAKEN, dateTaken);
        photoValues.put(MediaStore.Images.ImageColumns.DATA, path);
        return photoValues;
    }

    public static String getPhotoPath(ContentValues values) {
        return values.getAsString(MediaStore.Images.ImageColumns.DATA);
    }

    public static Uri insertJpeg(Context context, byte[] data, long dateTaken) {

        ContentValues metaData = getPhotoData(dateTaken);
        String path = getPhotoPath(metaData);

        FileOutputStream out = null;
        Uri uri = null;
        try {
            out = new FileOutputStream(path);
            out.write(data);
            uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, metaData);
            Log.v("SavePhoto", "Saved image to " + path);
        } catch (Exception e) {
            Log.e("SavePhoto", "Failed to write data", e);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                Log.e("SavePhoto", "Failed to close file after write", e);
            }
        }
        return uri;
    }
}
