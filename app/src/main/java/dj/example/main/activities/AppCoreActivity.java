package dj.example.main.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

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


    protected void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    protected void startProgress() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    protected void stopProgress() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
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

}
