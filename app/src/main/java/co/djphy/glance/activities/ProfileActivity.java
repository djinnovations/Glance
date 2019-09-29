package co.djphy.glance.activities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.customviews.CircularImageView;
import co.djphy.glance.fragments.CancelSubscriptionFragment;
import co.djphy.glance.fragments.ServicePackageDetailsFragment;
import co.djphy.glance.model.CancelSubscriptionDataObject;
import co.djphy.glance.model.ServicePackageDetails;
import co.djphy.glance.uiutils.ViewConstructor;
import co.djphy.glance.uiutils.WindowUtils;
import co.djphy.glance.utils.IntentKeys;

public class ProfileActivity extends BaseActivity{

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
        return toolbar;
    }

    @Override
    protected void onGarbageCollection() {

    }

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.flContainer)
    ViewGroup flContainer;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventReceived(Object event) {
        if (event instanceof CancelSubscriptionDataObject) {
            ViewConstructor.InfoDisplayListener infoDisplayListener = new ViewConstructor.InfoDisplayListener() {
                @Override
                public void onNegativeSelection(Dialog alertDialog) {

                }

                @Override
                public void onPositiveSelection(Dialog alertDialog) {
                    Toast.makeText(getApplicationContext(), "Cancel Request sent", Toast.LENGTH_LONG).show();
                }
            };
            WindowUtils.getInstance().displayBottomSheetGenericMsg(ProfileActivity.this,
                    "Alert", "Are you sure cancel this subscription?",
                    "Yes", "not sure", infoDisplayListener);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpCollapsingToolbar();

        getSupportFragmentManager().beginTransaction()
                .replace(flContainer.getId(), new CancelSubscriptionFragment()).commit();
    }

    @BindView(R.id.userImage)
    CircularImageView userImage;

    private void setUpCollapsingToolbar(){
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        /*int orient = MyApplication.getInstance().getResources().getConfiguration().orientation;
        DisplayProperties displayProperties = DisplayProperties.getInstance(orient);
        ((CoordinatorLayout.LayoutParams) rlMain.getLayoutParams()).topMargin
                = (int) (displayProperties.getYPixelsPerCell() * 26);*/
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
        collapsingToolbar.setCollapsedTitleTypeface(typeface);
        collapsingToolbar.setExpandedTitleTypeface(typeface);
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbar.setTitle("Profile");

        Picasso.with(this).load(R.drawable.vector_icon_profile_white_outline).placeholder(R.drawable
                .vector_icon_profile_white_outline).into(userImage);
    }
}
