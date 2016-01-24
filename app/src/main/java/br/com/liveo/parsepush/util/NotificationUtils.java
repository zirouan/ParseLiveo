package br.com.liveo.parsepush.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.liveo.parsepush.R;

public class NotificationUtils {

    private Context mContext;
    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotification(JSONObject jsonPush, Intent intent) {

        if (jsonPush == null) {
            return;
        }

        String alert;
        boolean isBackground;

        try {
            alert = jsonPush.getString(Constants.ALERT);
            isBackground = jsonPush.getBoolean(Constants.SHOW_NOTIFICATION);

            if (isBackground) {

                int icon = R.mipmap.ic_launcher;
                int smallIcon = R.mipmap.ic_launcher;
                int notificationId = R.mipmap.ic_launcher;

                intent.putExtra(Constants.PUSH_JSON, jsonPush.toString());
                PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, 0, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
                Notification notification = mBuilder.setSmallIcon(smallIcon)
                        .setTicker(mContext.getString(R.string.app_name))
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentTitle(mContext.getString(R.string.app_name))
                        .setStyle(inboxStyle)
                        .setContentIntent(resultPendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                        .setContentText(alert)
                        .build();

                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(notificationId, notification);
            } else {
                intent.putExtra(Constants.PUSH_JSON, jsonPush.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
