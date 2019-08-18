package co.djphy.glance.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import co.djphy.glance.MyApplication;
import co.djphy.glance.activities.PrimaryMainActivity;
import co.djphy.glance.adapters.GenericAdapter;
import co.djphy.glance.adapters.ThumbnailAdapter;
import co.djphy.glance.model.HeaderThumbnailData;

/**
 * Created by User on 03-02-2018.
 */

public class MainFragment extends SingleMenuFragment {

    private GenericAdapter adapter;

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

    private void setDummy(){
        Runnable runnable = new Runnable() {
            public void run() {
                int imax = 1, jmax = 1;

                if (getActivity() instanceof PrimaryMainActivity){
                    imax = 15;
                    jmax = 10;
                }
                List<HeaderThumbnailData> dataList = new ArrayList<>();
                for (int i =0; i < imax; i++){
                    List<HeaderThumbnailData.ThumbnailData> thumbnailDataList = new ArrayList<>();
                    for (int j = 0; j< jmax; j++){
                        if (i == 0){
                            HeaderThumbnailData.ThumbnailData thumbnailData
                                    = new HeaderThumbnailData.ThumbnailData(ThumbnailAdapter.LANDSCAPE, String.valueOf(j),
                                    "http://78.media.tumblr.com/5496c974500f1c6c6047e256c34b8a86/tumblr_mz9posw3YS1ra8ht7o1_500.jpg",
                                    "extra", "0", "item-" + String.valueOf(j));
                            thumbnailDataList.add(thumbnailData);
                        }
                       else {
                            HeaderThumbnailData.ThumbnailData thumbnailData
                                    = new HeaderThumbnailData.ThumbnailData(ThumbnailAdapter.PORTRAIT, String.valueOf(j),
                                    "https://cdn.cinematerial.com/p/297x/zeegh0s4/jumanji-welcome-to-the-jungle-british-movie-poster-md.jpg",
                                    "extra", "0", "item-" + String.valueOf(j));
                            thumbnailDataList.add(thumbnailData);
                        }
                    }
                    dataList.add(new HeaderThumbnailData("header-"+String.valueOf(i), thumbnailDataList));
                }
                changeData(dataList);
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public boolean isAddSnapper() {
        return false;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new GenericAdapter();
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

    // This method will be called when a SomeOtherEvent is posted
    @Subscribe
    public void onEventReceived(Object event) {
        if (event instanceof HeaderThumbnailData.ThumbnailData) {
            HeaderThumbnailData.ThumbnailData data = (HeaderThumbnailData.ThumbnailData) event;
            //((BaseActivity) getActivity()).setInfoMsg(data.getTitle());
            if (getActivity() instanceof PrimaryMainActivity){
                ((PrimaryMainActivity) getActivity()).launchDetailsActivity(data);
            }
        }
    }
}
