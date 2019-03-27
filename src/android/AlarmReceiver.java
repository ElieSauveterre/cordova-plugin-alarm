package org.eliesauveterre.cordova.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.apache.cordova.LOG;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        LOG.v(TAG, "Alarm received");
        intent = new Intent(context, LockScreenActivity.class);
        context.startActivity(intent);
    }
}
