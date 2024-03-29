package co.djphy.glance.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.uiutils.UiRandomUtils;

/**
 * Created by DJphy on 20-12-2016.
 */

public abstract class SingleMenuFragment extends PrimaryBaseFragment {

   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_single, container, false);
    }*/

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_menu_single;
    }

    @Override
    protected String getFragmentTitle() {
        return "";
    }

    public abstract boolean isAddSnapper();

    @BindView(R.id.llBody)
    ViewGroup llBody;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setBodySettings(llBody);
        setUpRecycleView();
    }

    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    private void setUpRecycleView() {
        RecyclerView.LayoutManager mLayoutManager = /*new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false)*/
                getLayoutManager();
        rvMenu.setHasFixedSize(false);
        rvMenu.setLayoutManager(mLayoutManager);
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        if (isAddSnapper())
            UiRandomUtils.getInstance().addSnapper(rvMenu, Gravity.START);
        //mTitlesAdapter = new TitlesAdapter(mainMenuSelectionListener);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter == null)
            return;
        rvMenu.setAdapter(adapter);
    }

    protected void setBodySettings(ViewGroup bodyLayout){

    }

    public abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.Adapter getAdapter();

    public abstract void changeData(List dataList);

}
