package com.sarscene.triage.d4h.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.CamcorderProfile;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.reconinstruments.ui.carousel.CarouselActivity;
import com.reconinstruments.ui.carousel.CarouselItem;
import com.reconinstruments.ui.carousel.StandardCarouselItem;
import com.sarscene.triage.R;
import com.sarscene.triage.util.Storage;

public class CameraActivity extends CarouselActivity {

    private static final String TAG = "CameraActivity";

    Camera camera;

    CameraPreview preview;
    FrameLayout modeSwitchView;
    CamcorderProfile profile;

    CarouselItem photoItem;
    PictureCallback jpegSavedCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Uri uri = Storage.insertJpeg(CameraActivity.this, data, System.currentTimeMillis());
            preview.setCamera(camera, profile);
            closeCamera();
            Intent intent = new Intent();
            intent.putExtra("data", uri);
            setResult(2, intent);
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sarscene.triage.R.layout.layout_camera);

        photoItem = new StandardCarouselItem(R.drawable.photo_icon) {
            @Override
            public void onClick(Context context) {
                camera.takePicture(null, null, jpegSavedCallback);
            }
        };
        getCarousel().setContents(photoItem);

        preview = (CameraPreview) findViewById(R.id.preview);
        modeSwitchView = (FrameLayout) findViewById(R.id.mode_switcher);

        try {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
        } catch (RuntimeException e) {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        }

    }

    // use onkeyup rather than CarouselItem.onClick for videoItem because the onClick event won't
    // be received by the videoItem when it's invisible (when recording), can't be focused
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
    }

    public void openCamera() {
        try {
            camera = Camera.open();
        } catch (RuntimeException ex) {
            Toast.makeText(this, "Failed to open camera", Toast.LENGTH_SHORT).show();
        }
        if (camera != null)
            preview.setCamera(camera, profile);
    }

    public void closeCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}