package com.sarscene.triage.d4h.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.sarscene.triage.R;
import com.sarscene.triage.d4h.api.AuthenticationManager;
import com.sarscene.triage.d4h.api.HUDManager;
import com.reconinstruments.os.connectivity.HUDConnectivityManager;

public class LauncherActivity extends Activity {
    static final String TAG = LauncherActivity.class.getName();
    FlowState mCurrentState;
    HUDConnectivityManager hudConnectivityManager;

    private enum FlowState {
        LOADING,
        CONNECT_SMARTPHONE,
        NO_WEB,
    }

    class LoginConnectTask extends AsyncTask<Void, Void, Boolean> {
        LoginConnectTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return AuthenticationManager.login("alexmcroberts", "Virusthreat1987");
        }

        protected void onPostExecute(Boolean responseStatus) {
            LauncherActivity.this.postLogin(responseStatus);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        hudConnectivityManager = HUDManager.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hudConnectivityManager.isHUDConnected()){
            if (! hudConnectivityManager.hasWebConnection()) {
                setCurrentState(FlowState.NO_WEB);
            }else{
                beginLogin();
            }
        }else{
            setCurrentState(FlowState.CONNECT_SMARTPHONE);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
            if (mCurrentState == FlowState.CONNECT_SMARTPHONE){
                startActivity(new Intent("com.reconinstruments.connectdevice.CONNECT"));
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    protected void onDestroy() {
        super.onDestroy();
//        AutoUploadService.onDestroy();
    }

    void beginLogin() {
        setCurrentState(FlowState.LOADING);
        new LoginConnectTask().execute();
    }

    void postLogin(Boolean responseStatus) {
        Log.d(TAG, "onReceiveConnectJSON: " + responseStatus);
        if (responseStatus) {
            startActivity(new Intent(LauncherActivity.this, ChannelActivity.class));

            finish();
        } else {
            Log.e(TAG, "Login Failed?");
        }
    }

    private void showView(int view_id) {
        findViewById(view_id).setVisibility(View.VISIBLE);
    }

    private void hideViews(){
        findViewById(R.id.loader).setVisibility(View.GONE);
        findViewById(R.id.no_web).setVisibility(View.GONE);
        findViewById(R.id.connect_smartphone).setVisibility(View.GONE);
    }

    private void setCurrentState(FlowState state){
        mCurrentState = state;
        hideViews();
        switch (state){
            case CONNECT_SMARTPHONE:
                showView(R.id.connect_smartphone);
                break;
            case NO_WEB:
                showView(R.id.no_web);
            case LOADING:
                showView(R.id.loader);
                break;
        }
    }
}
