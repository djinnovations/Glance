package co.djphy.glance.activities;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.model.NavigationDataObject;
import co.djphy.glance.redundant.ApplicationDefaultWebView;
import co.djphy.glance.redundant.BaseFragment;
import co.djphy.glance.utils.IDUtils;

public class WebActivity extends BaseActivity {

    private static final String TAG_CONTACT_US = "ContactUs";
    private static final String TAG_PEOPLE = "People";
    @BindView(R.id.mainHolder)
    FrameLayout mainHolder;

    @Override
    public ProgressBar getProgressBar() {
        return null;
    }

    @Override
    public Activity getSelf() {
        return null;
    }

    @Override
    public View getViewForLayoutAccess() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_web);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String url = getIntent().getExtras().getString("URL");
        if (url == null)
            url = getIntent().getExtras().getString("url");

        String title = getIntent().getExtras().getString("TITLE");

        if (title == null)
            title = url;

        setTitle(title + "");

        FragmentManager fragmentManager = getSupportFragmentManager();
        NavigationDataObject navigationObject = new NavigationDataObject(IDUtils.generateViewId(), ApplicationDefaultWebView.class, title,
                NavigationDataObject.ACTIVITY, url);
        Map<String, String> params = new HashMap<>();
        params.put("URL", url);
        params.put("Title", title);
        navigationObject.setParam(params);


        BaseFragment mActivePage = BaseFragment.newWebViewInstance(navigationObject);
        fragmentManager.beginTransaction().replace(R.id.mainHolder, mActivePage).commit();

    }

    @Override
    protected void onGarbageCollection() {

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
