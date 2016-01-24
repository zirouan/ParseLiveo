package br.com.liveo.parsepush.util;

import com.parse.ParseInstallation;
import com.parse.ParseObject;

/**
 * Created by Rudsonlive on 15/01/16.
 */
public class Utils {

    public static void subscribeWithUser(String objectId) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(Constants.USER_ID, getObjectIdUser(objectId));
        installation.saveInBackground();
    }

    public static Object getObjectIdUser(String objectId){
        return ParseObject.createWithoutData(Constants.CLASS_USER, objectId);
    }
}
