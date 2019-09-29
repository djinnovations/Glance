package co.djphy.glance.modules.customise;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.activities.MainActivity;
import co.djphy.glance.fragments.SingleMenuFragment;
import co.djphy.glance.model.QRTdataObject;
import co.djphy.glance.model.ServicePackageDetails;
import co.djphy.glance.utils.ResponseProxy;

public class ServiceCustomizationFragment extends SingleMenuFragment {

    CustomizeMainAdapter adapter;

    @Override
    public boolean isAddSnapper() {
        return false;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_team_response_quick;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new CustomizeMainAdapter();
        return adapter;
    }

    public Set<OptionValue> getPrimarySelection(){
        return adapter.primarySelected;
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


    @BindView(R.id.etIssue)
    EditText etIssue;
    @BindView(R.id.etphNum)
    EditText etphNum;
    @BindView(R.id.tvQRT)
    TextView tvQRT;

    @OnClick(R.id.tvQRT)
    void onClickQRT(){
        String issue = etIssue.getText().toString();
        String ph = etphNum.getText().toString();
        Set<OptionValue> primarySelected = adapter.primarySelected;
        QRTdataObject qrTdataObject = new QRTdataObject(issue, ph, primarySelected);
        Toast.makeText(getActivity().getApplicationContext(),
                "selected services: "+getString(primarySelected), Toast.LENGTH_LONG).show();
    }

    private String getString(Set<OptionValue> primarySelected){
        StringBuilder builder = new StringBuilder();
        for (OptionValue optionValue: primarySelected){
            builder.append(optionValue.getDisplayString()+",");
        }
        return builder.toString();
    }

    private void setDummy() {
        List<Object> servicePackageDetails = new ArrayList<>();
        if (getActivity() instanceof MainActivity) {
            servicePackageDetails = ((MainActivity) getActivity()).packageDetailsList;
        } else servicePackageDetails = ResponseProxy.getPackageDetailsList();
        List<Map.Entry<OptionKey, ArrayList<OptionValue>>> options = new ArrayList<>();
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
        changeData(options);
    }


    class CustomizeMainAdapter extends RecyclerView.Adapter<ServiceCustomizationViewHolder>{

        List<Map.Entry<OptionKey, ArrayList<OptionValue>>> options;
        private Set<OptionValue> primarySelected = new HashSet<>();

        public CustomizeMainAdapter() {
            menuSelectionListener = new MyApplication.MenuSelectionListener() {
                @Override
                public void onMenuSelected(Object data) {
                    primarySelected.addAll((Collection<? extends OptionValue>) data);
                }
            };
        }

        public Set<OptionValue> getSelectedList(){
            return new HashSet<>(primarySelected);
        }

        MyApplication.MenuSelectionListener menuSelectionListener;

        @NonNull
        @Override
        public ServiceCustomizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customize_main, parent, false);
            return new ServiceCustomizationViewHolder(view, menuSelectionListener);
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
