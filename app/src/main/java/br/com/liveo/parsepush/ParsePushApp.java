package br.com.liveo.parsepush;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import br.com.liveo.parsepush.util.Constants;

/**
 * Created by Rudsonlive on 13/01/16.
 */
public class ParsePushApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setUpParse(this);
    }

    public static void setUpParse(Context context) {

        Parse.enableLocalDatastore(context);
        Parse.initialize(context, "SUA_APPLICATION_ID", "SUA CLIENT_KEY");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);

        ParsePush.subscribeInBackground(Constants.CHANNEL, new SaveCallback() {
            @Override
            public void done(ParseException error) {
                if (error == null) {
                    Log.i(Constants.TAG, "Successfully subscribed to Parse!");
                }else{
                    Log.i(Constants.TAG, "Error subscribed to Parse!");
                }
            }
        });
    }
}
