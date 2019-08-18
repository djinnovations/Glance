package co.djphy.glance.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import co.djphy.glance.R;
import co.djphy.glance.activities.BaseActivity;
import co.djphy.glance.MyApplication;
import co.djphy.glance.activities.NormalLoginActivity;
import co.djphy.glance.uiutils.ColoredSnackbar;
import co.djphy.glance.uiutils.UiRandomUtils;

/**
 * Created by DJphy on 09-01-2017.
 */

public class LoginUserPasswordFragment extends PrimaryBaseFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UiRandomUtils.getInstance().boldSomeTxt(signUp, 21, signUp.getText().toString().length());

        btnLoginAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canContinue()) {
                    String shaUserId = /*RandomUtils.textToSHA256(*/etUserName.getText().toString().trim()/*)*/;
                    String shaPass = /*RandomUtils.textToSHA256(*/etPassword.getText().toString().trim()/*)*/;
                    //((BaseActivity) getActivity()).queryForLogin(shaUserId, shaPass);
                    return;
                }
                ColoredSnackbar.alert(Snackbar.make(btnLoginAcct, "Fill all fields", Snackbar.LENGTH_SHORT)).show();
            }
        });

        Runnable runnable = new Runnable() {
            public void run() {
                etUserName.requestFocus();
            }
        };
        MyApplication.getInstance().getUiHandler().postDelayed(runnable, 300);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login_normal;
    }

    @Override
    protected void onGarbageCollection() {

    }

    @Override
    protected String getFragmentTitle() {
        return "";
    }

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLoginAcct)
    Button btnLoginAcct;
    @BindView(R.id.signUp)
    TextView signUp;

    @OnClick(R.id.forgotPasswordButton)
    void onPlayClicked() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).performForgotPassword();
    }

    @OnClick(R.id.signUp)
    void onSignUpClicked() {
        if (getActivity() instanceof NormalLoginActivity)
            ((NormalLoginActivity) getActivity()).transactRegisterFragment();
    }

    private boolean canContinue(){
        return !(TextUtils.isEmpty(etPassword.getText().toString()) || TextUtils.isEmpty(etUserName.getText().toString()));
    }
}
