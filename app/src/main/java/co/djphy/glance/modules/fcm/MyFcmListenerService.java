package co.djphy.glance.modules.fcm;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import co.djphy.glance.MyApplication;
import co.djphy.glance.activities.MainActivity;
import co.djphy.glance.utils.DateTimeUtils;

public class MyFcmListenerService extends FirebaseMessagingService {

    private static final String TAG = "MyFcmListenerService";
    private NotificationUtils notificationUtils;

    @Override
    public void onNewToken(String token){
        //send token to server
    }

    @Override
    public void onMessageReceived(RemoteMessage messageMain) {

        String from = messageMain.getFrom();
        Map<String, String> databundle = messageMain.getData();

        String title = null;
        String message = null;
        //String image = null;
        String timestamp = null;
        String bannerImgUrl = null;
        String deepLinkData = null;
        for (String key : databundle.keySet()) {
            Log.d(TAG, key + " is a key in the bundle");
        }

        if (databundle != null) {
            message = databundle.get("mp_message");
            bannerImgUrl = databundle.get("mp_icnm");
            deepLinkData = databundle.get("mp_cta");
            title = databundle.get("mp_title");
        } else {
            Log.d(TAG, "bundle is null");
        }
        timestamp = DateTimeUtils.getCurrentDateTime("hh:mm a");//dd/MM/yyyy hh:mm a
        Log.i(TAG, "From: " + from);
        Log.i(TAG, "Title: " + title);
        Log.i(TAG, "message: " + message);
        Log.i(TAG, "ctaData: " + deepLinkData);
        Log.i(TAG, "timestamp: " + timestamp);

        if (!NotificationUtils.isAppIsInBackground(MyApplication.getInstance())) {

            // app is in foreground, broadcast the push message
            Log.e(TAG, "App is in foreground");
            Intent pushNotification = new Intent(FCMConstants.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(MyApplication.getInstance()).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils();
            notificationUtils.playNotificationSound();
        } else {

            Log.e(TAG, "App is in background");
            Intent resultIntent = new Intent(MyApplication.getInstance(), MainActivity.class);
            resultIntent.putExtra("message", message);

            if (TextUtils.isEmpty(bannerImgUrl)) {
                showNotificationMessage(title, message, timestamp, resultIntent);
            } else {
                showNotificationMessageWithBigImage(title, message, timestamp, resultIntent, bannerImgUrl);
            }
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}