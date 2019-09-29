package co.djphy.glance.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import co.djphy.glance.MyApplication;
import co.djphy.glance.activities.MainActivity;
import co.djphy.glance.activities.PrimaryMainActivity;
import co.djphy.glance.adapters.VerticalGenericAdapter;
import co.djphy.glance.adapters.HorizontalGenericAdapter;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.model.OwnedPackagesDataObject;
import co.djphy.glance.model.ServicePackageDetails;

/**
 * Created by User on 03-02-2018.
 */

public class MainFragment extends SingleMenuFragment {

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
    public void onResume() {
        super.onResume();
        if (isFirstEntry)
            isFirstEntry = false;
        else
            adapter.notifyIndexChange(0);
    }

    private boolean isFirstEntry;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isFirstEntry = true;
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
                int imax = 4, jmax = 0;

                if (getActivity() instanceof PrimaryMainActivity){
                    imax = 15;
                    jmax = 10;
                }
                List<Object> objectList = ((MainActivity) getActivity()).packageDetailsList;
                List<HeaderThumbnailData> dataList = new ArrayList<>();
                List<Object> horizontalViewDataList = new ArrayList<>();
                List<Object> ownedList = new ArrayList<>();
                for (int i = 0; i< 2; i++){
                    ServicePackageDetails servicePackageDetails = (ServicePackageDetails) objectList.get(i);
                    OwnedPackagesDataObject dataObject = new OwnedPackagesDataObject
                            (HorizontalGenericAdapter.OWNED_PACKAGE, "oct\n16\n2019",
                                    "nov\n01\n2019", servicePackageDetails.packageName,
                                    new ArrayList<>(servicePackageDetails.subPackagesMap.values()));
                    ownedList.add(dataObject);
                }
                /*ownedList.add(new HeaderThumbnailData.HorizontalViewData(HorizontalGenericAdapter
                        .OWNED_PACKAGE, "id1"));
                ownedList.add(new HeaderThumbnailData.HorizontalViewData(HorizontalGenericAdapter
                        .OWNED_PACKAGE, "id2"));*/
                for (int i =0; i < imax; i++){
                    if (i == 0) {
                        dataList.add(new HeaderThumbnailData(VerticalGenericAdapter.HORIZONTAL_VIEW,
                                "", ownedList));
                        dataList.add(new HeaderThumbnailData(VerticalGenericAdapter.CUSTOMIZE_BUTTON,
                                "header-"+String.valueOf(i), horizontalViewDataList));
                    }
                    else if (i > 0){
                        dataList.add(new HeaderThumbnailData(VerticalGenericAdapter.HORIZONTAL_VIEW,
                                "header-"+String.valueOf(i), objectList
                                .subList(jmax, jmax+2)));
                        jmax = jmax+2;
                    }
                }
                changeData(dataList);
            }
        };
        new Thread(runnable).start();
    }


    private List<Object> createServicePackages(){
        List<Object> packageDetails = new ArrayList<>();
        TreeMap<String, String> map = new TreeMap<>();
        map.put("FMSsub01", "Masonry");
        map.put("FMSsub02", "Carpentry Works");
        map.put("FMSsub03", "Flooring Works");
        map.put("FMSsub04", "Fabrication Works");
        map.put("FMSsub05", "Painting Works");
        map.put("FMSsub06", "AC Repairs & installation");
        ServicePackageDetails details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"01", "Facility management services",
                "dummyurl", map);
        packageDetails.add(details);
        TreeMap<String, String> map1 = new TreeMap<>();
        map1.put("PMsub01", "Power Distribution");
        map1.put("PMsub02", "Energy Management");
        map1.put("PMsub03", "Diesel Generators");
        map1.put("PMsub04", "HVAC Systems");
        map1.put("PMsub05", "Transformers etc");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"02", "Power Management",
                "dummyurl", map1);
        packageDetails.add(details);
        TreeMap<String, String> map2 = new TreeMap<>();
        map2.put("IWsub01", "Woodend Partitions");
        map2.put("IWsub02", "Kitchen Cabinets");
        map2.put("IWsub03", "Bedroom cots & study tables");
        map2.put("IWsub04", "TV cabinets");
        map2.put("IWsub05", "False ceiling works, etc");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"03", "Interior Works",
                "dummyurl", map2);
        packageDetails.add(details);

        TreeMap<String, String> map3 = new TreeMap<>();
        map3.put("WMsub01", "Plumbing & sewage systems");
        map3.put("WMsub02", "Sewage treatment & water treatment systems");
        map3.put("WMsub03", "Pumps & pumping system");
        map3.put("WMsub04", "Swimming pool maintenance");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"04", "Water Management",
                "dummyurl", map3);
        packageDetails.add(details);

        TreeMap<String, String> map4 = new TreeMap<>();
        map4.put("PMSsub01", "Property Management");
        map4.put("PMSsub02", "Property Maintenance");
        map4.put("PMSsub03", "Rent Management");
        map4.put("PMSsub04", "Statutory payments");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"05", "Property Management services",
                "dummyurl", map4);
        packageDetails.add(details);

        TreeMap<String, String> map5 = new TreeMap<>();
        map5.put("GCGPIsub01", "Grass cutting");
        map5.put("GCGPIsub02", "Hedge trimming");
        map5.put("GCGPIsub03", "Weed Control");
        map5.put("GCGPIsub04", "Garden clean up");
        map5.put("GCGPIsub05", "Strimmer work");
        map5.put("GCGPIsub06", "All Gas pipe installation & repairs");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"06", "Gardening & Copper Gas pipe installation",
                "dummyurl", map5);
        packageDetails.add(details);
        return packageDetails;
    }

    // This method will be called when a SomeOtherEvent is posted
    @Subscribe
    public void onEventReceived(Object event) {
        if (event instanceof HeaderThumbnailData.HorizontalViewData) {
            HeaderThumbnailData.HorizontalViewData data = (HeaderThumbnailData.HorizontalViewData) event;
            //((BaseActivity) getActivity()).setInfoMsg(data.getTitle());
            if (getActivity() instanceof PrimaryMainActivity){
                ((PrimaryMainActivity) getActivity()).launchDetailsActivity(data);
            }
        }
    }
}
