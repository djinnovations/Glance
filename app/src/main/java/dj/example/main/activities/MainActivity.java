package dj.example.main.activities;

import android.os.Bundle;
import android.util.Pair;

import java.util.ArrayList;

import dj.example.main.R;
import dj.example.main.fragments.TabFragment1;
import dj.example.main.fragments.TabFragment2;
import dj.example.main.model.NavigationDataObject;
import dj.example.main.utils.MyPrefManager;

public class MainActivity extends TabsBaseActivity {

    @Override
    public ArrayList<Pair<Class, String>> getTabFragmentsList() {
        ArrayList<Pair<Class, String>> list = new ArrayList<>();
        list.add(new Pair<Class, String>(TabFragment1.class, "Tab - 1"));
        list.add(new Pair<Class, String>(TabFragment2.class, "Tab - 2"));
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        MyPrefManager.getInstance().updateSessionCounts();
    }

    @Override
    protected void onGarbageCollection() {
        activePageData = null;
        history = null;
        activePage = null;
    }

    @Override
    protected NavigationDataObject getPrimaryNavigationObj() {
        return MyApplication.getInstance().getNavigationObj(R.id.nav_home);
    }
}
