package co.djphy.glance.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.activities.ProfileActivity;
import co.djphy.glance.adapters.HorizontalGenericAdapter;
import co.djphy.glance.model.CancelSubscriptionDataObject;
import co.djphy.glance.uiutils.ViewConstructor;
import co.djphy.glance.uiutils.WindowUtils;

public class CancelSubscriptionFragment extends SingleMenuFragment {

    HorizontalGenericAdapter adapter;

    @Override
    public boolean isAddSnapper() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        //return super.getLayoutResId();
        return R.layout.fragment_menu_single_textview;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new HorizontalGenericAdapter();
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
        Runnable runnable = new Runnable() {
            public void run() {
                setDummy();
            }
        };
        MyApplication.getInstance().getUiHandler().postDelayed(runnable, 50);
    }

    private void setDummy() {
        List<CancelSubscriptionDataObject> subscriptionDataObjectList = new ArrayList<>();
        CancelSubscriptionDataObject dataObject = new CancelSubscriptionDataObject(
                HorizontalGenericAdapter.CANCEL_SUBSCRIPTION,
                "Facility Management Services", "FCM01");
        subscriptionDataObjectList.add(dataObject);
        dataObject = new CancelSubscriptionDataObject(
                HorizontalGenericAdapter.CANCEL_SUBSCRIPTION,
                "Water Management", "WM04");
        subscriptionDataObjectList.add(dataObject);
        dataObject = new CancelSubscriptionDataObject(
                HorizontalGenericAdapter.CANCEL_SUBSCRIPTION,
                "Property Management Services", "WM05");
        subscriptionDataObjectList.add(dataObject);
        dataObject = new CancelSubscriptionDataObject(
                HorizontalGenericAdapter.CANCEL_SUBSCRIPTION,
                "Interior Works", "WM03");
        subscriptionDataObjectList.add(dataObject);
        changeData(subscriptionDataObjectList);
    }


    @Subscribe
    public void onEventReceived(Object event) {
        if (event instanceof CancelSubscriptionDataObject) {
            ViewConstructor.InfoDisplayListener infoDisplayListener = new ViewConstructor.InfoDisplayListener() {
                @Override
                public void onNegativeSelection(Dialog alertDialog) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Cancel Request sent", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPositiveSelection(Dialog alertDialog) {

                }
            };
            WindowUtils.getInstance().displayBottomSheetGenericMsg(getActivity(),
                    "Alert", "Are you sure to Cancel this Subscription?",
                    "not sure", "Yes", infoDisplayListener);
        }
    }

}
