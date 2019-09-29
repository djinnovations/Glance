package co.djphy.glance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.fragments.DailyReportFragment;
import co.djphy.glance.model.DailyProgressDetails;
import co.djphy.glance.utils.IntentKeys;

public class EmptyActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.container)
    ViewGroup container;

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
        return container;
    }

    @Override
    protected void onGarbageCollection() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String fragmentName = intent.getStringExtra(IntentKeys.KEY_FRAGMENT_NAME);
        String title = intent.getStringExtra(IntentKeys.TITLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (TextUtils.isEmpty(title))
            title = "";
        setTitle(title);
        try {
            Fragment fragment = (Fragment) Class.forName(fragmentName).getConstructor().newInstance();//no args  needed for fragment constructor
            getSupportFragmentManager().beginTransaction().replace(container.getId(), fragment).commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*if (fragmentName.equalsIgnoreCase(DailyReportFragment.class.getSimpleName())) {
            DailyReportFragment reportFragment = new DailyReportFragment();
            getSupportFragmentManager().beginTransaction().replace(container.getId(), reportFragment).commit();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
