package co.djphy.glance.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.fragments.LoginUserPasswordFragment;
import co.djphy.glance.fragments.PresidentRegisterFragment;

/**
 * Created by User on 26-01-2017.
 */

public class NormalLoginActivity extends BaseActivity {

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

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.llContainer)
    LinearLayout llLoginContainer;

    private LoginUserPasswordFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_normal);
        ButterKnife.bind(this);
        transactLoginFragment();
    }


    private FragmentTransaction animateTransaction(FragmentTransaction manager, boolean isRightToLeft) {
        if (isRightToLeft)
            return manager.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_into_left);
        return manager.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }

    private boolean isLoginFragment;

    public void transactLoginFragment(){
        isLoginFragment = true;
        animateTransaction(getSupportFragmentManager().beginTransaction(), true)
                .replace(llLoginContainer.getId(), new LoginUserPasswordFragment()).commit();
    }


    public void transactRegisterFragment(){
        isLoginFragment = false;
        animateTransaction(getSupportFragmentManager().beginTransaction(), true)
                .replace(llLoginContainer.getId(), new PresidentRegisterFragment()).commit();
    }


    @Override
    public void onBackPressed() {
        if (isLoginFragment)
            super.onBackPressed();
        else transactLoginFragment();
    }

    @Override
    protected void onGarbageCollection() {

    }
}
