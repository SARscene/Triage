package com.sarscene.triage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sarscene.triage.d4h.api.FileManager;
import com.sarscene.triage.d4h.api.TypeManager;
import com.sarscene.triage.d4h.models.Channel;
import com.sarscene.triage.d4h.models.File;

import org.json.JSONException;

public class PhotoUploader {
    static final String TAG = PhotoUploader.class.getName();
    Context mContext;
    Channel mChannel;

    PhotoUploader(Context context, Channel channel) {
        this.mContext = null;
        this.mContext = context;
        this.mChannel = channel;
    }

    void photoFolderEvent(String filepath) {
        Log.d(TAG, "File changed: " + filepath);
        new UploadFileTask(mChannel, filepath).execute();
    }

    class UploadFileTask extends AsyncTask<String, Void, Boolean> {
        public UploadFileTask(Channel channel, String filepath) {
            File file = FileManager.upload(channel, filepath);
            try {
                TypeManager.logWithAttachment(
                        channel,
                        "File uploaded at " + Long.toString(System.currentTimeMillis()),
                        file);
            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
            }
        }

        protected Boolean doInBackground(String... urls) {
            try {
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                Log.e(PhotoUploader.TAG, "WEEEEEE");
            } else {
                Log.e(PhotoUploader.TAG, "BOOOOOOOO");
            }
        }
    }
}
