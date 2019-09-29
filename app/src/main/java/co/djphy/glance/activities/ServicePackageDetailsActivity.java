package co.djphy.glance.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.fragments.DailyReportFragment;
import co.djphy.glance.fragments.ServicePackageDetailsFragment;
import co.djphy.glance.model.ServicePackageDetails;
import co.djphy.glance.utils.IntentKeys;

public class ServicePackageDetailsActivity extends BaseActivity {


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imgToolbar)
    ImageView imgToolbar;

    private boolean isSubService = false;

    public boolean isSubServiceCall(){
        return isSubService;
    }

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
        return imgToolbar;
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


    @BindView(R.id.flContainer)
    ViewGroup flContainer;
    /*@BindView(R.id.rlMain)
    RelativeLayout rlMain;*/
    @BindView(R.id.cordinatorlayout)
    CoordinatorLayout cordinatorlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_package_service);
        ButterKnife.bind(this);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpCollapsingToolbar();

        getSupportFragmentManager().beginTransaction()
                .replace(flContainer.getId(), new ServicePackageDetailsFragment()).commit();
    }

    private void setUpCollapsingToolbar(){
        ServicePackageDetails packageDetails = getIntent().getParcelableExtra(IntentKeys.KEY_SERVICE_PACKAGE_DATA);
        isSubService = getIntent().getBooleanExtra(IntentKeys.KEY_IS_SUB_SERVICE, false);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        /*int orient = MyApplication.getInstance().getResources().getConfiguration().orientation;
        DisplayProperties displayProperties = DisplayProperties.getInstance(orient);
        ((CoordinatorLayout.LayoutParams) rlMain.getLayoutParams()).topMargin
                = (int) (displayProperties.getYPixelsPerCell() * 26);*/
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
        collapsingToolbar.setCollapsedTitleTypeface(typeface);
        collapsingToolbar.setExpandedTitleTypeface(typeface);
        collapsingToolbar.setTitle(packageDetails.packageName);
    }
}
