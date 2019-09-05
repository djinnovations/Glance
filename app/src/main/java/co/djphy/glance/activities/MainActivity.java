package co.djphy.glance.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.fragments.MainFragment;
import co.djphy.glance.utils.MyPrefManager;

public class MainActivity extends BaseDrawerActivity {
    private static final String TAG = "MainActivity";

    /*@Override
    public ArrayList<Pair<Class, String>> getTabFragmentsList() {
        ArrayList<Pair<Class, String>> list = new ArrayList<>();
        list.add(new Pair<Class, String>(TabFragment1.class, "Tab - 1"));
        list.add(new Pair<Class, String>(TabFragment2.class, "Tab - 2"));
        return list;
    }*/

    @Override
    public ProgressBar getProgressBar() {
        return new ProgressBar(this);
    }

    @Override
    public Activity getSelf() {
        return this;
    }

    @Override
    public View getViewForLayoutAccess() {
        return rlMain;
    }

    @BindView(R.id.rlMain)
    ViewGroup rlMain;
    @BindView(R.id.flFragmentParent)
    ViewGroup flFragmentParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbarTitle("Apartment Name");

        MyPrefManager.getInstance().updateSessionCounts();
        getSupportFragmentManager().beginTransaction().replace(flFragmentParent.getId(), new MainFragment()).commit();
    }

    @Override
    protected void onGarbageCollection() {

    }

    /*@Override
    protected void onGarbageCollection() {
        activePageData = null;
        history = null;
        activePage = null;
    }*/

    /*@Override
    protected NavigationDataObject getPrimaryNavigationObj() {
        return MyApplication.getInstance().getNavigationObj(R.id.nav_home);
    }*/
}
