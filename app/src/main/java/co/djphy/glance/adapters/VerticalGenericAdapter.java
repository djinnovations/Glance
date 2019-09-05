package co.djphy.glance.adapters;

import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.uiutils.UiRandomUtils;

/**
 * Created by User on 02-02-2018.
 */

public class VerticalGenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GenericAdapterInterface {

    public static final int HORIZONTAL_VIEW = 1990;
    public static final int CUSTOMIZE_BUTTON = 1889;
    //public static final int COURSES_MINE = IDUtils.generateViewId();


    public VerticalGenericAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getRootLayout(viewType), parent, false);
        return getViewHolder(view, viewType);
    }


    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        if (viewType == HORIZONTAL_VIEW)
            return new HeaderHorizontalListViewHolder(view);
        else if (viewType == CUSTOMIZE_BUTTON)
            return new CustomizeButtonViewHolder(view);
        /*else if (viewType == COURSES_MINE)
            return new MyCourseViewHolder(view);*/
        else return null;
    }

    @Override
    public int getRootLayout(int viewType) {
        if (viewType == HORIZONTAL_VIEW)
        return R.layout.viewholder_header_thumbnail;
        else if (viewType == CUSTOMIZE_BUTTON)
            return R.layout.viewholder_customize_button;
        return -1;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseItemHolder) holder).onItemViewUpdate(dataList.get(position), holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private List<HeaderThumbnailData> dataList = new ArrayList<>();

    @Override
    public void changeData(final List dataList) throws Exception {
        if (dataList == null)
            return;
        if (dataList.size() <= 0)
            return;
        /*if (!(dataList.get(0) instanceof HeaderThumbnailData))
            throw new IllegalArgumentException("Required data type \"HeaderThumbnailData\"");*/
        this.dataList.clear();
        this.dataList.addAll(dataList);
        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, 100);
    }

    @Override
    public void setOnClickListener(RecyclerView.ViewHolder holder) {
        if (holder instanceof CustomizeButtonViewHolder)
            ((CustomizeButtonViewHolder) holder).btnCustomize.setOnClickListener((View.OnClickListener) holder);
    }


    class CustomizeButtonViewHolder extends BaseItemHolder implements View.OnClickListener {

        @BindView(R.id.btnCustomize)
        MaterialButton btnCustomize;

        public CustomizeButtonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(itemView.getContext(), "go to details screen", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            /*HeaderThumbnailData data = (HeaderThumbnailData) dataObject;
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(holder.itemView.getContext()).load("").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable drawable = new BitmapDrawable(holder.itemView.getResources(), bitmap);
            btnCustomize.setIcon(drawable);*/
            btnCustomize.setText("Customize Services");
            //// TODO: 05-09-2019 enabled when api ready
        }
    }

    class HeaderHorizontalListViewHolder extends BaseItemHolder implements View.OnClickListener {

        @BindView(R.id.rvMenu)
        RecyclerView rvMenu;
        @BindView(R.id.tvHeader)
        TextView tvHeader;

        private HorizontalGenericAdapter adapter;

        public HeaderHorizontalListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //setOnClickListener(this);
            setUpRecycleView();
        }

        private void setUpRecycleView() {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            rvMenu.setHasFixedSize(false);
            rvMenu.setLayoutManager(mLayoutManager);
            rvMenu.setItemAnimator(new DefaultItemAnimator());
            UiRandomUtils.getInstance().addSnapper(rvMenu, Gravity.START);
            rvMenu.setAdapter(adapter = new HorizontalGenericAdapter());
        }

        @Override
        public void onClick(View view) {
            //EventBus.getDefault().post(datalist);
        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            try {
                HeaderThumbnailData data = (HeaderThumbnailData) dataObject;
                tvHeader.setText(data.getHeaderTitle());
                adapter.changeData(data.getDataList());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
