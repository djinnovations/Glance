package co.djphy.glance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.material.snackbar.Snackbar;
import com.rey.material.widget.SnackBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.fragments.LoginUserPasswordFragment;
import co.djphy.glance.fragments.PresidentRegisterFragment;
import co.djphy.glance.model.UserInfo;
import co.djphy.glance.utils.IDUtils;
import co.djphy.glance.utils.IntentKeys;
import co.djphy.glance.utils.NetworkResultValidator;

/**
 * Created by User on 26-01-2017.
 */

public class NormalLoginActivity extends BaseActivity {

    private String TAG = getClass().getCanonicalName();

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public Activity getSelf() {
        return this;
    }

    @Override
    public View getViewForLayoutAccess() {
        return progressBar;
    }

    @Override
    public void onBackPressed() {
        if (isLoginFragment)
            super.onBackPressed();
        else transactLoginFragment(null);
    }

    @Override
    protected void onGarbageCollection() {
        TAG = null;
    }

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.llContainer)
    LinearLayout llLoginContainer;

    //private LoginUserPasswordFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_normal);
        ButterKnife.bind(this);
        transactLoginFragment(null);
    }


    private FragmentTransaction animateTransaction(FragmentTransaction manager, boolean isRightToLeft) {
        if (isRightToLeft)
            return manager.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_into_left);
        return manager.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }

    private boolean isLoginFragment;

    public void transactLoginFragment(Bundle arguments){
        isLoginFragment = true;
        LoginUserPasswordFragment fragment = new LoginUserPasswordFragment();
        if (arguments != null)
            fragment.setArguments(arguments);
        animateTransaction(getSupportFragmentManager().beginTransaction(), true)
                .replace(llLoginContainer.getId(), fragment).commit();
    }


    public void transactRegisterFragment(){
        isLoginFragment = false;
        animateTransaction(getSupportFragmentManager().beginTransaction(), true)
                .replace(llLoginContainer.getId(), new PresidentRegisterFragment()).commit();
    }


    public void onRegisterClickedDelegate(UserInfo info) {

        //queryForRegister(info);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public final int REGISTER_CALL = IDUtils.generateViewId();

    public void queryForRegister(UserInfo info) {
        startProgress();
        AjaxCallback ajaxCallback = getAjaxCallback(REGISTER_CALL, info);
        ajaxCallback.method(AQuery.METHOD_POST);
        String url = /*URLHelper.getInstance().getNormalLoginAPI()*/ " "; // TODO: 08-07-2017 add normal login API
        Log.d(TAG, "POST url- queryForLogin()" + TAG + ": " + url);
        Map<String, String> params = new HashMap<>();
        params.put("email", info.emailId);
        params.put("password", info.password);
        params.put("role", "intern");
        params.put("place_id", info.place.getId());
        Log.d(TAG, "POST reqParams- queryForLogin()" + TAG + ": " + params);
        getAQuery().ajax(url, params, String.class, ajaxCallback);
    }

    @Override
    public void serverCallEnds(Object data, int id, String url, Object json, AjaxStatus status) {
        stopProgress();
        if (id == REGISTER_CALL){
            boolean success = NetworkResultValidator.getInstance().isResultOK((String) json, status, progressBar);
            if (success) {
                UserInfo info = (UserInfo) data;
                setInfoMsg("Registration complete", Snackbar.LENGTH_LONG);
                Bundle bundle = new Bundle();
                bundle.putString(IntentKeys.KEY_EMAIL_ID, info.emailId);
                bundle.putString(IntentKeys.KEY_PASSWORD, info.password);
                transactLoginFragment(bundle);
            }
        }
        else super.serverCallEnds(data, id, url, json, status);
    }
}
