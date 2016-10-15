package com.sarscene.triage;

import android.content.ContextWrapper;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

import com.sarscene.triage.d4h.models.Channel;

public class PhotoFileObserver extends FileObserver {
    public static final String PHOTO_USER_FULL_PATH;
    public static final String TAG = PhotoFileObserver.class.getName();

    static {
        PHOTO_USER_FULL_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera";
    }

    private PhotoUploader photoUploader;

    public PhotoFileObserver(ContextWrapper context, Channel channel) {
        super(PHOTO_USER_FULL_PATH, FileObserver.CLOSE_WRITE);
        this.photoUploader = new PhotoUploader(context, channel);
    }

    public void startWatching() {
        super.startWatching();
    }

    public void stopWatching() {
        super.stopWatching();
    }

    public void onEvent(int event, String path) {
        Log.i(TAG, "File changed: " + path);
        //todo: new casualty, set the filepath
    }
}
