package dj.example.main.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

import dj.example.main.uiutils.ColoredSnackbar;


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
        if (!TextUtils.isEmpty(value))
            return true;
        String serverMsg = "Internal Err";
        if (status != null){
            if (!TextUtils.isEmpty(status.getError()))
                serverMsg = status.getError();
            else if (status.getCode() == 200 && !(TextUtils.isEmpty(status.getMessage())))
                serverMsg = status.getMessage();
        }
        Snackbar snackbar = Snackbar.make(hostPort, serverMsg, Snackbar.LENGTH_SHORT);
        ColoredSnackbar.alert(snackbar).show();
        return false;
        //return isResultOK( url,  value,   status, msg, hostPort, activityContext,null);
    }

    
}
