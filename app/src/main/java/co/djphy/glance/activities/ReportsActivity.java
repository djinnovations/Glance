package co.djphy.glance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import co.djphy.glance.MyApplication;
import co.djphy.glance.fragments.DailyReportFragment;
import co.djphy.glance.fragments.HomeTabFragment;
import co.djphy.glance.fragments.MainFragment;
import co.djphy.glance.fragments.TabFragment2;
import co.djphy.glance.fragments.WeeklyReportFragment;
import co.djphy.glance.model.DailyProgressDetails;
import co.djphy.glance.model.NavigationDataObject;
import co.djphy.glance.model.WeeklyReportDetails;
import co.djphy.glance.utils.IntentKeys;
import co.djphy.glance.utils.ResponseProxy;

public class ReportsActivity extends TabsBaseActivity {

    @Override
    public ArrayList<Pair<Class, String>> getTabFragmentsList() {
        ArrayList<Pair<Class, String>> list = new ArrayList<>();
        list.add(new Pair<Class, String>(DailyReportFragment.class, "Daily"));
        list.add(new Pair<Class, String>(WeeklyReportFragment.class, "Weekly"));
        list.add(new Pair<Class, String>(TabFragment2.class, "Monthly"));
        return list;
    }

    @Override
    protected NavigationDataObject getPrimaryNavigationObj() {
        return new NavigationDataObject(111, HomeTabFragment.class, NavigationDataObject.FRAGMENT_VIEW);
    }

    @Override
    public boolean isDisableTabIndication() {
        return false;
    }

    @Override
    protected void onGarbageCollection() {
        activePageData = null;
        history = null;
        activePage = null;
    }

    @Override
    protected void promptBeforeExit() {
        //super.promptBeforeExit();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getPageIndicator().getLayoutParams().width = (int) (40 * displayProperties.getXPixelsPerCell());
        getPageIndicator().getLayoutParams().height = (int) (9 * displayProperties.getYPixelsPerCell());
        //MyPrefManager.getInstance().updateSessionCounts();
    }


    public void launchEmptyActivity(Object object){

    }
}
