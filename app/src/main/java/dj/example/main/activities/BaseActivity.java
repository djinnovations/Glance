package dj.example.main.activities;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.util.AQUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dj.example.main.uiutils.ColoredSnackbar;
import dj.example.main.utils.IDUtils;
import dj.example.main.utils.URLHelper;

/**
 * Created by User on 25-01-2017.
 */

public abstract class BaseActivity extends AppCoreActivity {

    private String TAG = "BaseActivity";

    public final int SOCIAL_LOGIN_CALL = IDUtils.generateViewId();
    public void queryForSocialLogin(JSONObject inputParams){
        startProgress();
        AjaxCallback ajaxCallback = getAjaxCallback(SOCIAL_LOGIN_CALL);
        ajaxCallback.method(AQuery.METHOD_POST);
        ajaxCallback.header("content-type", "application/json");
        String url = /*URLHelper.getInstance().getSocialLoginAPI()*/ " "; //// TODO: 08-07-2017  add social login actual API
        Log.d(TAG, "POST url- queryForSocialLogin()" + TAG + ": " + url);
        Map<String,Object> params = null;
        try {
            params = new ObjectMapper().readValue(inputParams.toString(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (params == null){
            ColoredSnackbar.alert(Snackbar.make(getViewForLayoutAccess(), "Sign in Failed", Snackbar.LENGTH_SHORT)).show();
            return;
        }
        Log.d(TAG, "POST reqParams- queryForSocialLogin()" + TAG + ": " + params);
        getAQuery().ajax(url, params, String.class, ajaxCallback);
    }


    public void setErrMsg(String msg){
        ColoredSnackbar.alert(Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_SHORT)).show();
    }

    public void setErrMsg(String msg, boolean lengthLong) {
        int time = Snackbar.LENGTH_SHORT;
        if (lengthLong)
            time = Snackbar.LENGTH_LONG;
        setErrMsg(msg);
    }

    public void setErrMsg(Object json) {
        String msg = "UNKNOWN_ERR";
        try {
            msg = new JSONObject(json.toString()).getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setErrMsg(msg);
    }

    public void setWarningMsg(String msg){
        ColoredSnackbar.warning(Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_SHORT)).show();
    }

    public void setInfoMsg(String msg){
        ColoredSnackbar.info(Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_SHORT)).show();
    }

    public final int NORMAL_LOGIN_CALL = IDUtils.generateViewId();
    public void queryForLogin(String userId, String password) {
        startProgress();
        AjaxCallback ajaxCallback = getAjaxCallback(NORMAL_LOGIN_CALL);
        ajaxCallback.method(AQuery.METHOD_POST);
        String url = /*URLHelper.getInstance().getNormalLoginAPI()*/ " "; // TODO: 08-07-2017 add normal login API
        Log.d(TAG, "POST url- queryForLogin()" + TAG + ": " + url);
        Map<String, String> params = new HashMap<>();
        params.put("email", userId);
        params.put("password", password);
        params.put("role", "intern");
        Log.d(TAG, "POST reqParams- queryForLogin()" + TAG + ": " + params);
        getAQuery().ajax(url, params, String.class, ajaxCallback);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isTaskRoot()){
            //clean the file cache with advance option
            //long triggerSize = 3000000; //starts cleaning when cache size is larger than 3M
            //long targetSize = 2000000;  //remove the least recently used files until cache size is less than 2M
            AQUtility.cleanCacheAsync(this/*, triggerSize, targetSize*/);
        }
    }
}
