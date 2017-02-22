package com.example.tapojit.hamphack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by tapojit on 1/14/17.
 */

public class Receiver extends BroadcastReceiver {

    private static final String TAG = "RestartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");
        context.startService(new Intent(context.getApplicationContext(),NotificationService.class));
    }
}
