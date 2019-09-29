package co.djphy.glance.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.adapters.VerticalGenericAdapter;
import co.djphy.glance.model.DailyProgressDetails;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.utils.IntentKeys;
import co.djphy.glance.utils.ResponseProxy;

/**
 * Created by DJphy on 10-07-2017.
 */

public class DailyReportFragment extends SingleMenuFragment {

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
        return "Daily";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*if (getArguments() != null)
            dailyProgressDetailsListPrimary = new ArrayList<>(getArguments()
                    .getParcelableArrayList(IntentKeys.KEY_DAILY_REPORT_DATA))*/
        dailyProgressDetailsListPrimary = MyApplication.getInstance().getPrimaryDailyReport();

        Runnable runnable = new Runnable() {
            public void run() {
                setDummy();
            }
        };
        MyApplication.getInstance().getUiHandler().postDelayed(runnable, 50);
    }

    List<Object> dailyProgressDetailsListPrimary = new ArrayList<>();

    private void setDummy() {
        Runnable runnable = new Runnable() {
            public void run() {
                List<Object> dailyProgressDetailsList;
                if (dailyProgressDetailsListPrimary.size() <= 0)
                    dailyProgressDetailsList = ResponseProxy.getDailyProgressList();
                else dailyProgressDetailsList = dailyProgressDetailsListPrimary;
                List<HeaderThumbnailData> headerThumbnailData = new ArrayList<>();
                for (Object object : dailyProgressDetailsList) {
                    List<Object> list = new ArrayList<>();
                    list.add(object);
                    HeaderThumbnailData thumbnailData = new HeaderThumbnailData
                            (VerticalGenericAdapter.REPORT_DAILY, "", list);
                    headerThumbnailData.add(thumbnailData);
                }
                changeData(headerThumbnailData);
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void onDestroyView() {
        MyApplication.getInstance().setPrimaryDailyReport(new ArrayList<>());
        super.onDestroyView();
    }
}
