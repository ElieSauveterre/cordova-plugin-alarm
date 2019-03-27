package org.eliesauveterre.cordova.alarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ionicframework.cordova.webview.IonicWebViewEngine;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;

public class LockScreenActivity extends CordovaActivity {

    private static final String TAG = "LockScreenActivity";
    private float lastVolumeValue = 0.1f;
    private MediaPlayer mediaPlayer;
    private LinearLayout layout;
    private SystemWebView webView;
    private Handler volumeIncrease;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showOnLockScreen();

        Log.d(TAG, "Adding webview container");
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(layout);

        Log.d(TAG, "Starting WebApp");
        super.init();
        loadUrl(launchUrl);

        Log.d(TAG, "Notify with sound and vibrator");
        startVibrator();
        startRingTone();

        this.preferences.set("alarmFired", true);
    }

    @Override
    protected CordovaWebView makeWebView() {
        webView = new SystemWebView(this);
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout.addView(webView);
        return new CordovaWebViewImpl(new IonicWebViewEngine(webView, this.preferences));
    }

    @Override
    protected void createViews() {
        appView.getView().requestFocusFromTouch();
    }

    @Override
    public void onDestroy() {

        volumeIncrease.removeCallbacksAndMessages(null);
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;

        layout.removeView(webView);
        webView.removeAllViews();
        webView.destroy();

        super.onDestroy();
    }

    private void showOnLockScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    private void startVibrator() {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(2000);
    }

    private void startRingTone() {

        Uri defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE);
        mediaPlayer = MediaPlayer.create(this, defaultRingtoneUri);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(lastVolumeValue, lastVolumeValue);
        mediaPlayer.start();

        scheduleVolumeIncrease();
    }

    private void scheduleVolumeIncrease() {
        volumeIncrease = new Handler();
        volumeIncrease.postDelayed(
                new Runnable() {
                    public void run() {

                        lastVolumeValue = Math.min(lastVolumeValue + 0.1f, 1f);
                        mediaPlayer.setVolume(lastVolumeValue, lastVolumeValue);

                        scheduleVolumeIncrease();
                    }
                },
                4000);
    }
}
