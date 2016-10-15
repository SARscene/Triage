package com.sarscene.triage.d4h.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.reconinstruments.os.connectivity.HUDConnectivityManager;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;
import com.reconinstruments.ui.list.StandardListItem;
import com.sarscene.triage.R;
import com.sarscene.triage.d4h.AuthenticationManager;
import com.sarscene.triage.d4h.api.ChannelManager;
import com.sarscene.triage.d4h.api.HUDManager;
import com.sarscene.triage.d4h.models.Channel;
import com.sarscene.triage.d4h.models.User;

import java.util.ArrayList;

public class ChannelActivity extends SimpleListActivity {

    static final String TAG = ChannelActivity.class.getName();
    HUDConnectivityManager hudConnectivityManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hudConnectivityManager = HUDManager.getInstance();
        setContentView(R.layout.list_standard_layout);

        ArrayList<Channel> channels = ChannelManager.listChannels();
        ArrayList<SimpleListItem> listItems;
        listItems = new ArrayList<SimpleListItem>();
        ChannelListItem addItem;

        for (Channel channel : channels) {
            if (null != channel.getRoomDbName() && !channel.getRoomDbNameId().equalsIgnoreCase("null")) {
                addItem = new ChannelListItem(channel.getRoomDbNameId(), channel);
                listItems.add(addItem);
            }
        }
        setContents(listItems);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // no user = launch again and login
        Log.e(TAG, "onResume...");
        User u = AuthenticationManager.getUser();
        if (null == u.getToken()) {
            Log.e(TAG, "...user doesn't exist...return to launcher!");
            startActivity(new Intent(ChannelActivity.this, LauncherActivity.class));
            finish();
        }
        Log.e(TAG, "...user exists " + u.getUserData().getUsername());
    }

    public class ChannelListItem extends StandardListItem {
        Channel channel;

        public ChannelListItem(String text, Channel channel) {
            super(text);
            this.channel = channel;
        }

        public void onClick(Context context) {
            Intent i = new Intent(getApplicationContext(), ChannelActionActivity.class);
            // sending data to new activity
            i.putExtra("channel", (Parcelable) channel);
            startActivity(i);
        }
    }
}
