package org.eliesauveterre.cordova.alarm;

import org.apache.cordova.*;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;
import java.text.SimpleDateFormat;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.Intent;
import android.content.Context;

public class Alarm extends CordovaPlugin {

    private static final String TAG = "Alarm";

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) {

        try {

            if (action.equals("add")) {

                JSONObject options = data.getJSONObject(0);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date alarmDate = sdf.parse((String) options.get("date"));

                addAlarm(alarmDate);

                callbackContext.success("added for: " + alarmDate.toString());

                return true;
            }

            if (action.equals("remove")) {

                Intent intent = new Intent(this.cordova.getActivity(), AlarmReceiver.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.cordova.getActivity(), 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) (this.cordova.getActivity().getSystemService(Context.ALARM_SERVICE));
                alarmManager.cancel(pendingIntent);

                callbackContext.success("removed");
                return true;
            }

            if (action.equals("stop")) {

                LockScreenActivity lockScreenActivity = (LockScreenActivity) this.cordova.getActivity();
                lockScreenActivity.finish();

                startAppInBackground();

                callbackContext.success("true");
                return true;
            }

            if (action.equals("snooze")) {

                int snoozeMinutes = 10;
                Date nextReminder = new Date((new Date()).getTime() + 60000 * snoozeMinutes);
                addAlarm(nextReminder);

                LockScreenActivity lockScreenActivity = (LockScreenActivity) this.cordova.getActivity();
                lockScreenActivity.finish();

                startAppInBackground();

                callbackContext.success("true");
                return true;
            }

            if (action.equals("alarmFired")) {

                if (this.cordova.getActivity().getClass() == LockScreenActivity.class) {
                    callbackContext.success("true");
                }

                return true;
            }

            return false;

        } catch (Exception e) {

            LOG.v(TAG, "Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());

            return false;
        }
    }

    /**
     * Normal app launch to have the app in foreground after screen unlock.
     */
    private void startAppInBackground(){
        Intent  intent = new Intent();
        intent.setAction("org.eliesauveterre.cordova.alarm.ALARM");
        intent.setPackage(this.cordova.getActivity().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.cordova.getActivity().startActivity(intent);
    }

    private void addAlarm(Date time) {

        Intent intent = new Intent(this.cordova.getActivity(), AlarmReceiver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.cordova.getActivity(), 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) (this.cordova.getActivity().getSystemService(Context.ALARM_SERVICE));
        AlarmManager.AlarmClockInfo clockInfo = new AlarmManager.AlarmClockInfo(time.getTime(), pendingIntent);
        alarmManager.setAlarmClock(clockInfo, pendingIntent);
    }
}
