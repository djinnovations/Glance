package co.djphy.glance.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import co.djphy.glance.R;
import co.djphy.glance.activities.ServicePackageDetailsActivity;
import co.djphy.glance.adapters.VerticalGenericAdapter;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.redundant.BaseFragment;

public class ServicePackageDetailsFragment extends SingleMenuFragment {

    private VerticalGenericAdapter adapter;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDummy();
    }

    private void setDummy(){
        List<HeaderThumbnailData> dataList = new ArrayList<>();
        if (getActivity() instanceof ServicePackageDetailsActivity){
            if(((ServicePackageDetailsActivity) getActivity()).isSubServiceCall()){
                List<Object> stringList = new ArrayList<>();
                stringList.add("REPAIR");
                dataList.add(new HeaderThumbnailData(VerticalGenericAdapter.TYPE_OF_SERVICE,
                        "Select Service Type", stringList));
                stringList = new ArrayList<>();
                stringList.add("INSTALLATION");
                dataList.add(new HeaderThumbnailData(VerticalGenericAdapter.TYPE_OF_SERVICE,
                        "", stringList));
                stringList = new ArrayList<>();
                stringList.add("GAS REFILL");
                dataList.add(new HeaderThumbnailData(VerticalGenericAdapter.TYPE_OF_SERVICE,
                        "", stringList));
            }
        }
        dataList.add(new HeaderThumbnailData(VerticalGenericAdapter.VIEW_PROCEDURE,
                "", getStringList()));
        changeData(dataList);
    }

    private List<Object> getStringList(){
        List<Object> stringList = new ArrayList<>();
        stringList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Cras pulvinar mattis nunc sed blandit libero volutpat sed cras.");
        stringList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        stringList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Euismod quis viverra nibh cras pulvinar. Erat pellentesque adipiscing commodo elit at imperdiet dui.");
        stringList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ullamcorper velit sed ullamcorper morbi tincidunt. Nullam eget felis eget nunc lobortis mattis aliquam faucibus purus. Tincidunt eget nullam non nisi est sit amet facilisis magna.");
        stringList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Cras pulvinar mattis nunc sed blandit libero volutpat sed cras.");
        stringList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        return stringList;
    }

}
