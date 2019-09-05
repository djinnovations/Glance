package co.djphy.glance.utils;

import com.google.android.material.snackbar.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import co.djphy.glance.uiutils.ColoredSnackbar;


/**
 * Created by DJphy on 11/6/15.
 */
public class NetworkResultValidator {
    // Private constructor prevents instantiation from other classes
    private NetworkResultValidator() {}

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private static final NetworkResultValidator INSTANCE = new NetworkResultValidator();
    }

    public static NetworkResultValidator getInstance() {
        return SingletonHolder.INSTANCE;
    }




    public boolean isResultOK(String value, final AjaxStatus status, View hostPort)
    {
        if (TextUtils.isEmpty(value)){
            return false;
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(value);
                String msg = jsonObject.getString("message");
                Snackbar snackbar = Snackbar.make(hostPort, msg, Snackbar.LENGTH_SHORT);
                ColoredSnackbar.alert(snackbar).show();
                return jsonObject.isNull("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String serverMsg = "Internal Err";
        if (status != null){
            if (!TextUtils.isEmpty(status.getError()))
                serverMsg = status.getError();
            else if (status.getCode() == 200 && !(TextUtils.isEmpty(status.getMessage())))
                serverMsg = status.getMessage();
        }
        Snackbar snackbar = Snackbar.make(hostPort, serverMsg, Snackbar.LENGTH_LONG);
        ColoredSnackbar.alert(snackbar).show();
        return false;
        //return isResultOK( url,  value,   status, msg, hostPort, activityContext,null);
    }

    
}
