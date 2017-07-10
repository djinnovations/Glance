package dj.example.main.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import dj.example.main.R;
import dj.example.main.modules.appupdater.MyAppRaterUpdateHelper;
import dj.example.main.modules.sociallogins.SocialLoginUtil;
import dj.example.main.uiutils.ColoredSnackbar;
import dj.example.main.uiutils.WindowUtils;

public abstract class AppCoreActivity extends AppCompatActivity {

    private String TAG = "AppCoreActivity";

    protected ProgressBar progressBar;
    public abstract ProgressBar getProgressBar();
    public abstract Activity getSelf();
    public abstract View getViewForLayoutAccess();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

    protected void startProgress() {
        Runnable runnable = new Runnable() {
            public void run() {
                AppCoreActivity.this.progressBar = getProgressBar();
                if (progressBar != null)
                    progressBar.setVisibility(View.VISIBLE);
                displayTransOverlay(false);
            }
        };
        MyApplication.getInstance().getUiHandler().post(runnable);
    }

    protected void stopProgress() {
        Runnable runnable = new Runnable() {
            public void run() {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                dismissTransOverlay();
            }
        };
        MyApplication.getInstance().getUiHandler().post(runnable);
    }


    protected void checkIfAppUpdated() {
        MyAppRaterUpdateHelper updateHelper = MyAppRaterUpdateHelper.getInstance();
        updateHelper.checkForUpdates(this);
        updateHelper.rateApp(this);
    }

    protected AjaxCallback getAjaxCallback(final int id) {
        AjaxCallback<Object> ajaxCallback = new AjaxCallback<Object>() {
            public void callback(String url, Object json, AjaxStatus status) {
                serverCallEnds(id, url, json, status);
            }
        };
        /*List<Cookie> cookies = getApp().getCookies();
        if (cookies != null && cookies.size() != 0) {
            for (Cookie cookie : cookies) {
                ajaxCallback.cookie(cookie.getName(), cookie.getValue());
            }
        }*/
        return ajaxCallback;
    }


    private AQuery aQuery;

    protected AQuery getAQuery() {
        if (aQuery == null)
            aQuery = new AQuery(this);
        return aQuery;
    }

    public void serverCallEnds(int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried-" + TAG + ": " + url);
        Log.d(TAG, "response-" + TAG + ": " + json);
        stopProgress();
    }


    protected Dialog transDialog;

    protected void displayTransOverlay(final boolean isShowProgressBar) {
        Runnable runnable = new Runnable() {
            public void run() {
                View view = LayoutInflater.from(AppCoreActivity.this).inflate(R.layout.transparent_overlay, null);
                if (transDialog == null)
                    transDialog = WindowUtils.getInstance().displayDialogNoTitle(AppCoreActivity.this, view);
                transDialog.show();
                if (isShowProgressBar)
                    view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                else view.findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        };
        MyApplication.getInstance().getUiHandler().post(runnable);
    }

    protected void dismissTransOverlay() {
        Runnable runnable = new Runnable() {
            public void run() {
                if (transDialog != null)
                    transDialog.dismiss();
            }
        };
        MyApplication.getInstance().getUiHandler().post(runnable);
    }

    protected void promptBeforeExit(){
        final Snackbar snackbar = Snackbar.make(getViewForLayoutAccess(), "Sure you want to exit?", Snackbar.LENGTH_SHORT);
        snackbar.setAction("Yes", new View.OnClickListener() {
            public void onClick(View v) {
                snackbar.dismiss();
                onExitYesClick();
            }
        });
        ColoredSnackbar.alert(snackbar).show();
    }

    protected void onExitYesClick(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}
