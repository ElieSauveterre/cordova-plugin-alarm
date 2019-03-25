package org.eliesauveterre.cordova.alarm;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class Alarm extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        if (action.equals("add")) {

            callbackContext.success("test");

            return true;

        } else {
            return false;
        }
    }
}
