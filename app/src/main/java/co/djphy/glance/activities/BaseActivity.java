package co.djphy.glance.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.djphy.glance.R;
import co.djphy.glance.fragments.DailyReportFragment;
import co.djphy.glance.model.NavigationDataObject;
import co.djphy.glance.model.UserInfo;
import co.djphy.glance.uiutils.ColoredSnackbar;
import co.djphy.glance.uiutils.DisplayProperties;
import co.djphy.glance.uiutils.ViewConstructor;
import co.djphy.glance.uiutils.WindowUtils;
import co.djphy.glance.utils.IDUtils;
import co.djphy.glance.utils.IntentKeys;

/**
 * Created by DJphy on 25-01-2017.
 */

public abstract class BaseActivity extends AppCoreActivity {

    private String TAG = "BaseActivity";

    protected DisplayProperties displayProperties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orient = this.getResources().getConfiguration().orientation;
        displayProperties = DisplayProperties.getInstance(orient);
    }

    public void setErrMsgWithOk(String msg, String btnTxt) {
        final Snackbar snackbar = Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(btnTxt, new View.OnClickListener() {
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        ColoredSnackbar.info(snackbar).show();
    }

    public void setErrMsg(String msg) {
        ColoredSnackbar.alert(Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_SHORT)).show();
    }

    public void setErrMsgIndefinite(String msg) {
        ColoredSnackbar.alert(Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_INDEFINITE)).show();
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

    public void setWarningMsg(String msg) {
        ColoredSnackbar.warning(Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_SHORT)).show();
    }

    public void setInfoMsg(String msg) {
        ColoredSnackbar.info(Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_SHORT)).show();
    }

    public void setInfoMsg(String msg, int length) {
        ColoredSnackbar.info(Snackbar.make(getViewForLayoutAccess(), msg, length)).show();
    }

    public void setInfoMsgIndefinite(String msg) {
        ColoredSnackbar.info(Snackbar.make(getViewForLayoutAccess(), msg, Snackbar.LENGTH_INDEFINITE)).show();
    }

    protected View addBack(ViewGroup root) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_back, root, false);
        if (root.findViewById(R.id.ivPrimaryBack) != null)
            return null;
        root.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelf().onBackPressed();
            }
        });
        return view;
    }

    public void showDialogInfo(String msg, boolean isPositive) {
        int color;
        color = isPositive ? R.color.colorPrimary : R.color.redStatus;
        WindowUtils.getInstance().genericInfoMsgWithOK(this, null, msg, color);
    }


    Dialog alertDialogForgotPassword;

    public void performForgotPassword() {
        alertDialogForgotPassword = null;
        WindowUtils.getInstance().invokeForgotPasswordDialog(this, new ViewConstructor.InfoDisplayListener() {
            @Override
            public void onNegativeSelection(Dialog alertDialog) {

            }

            @Override
            public void onPositiveSelection(Dialog alertDialog) {
                alertDialogForgotPassword = alertDialog;
                String txt = (String) ((AlertDialog) alertDialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE).getTag();
                //queryForRenewSubs(txt);
                //performChangeFromLogOffToCurrent(StaticTopBarFragment.MENU_SEARCH);
            }
        });
    }

    protected boolean isClearTask;

    public boolean action(NavigationDataObject navigationDataObject) {
        int actionType = navigationDataObject.getTargetType();
        Log.d(TAG, "actionType: " + actionType);
        Intent intent;
        if (actionType == NavigationDataObject.ACTIVITY) {
            Class target = navigationDataObject.getTargetClass();
            if (target != null) {
                intent = new Intent(this, target);
                if (target.getSimpleName().equalsIgnoreCase("EmptyActivity")) {
                    intent.putExtra(IntentKeys.KEY_FRAGMENT_NAME, navigationDataObject.getFragmentName());
                    intent.putExtra(IntentKeys.TITLE, navigationDataObject.getTitle());
                }
                //add flags if any

                startActivity(intent);
            }
        } else if (actionType == NavigationDataObject.WEB_ACTIVITY) {

        } else if (actionType == NavigationDataObject.LOGOUT) {
            //// TODO: 08-07-2017  perform logout
            ViewConstructor.InfoDisplayListener infoDisplayListener = new ViewConstructor.InfoDisplayListener() {
                @Override
                public void onNegativeSelection(Dialog alertDialog) {
                    isClearTask = true;
                    Class target = navigationDataObject.getTargetClass();
                    if (target != null) {
                        Intent intent = new Intent(BaseActivity.this, target);
                        //intent.putExtra("lycavidurl", url);
                        if (isClearTask)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        else intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }

                @Override
                public void onPositiveSelection(Dialog alertDialog) {

                }
            };
            WindowUtils.getInstance().displayBottomSheetGenericMsg(this, "LogOff",
                    "Sure you gotta Logout?", "Nope", "Sure", infoDisplayListener);

        } else if (actionType == NavigationDataObject.SHARE) {
            //// TODO: 08-07-2017  any share actions here 
        } else if (actionType == NavigationDataObject.RATE_US) {
            //// TODO: 08-07-2017 rate us on playstore here 
        } else if (navigationDataObject.getViewId() == R.id.nav_menu_contact) {
            WindowUtils.getInstance().displayBottomSheetContactList(BaseActivity.this);
        }
        return true;
    }

    @Override
    public void serverCallEnds(int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried-" + TAG + ": " + url);
        Log.d(TAG, "response-" + TAG + ": " + json);

    }

    public final int SOCIAL_LOGIN_CALL = IDUtils.generateViewId();

    public void queryForSocialLogin(JSONObject inputParams) {
        startProgress();
        AjaxCallback ajaxCallback = getAjaxCallback(SOCIAL_LOGIN_CALL);
        ajaxCallback.method(AQuery.METHOD_POST);
        ajaxCallback.header("content-type", "application/json");
        String url = /*URLHelper.getInstance().getSocialLoginAPI()*/ " "; //// TODO: 08-07-2017  add social login actual API
        Log.d(TAG, "POST url- queryForSocialLogin()" + TAG + ": " + url);
        Map<String, Object> params = null;
        try {
            params = new ObjectMapper().readValue(inputParams.toString(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (params == null) {
            ColoredSnackbar.alert(Snackbar.make(getViewForLayoutAccess(), "Sign in Failed", Snackbar.LENGTH_SHORT)).show();
            return;
        }
        Log.d(TAG, "POST reqParams- queryForSocialLogin()" + TAG + ": " + params);
        getAQuery().ajax(url, params, String.class, ajaxCallback);
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
        if (isTaskRoot()) {
            //clean the file cache with advance option
            //long triggerSize = 3000000; //starts cleaning when cache size is larger than 3M
            //long targetSize = 2000000;  //remove the least recently used files until cache size is less than 2M
            AQUtility.cleanCacheAsync(this/*, triggerSize, targetSize*/);
        }
    }
}
