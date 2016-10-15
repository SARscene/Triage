package com.sarscene.triage.d4h.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sarscene.triage.PhotoFileObserver;
import com.reconinstruments.os.connectivity.HUDConnectivityManager;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.StandardListItem;
import com.sarscene.triage.d4h.AuthenticationManager;
import com.sarscene.triage.d4h.api.HUDManager;
import com.sarscene.triage.d4h.api.TypeManager;
import com.sarscene.triage.d4h.api.TypeManager.SubType;
import com.sarscene.triage.d4h.models.Channel;
import com.sarscene.triage.d4h.models.User;
import com.sarscene.triage.R;

import org.json.JSONException;

public class ChannelActionActivity extends SimpleListActivity {
    static final String TAG = ChannelActionActivity.class.getName();

    private HUDConnectivityManager hudConnectivityManager;
    public Channel channel;
    private boolean mAlreadyStarted;
    private PhotoFileObserver mPhotoFileObserver;

    public class ListItem extends StandardListItem {
        SubType subType;

        public ListItem(String text, SubType subType) {
            super(text);
            this.subType = subType;
        }

        public void onClick(Context context) {
            CharSequence text = "";
            try {
                switch (subType) {
                    case CHAT:
                        TypeManager.chat(channel, "This is a chat");
                        text = "This is a chat";
                        break;
                    case LOG:
                        TypeManager.log(channel, "This is a log");
                        text = "This is a log";
                        break;
                    case TRIAGE:
                        initPhotoObserver();
                        promptPhoto();
                        //See PhotoFileObserver.onEvent() for the rest
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
//            context.startActivity(new Intent(context, activityClass));
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hudConnectivityManager = HUDManager.getInstance();
        setContentView(R.layout.list_standard_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // no user = launch again and login
        Log.e(TAG, "onResume...");
        User u = AuthenticationManager.getUser();
        if (null == u.getToken()) {
            Log.e(TAG, "...user doesn't exist...return to launcher!");
            startActivity(new Intent(ChannelActionActivity.this, LauncherActivity.class));
            finish();
        }
        Log.e(TAG, "...user exists " + u.getUserData().getUsername());

        getChannel();
        initPhotoObserver();
        populateListView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyPhotoObserver();
    }

    private void initPhotoObserver() {
        if (!mAlreadyStarted) {
            mPhotoFileObserver = new PhotoFileObserver(this, channel);
            mPhotoFileObserver.startWatching();
            mAlreadyStarted = true;
        }
    }

    private void destroyPhotoObserver() {
        if (mAlreadyStarted) {
            mPhotoFileObserver.stopWatching();
            mPhotoFileObserver = null;
            mAlreadyStarted = false;
        }
    }

    private void getChannel() {
        Bundle extras = getIntent().getExtras();
        Intent intentIncoming = getIntent();
        if (extras != null) {
            channel = intentIncoming.getParcelableExtra("channel");
        }
    }

    private void populateListView() {
        setContents(
                new ListItem("Chat something", SubType.CHAT),
                new ListItem("Log something", SubType.LOG),
                new ListItem("Triage something", SubType.TRIAGE)
        );
    }

    public void promptPhoto() {
        Intent intent = new Intent("com.reconinstruments.camera");
        startActivity(intent);
    }

    public class ListItem extends StandardListItem {
        SubType subType;

        public ListItem(String text, SubType subType) {
            super(text);
            this.subType = subType;
        }

        public void onClick(Context context) {
            CharSequence text = "";
            try {
                switch (subType) {
                    case CHAT:
                        TypeManager.chat(channel, "This is a chat");
                        text = "This is a chat";
                        break;
                    case LOG:
                        TypeManager.log(channel, "This is a log");
                        text = "This is a log";
                        break;
                    case TRIAGE:
                        initPhotoObserver();
                        promptPhoto();
                        //See PhotoFileObserver.onEvent() for the rest
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
//            context.startActivity(new Intent(context, activityClass));
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
