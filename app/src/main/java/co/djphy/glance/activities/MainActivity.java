package co.djphy.glance.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.customviews.ArrayAdapterSearchView;
import co.djphy.glance.fragments.MainFragment;
import co.djphy.glance.model.ServicePackageDetails;
import co.djphy.glance.modules.customise.MyEntry;
import co.djphy.glance.modules.customise.OptionKey;
import co.djphy.glance.modules.customise.OptionValue;
import co.djphy.glance.modules.customise.ServiceCustomizationViewHolder;
import co.djphy.glance.uiutils.ViewConstructor;
import co.djphy.glance.uiutils.WindowUtils;
import co.djphy.glance.utils.IntentKeys;
import co.djphy.glance.utils.MyPrefManager;
import co.djphy.glance.utils.ResponseProxy;

public class MainActivity extends BaseDrawerActivity {
    private static final String TAG = "MainActivity";

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

    @Override
    protected void onGarbageCollection() {

    }

    @BindView(R.id.rlMain)
    ViewGroup rlMain;
    @BindView(R.id.flFragmentParent)
    ViewGroup flFragmentParent;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEventReceived(Object event) {
        if (event instanceof MaterialButton){
            /*getSupportFragmentManager().beginTransaction().replace(flFragmentParent.getId(),
                    new ServiceCustomizationFragment()).commit();*/
            new ServiceCustomizationHelper().customizationCallOnDialog();
        }
        else if (event instanceof ServicePackageDetails){
            ServicePackageDetails packageDetails = (ServicePackageDetails) event;
            Intent intent = new Intent(this, ServicePackageDetailsActivity.class);
            intent.putExtra(IntentKeys.KEY_SERVICE_PACKAGE_DATA, packageDetails);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createServicePackages();

        setToolbarTitle("Apartment Name");

        MyPrefManager.getInstance().updateSessionCounts();
        getSupportFragmentManager().beginTransaction().replace(flFragmentParent.getId(), new MainFragment()).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //todo menus selection
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        /*MenuItem searchItem = menu.findItem(R.id.nav_my_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager =
                (SearchManager) getSystemService(this.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/
        setUpAutoSearch(menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        promptBeforeExit();
    }

    class ServiceCustomizationHelper{
         void customizationCallOnDialog(){
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_menu_single, null);
            RecyclerView rvMenu = view.findViewById(R.id.rvMenu);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager
                    (getApplicationContext(), RecyclerView.VERTICAL, false);
            rvMenu.setHasFixedSize(false);
            rvMenu.setLayoutManager(mLayoutManager);
            rvMenu.setItemAnimator(new DefaultItemAnimator());
            final CustomizeMainAdapter adapter = new CustomizeMainAdapter();
            rvMenu.setAdapter(adapter);
             Runnable runnable = new Runnable() {
                 public void run() {
                     setDummy(adapter);
                 }
             };
             new Thread(runnable).start();
             ViewConstructor.InfoDisplayListener listener = new ViewConstructor.InfoDisplayListener() {
                @Override
                public void onNegativeSelection(Dialog alertDialog) {

                }

                @Override
                public void onPositiveSelection(Dialog alertDialog) {
                    Toast.makeText(getApplicationContext(), "Sending your custom Request", Toast.LENGTH_SHORT).show();
                }
            };
            //WindowUtils.getInstance().invokeCustomizationDialog(MainActivity.this, view, listener);
             WindowUtils.getInstance().displayBottomSheetDialog(MainActivity.this,
                     "", "Send Request", "Cancel",-1, view, listener);
        }

         void setDummy(final CustomizeMainAdapter adapter) {
            List<Object> servicePackageDetails = new ArrayList<>();
            servicePackageDetails = packageDetailsList;
            final List<Map.Entry<OptionKey, ArrayList<OptionValue>>> options = new ArrayList<>();
            for(Object details: servicePackageDetails){
                ServicePackageDetails servicePackageDetails1 = (ServicePackageDetails) details;
                List<String> values = new ArrayList<>(((ServicePackageDetails) details).subPackagesMap.values());
                ArrayList<OptionValue> optionValueList = new ArrayList<>();
                for (String string: values){
                    OptionValue optionValue = new OptionValue(string);
                    optionValueList.add(optionValue);
                }
                MyEntry<OptionKey, ArrayList<OptionValue>> myEntry = new MyEntry<>
                        (new OptionKey(servicePackageDetails1.packageName), optionValueList);
                options.add(myEntry);
            }
             Runnable runnable = new Runnable() {
                 public void run() {
                     if (adapter != null)
                         adapter.changeData(options);
                 }
             };
            new Handler(Looper.getMainLooper()).post(runnable);
         }


        class CustomizeMainAdapter extends RecyclerView.Adapter<ServiceCustomizationViewHolder>{

            List<Map.Entry<OptionKey, ArrayList<OptionValue>>> options;

            @NonNull
            @Override
            public ServiceCustomizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customize_main, parent, false);
                return new ServiceCustomizationViewHolder(view, null);
            }

            @Override
            public void onBindViewHolder(ServiceCustomizationViewHolder holder, int position) {
                Map.Entry<OptionKey, ArrayList<OptionValue>> option = options.get(position);
                holder.bindUI(option);
            }

            @Override
            public int getItemCount() {
                return options == null ? 0 : options.size();
            }

            public void changeData(List<Map.Entry<OptionKey, ArrayList<OptionValue>>> optionsList) {
                options = optionsList;
                Log.d("changeData ", "" + optionsList.size());
                notifyDataSetChanged();
            }

        }
    }


    ServicePackageDetails servicePackageDetails = null;

    private void setUpAutoSearch(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.nav_my_search);
        final ArrayAdapterSearchView searchView = (ArrayAdapterSearchView) searchItem.getActionView();
        List<String> subs = new ArrayList<>();
        for (Object details: packageDetailsList){
            ServicePackageDetails servicePackageDetails = (ServicePackageDetails) details;
            Map<String, String> map = servicePackageDetails.subPackagesMap;
            subs.addAll(new ArrayList<>(map.values()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, subs);
        searchView.setAdapter(adapter);
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = adapter.getItem(position);
                searchView.setText(select);

                for (Object details: packageDetailsList) {
                    servicePackageDetails = (ServicePackageDetails) details;
                    Map<String, String> map = servicePackageDetails.subPackagesMap;
                    boolean isBroke = false;
                    for (String string: new ArrayList<>(map.values())){
                        if (string.equalsIgnoreCase(select)) {
                            isBroke = true;
                            break;
                        }
                    }
                    if (isBroke)
                        break;
                }

                Runnable runnable = new Runnable() {
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, ServicePackageDetailsActivity.class);
                        intent.putExtra(IntentKeys.KEY_SERVICE_PACKAGE_DATA, servicePackageDetails);
                        intent.putExtra(IntentKeys.KEY_IS_SUB_SERVICE, true);
                        startActivity(intent);
                    }
                };
                new Handler(Looper.getMainLooper()).postDelayed(runnable, 1000);
            }
        });
    }

    public List<Object> packageDetailsList;


    private List<Object> createServicePackages(){
        packageDetailsList = /*new ArrayList<>();
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
        packageDetailsList.add(details);
        TreeMap<String, String> map1 = new TreeMap<>();
        map1.put("PMsub01", "Power Distribution");
        map1.put("PMsub02", "Energy Management");
        map1.put("PMsub03", "Diesel Generators");
        map1.put("PMsub04", "HVAC Systems");
        map1.put("PMsub05", "Transformers etc");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"02", "Power Management",
                "dummyurl", map1);
        packageDetailsList.add(details);
        TreeMap<String, String> map2 = new TreeMap<>();
        map2.put("IWsub01", "Woodend Partitions");
        map2.put("IWsub02", "Kitchen Cabinets");
        map2.put("IWsub03", "Bedroom cots & study tables");
        map2.put("IWsub04", "TV cabinets");
        map2.put("IWsub05", "False ceiling works, etc");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"03", "Interior Works",
                "dummyurl", map2);
        packageDetailsList.add(details);

        TreeMap<String, String> map3 = new TreeMap<>();
        map3.put("WMsub01", "Plumbing & sewage systems");
        map3.put("WMsub02", "Sewage treatment & water treatment systems");
        map3.put("WMsub03", "Pumps & pumping system");
        map3.put("WMsub04", "Swimming pool maintenance");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"04", "Water Management",
                "dummyurl", map3);
        packageDetailsList.add(details);

        TreeMap<String, String> map4 = new TreeMap<>();
        map4.put("PMSsub01", "Property Management");
        map4.put("PMSsub02", "Property Maintenance");
        map4.put("PMSsub03", "Rent Management");
        map4.put("PMSsub04", "Statutory payments");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"05", "Property Management services",
                "dummyurl", map4);
        packageDetailsList.add(details);

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
        packageDetailsList.add(details);*/
                ResponseProxy.getPackageDetailsList();
                ResponseProxy.getDailyProgressList();
        return packageDetailsList;
    }

}
