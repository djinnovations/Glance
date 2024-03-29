package co.djphy.glance.activities;

import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.fragments.MainFragment;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.model.NavigationDataObject;
import co.djphy.glance.utils.MyPrefManager;

/**
 * Created by User on 05-02-2018.
 */

public class PrimaryMainActivity extends TabsBaseActivity {
    @Override
    public ArrayList<Pair<Class, String>> getTabFragmentsList() {
        ArrayList<Pair<Class, String>> list = new ArrayList<>();
        list.add(new Pair<Class, String>(MainFragment.class, "Home"));
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //todo menus selection
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getPageIndicator().getLayoutParams().width = (int) (35 * displayProperties.getXPixelsPerCell());
        getPageIndicator().getLayoutParams().height = (int) (9 * displayProperties.getYPixelsPerCell());
        //MyPrefManager.getInstance().updateSessionCounts();
    }

    @Override
    protected void onGarbageCollection() {
        activePageData = null;
        history = null;
        activePage = null;
    }

    @Override
    protected NavigationDataObject getPrimaryNavigationObj() {
        return MyApplication.getInstance().getNavigationObj(R.id.nav_menu_home);
    }

    @Override
    public boolean isDisableTabIndication() {
        return true;
    }


    public void launchDetailsActivity(HeaderThumbnailData.HorizontalViewData data) {
        /*Intent intent = new Intent(this, VideoDetailsActivity.class);
        DetailsData data1 = new DetailsData(data.getId(), data.getExtraData(),
                data.getThumbnailUrl(), data.getTitle(), "");
        intent.putExtra(IntentKeys.VID_DETAIL_DATA, data1);
        startActivity(intent);*/
        Toast.makeText(getApplicationContext(), "Soon", Toast.LENGTH_SHORT).show();
    }
}
