package co.djphy.glance.activities;

import android.graphics.drawable.LayerDrawable;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.model.NavigationDataObject;
import co.djphy.glance.uiutils.DisplayProperties;

/**
 * Created by DJphy
 */
public abstract class BaseDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.vNavigation)
    NavigationView vNavigation;
    Toolbar toolbar;

    protected DisplayProperties displayProperties;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        ButterKnife.bind(this);
        int orient = this.getResources().getConfiguration().orientation;
        displayProperties = DisplayProperties.getInstance(orient);
        setupToolbar();
        bindViews();
        setupHeader();
    }

    @OnClick({R.id.nav_menu_home,R.id.nav_menu_report, R.id.nav_menu_qrt,
            R.id.nav_menu_privacy_policy, R.id.nav_menu_contact, R.id.nav_menu_profile,
            R.id.nav_menu_settings, R.id.nav_menu_about, R.id.nav_menu_logout})
    public void menuButtonClick(View view) {
        int id = view.getId();
        menuAction(id);
    }

    public void menuAction(int id) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);

        NavigationDataObject navigationDataObject = MyApplication.getInstance().getNavigationObj(id);
        if (navigationDataObject != null) {
            action(navigationDataObject);
        }

        /*if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);*/
    }

    private final String TAG = "BaseDrawerActivity";

    @Override
    public void serverCallEnds(int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried- "+TAG+": " + url);
        Log.d(TAG, "response- "+TAG+": " + json);
        stopProgress();
        super.serverCallEnds(id, url, json, status);
    }

    static LayerDrawable icon;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    protected void bindViews() {

    }

    boolean isFirstTime = true;

    protected void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }


    protected void setToolbarTitle(String string){
        TextView toolbarTitle = (TextView)toolbar.findViewById(R.id.title);
        toolbarTitle.setText(string);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        boolean returnVal = false;
        NavigationDataObject navigationDataObject = (NavigationDataObject) MyApplication.getInstance().getNavigationObj(id);
        if (navigationDataObject != null)
            returnVal = action(navigationDataObject);
        drawerLayout.closeDrawer(GravityCompat.START);
        return returnVal;
    }

    protected boolean isPointsUpdateInprogress = false;

    private void setupHeader() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        vNavigation.setNavigationItemSelectedListener(this);
        View headerLayout = vNavigation.getHeaderView(0);
        //// TODO: 09-07-2017  load name and user images here in silder view 
        TextView userName = (TextView) headerLayout.findViewById(R.id.userName);
        userName.setText("DJphy");
        ImageView userImage = (ImageView) headerLayout.findViewById(R.id.userImage);
        Picasso.with(this).load(R.drawable.vector_icon_profile_white_outline).placeholder(R.drawable
                .vector_icon_profile_white_outline).into(userImage);
        //User user = getApp().getUser();
        /*if (user != null) {
            userName.setText(user.getName());
            ImageView userImage = (ImageView) headerLayout.findViewById(R.id.userImage);
            userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationDataObject navigationDataObject = (NavigationDataObject) getApp().getMainMenu().get(R.id.nav_my_profile);
                    if (navigationDataObject != null) {
                        action(navigationDataObject);
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
            });
            Picasso.with(this).load(user.getImageUrl()).placeholder(R.drawable
                    .vector_image_place_holder_profile_dark).into(userImage);
        }*/

    }

}
