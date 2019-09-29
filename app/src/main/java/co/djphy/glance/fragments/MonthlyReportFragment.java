package co.djphy.glance.fragments;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.djphy.glance.adapters.VerticalGenericAdapter;

/**
 * Created by DJphy on 10-07-2017.
 */

public class MonthlyReportFragment extends SingleMenuFragment{

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

    }

    @Override
    protected String getFragmentTitle() {
        return "Monthly";
    }
}
