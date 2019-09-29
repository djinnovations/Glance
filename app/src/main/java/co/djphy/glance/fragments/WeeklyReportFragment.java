package co.djphy.glance.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.activities.EmptyActivity;
import co.djphy.glance.activities.ReportsActivity;
import co.djphy.glance.adapters.VerticalGenericAdapter;
import co.djphy.glance.model.DailyProgressDetails;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.model.WeeklyProgressDetails;
import co.djphy.glance.model.WeeklyReportDetails;
import co.djphy.glance.utils.IntentKeys;
import co.djphy.glance.utils.ResponseProxy;

/**
 * Created by DJphy on 10-07-2017.
 */

public class WeeklyReportFragment extends SingleMenuFragment{

    VerticalGenericAdapter adapter;

    @Override
    public boolean isAddSnapper() {
        return false;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new VerticalGenericAdapter();
        return adapter;
    }

    @Override
    public void changeData(List dataList) {
        try {
            if (adapter != null)
                adapter.changeData(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onGarbageCollection() {
        adapter = null;
    }

    @Override
    protected String getFragmentTitle() {
        return "Weekly";
    }


    // Called when a day is selected from weeklyreport tab, will launch dailyreportfragment
    @Subscribe
    public void onEventReceived(Object event) {
        WeeklyReportDetails progressDetails = (WeeklyReportDetails) event;
        List<Object> list = progressDetails.getDailyProgressDetails();
        ArrayList<DailyProgressDetails> arrayList = new ArrayList<>();
        for (Object objects: list){
            arrayList.add((DailyProgressDetails) objects);
        }
        Intent intent = new Intent(getActivity(), EmptyActivity.class);
        intent.putExtra(IntentKeys.KEY_FRAGMENT_NAME, DailyReportFragment.class.getName());
        intent.putExtra(IntentKeys.TITLE, progressDetails.day);
        MyApplication.getInstance().setPrimaryDailyReport(arrayList);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Runnable runnable = new Runnable() {
            public void run() {
                setDummy();
            }
        };
        MyApplication.getInstance().getUiHandler().postDelayed(runnable, 50);
    }


    private void setDummy() {
        Runnable runnable = new Runnable() {
            public void run() {
                List<HeaderThumbnailData> headerThumbnailData = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    List<Object>
                            weeklyProgressList = ResponseProxy.getWeeklyProgressDetailsList();
                /*for (Object object: weeklyProgressList){
                    List<Object> list = new ArrayList<>();
                    list.add(object);*/
                    HeaderThumbnailData thumbnailData = new HeaderThumbnailData
                            (VerticalGenericAdapter.HORIZONTAL_VIEW, "20 Sept - 15 Sept", weeklyProgressList);
                    headerThumbnailData.add(thumbnailData);
                    //}
                }
                changeData(headerThumbnailData);
            }
        };
        new Thread(runnable).start();
    }
}
