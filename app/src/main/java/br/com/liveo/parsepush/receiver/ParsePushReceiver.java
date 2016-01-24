package br.com.liveo.parsepush.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.liveo.parsepush.ui.activity.MainActivity;
import br.com.liveo.parsepush.util.Constants;
import br.com.liveo.parsepush.util.NotificationUtils;


public class ParsePushReceiver extends ParsePushBroadcastReceiver {

    private Intent mIntentReceiver;
    private final String TAG = ParsePushReceiver.class.getSimpleName();

    @Override
    protected void onPushReceive(Context context, Intent intent) {

        if (intent == null) {
            return;
        }

        try {
            mIntentReceiver = intent;
            JSONObject jsonPush = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            mIntentReceiver.putExtra(Constants.PUSH_JSON, jsonPush.toString());
            parsePushJson(context, jsonPush);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
    }

    private void parsePushJson(Context context, JSONObject json) {

        if (!json.isNull("alert")) {
            Intent intent = new Intent(context, MainActivity.class);
            NotificationUtils notificationUtils = new NotificationUtils(context);
            intent.putExtras(mIntentReceiver.getExtras());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notificationUtils.showNotification(json, intent);
        }
    }
}