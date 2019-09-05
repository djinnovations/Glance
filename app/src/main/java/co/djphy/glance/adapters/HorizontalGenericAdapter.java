package co.djphy.glance.adapters;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.MyApplication;
import co.djphy.glance.R;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.model.ServicePackageDetails;
import co.djphy.glance.uiutils.DisplayProperties;
import co.djphy.glance.uiutils.ResourceReader;

/**
 * Created by User on 02-02-2018.
 */

public class HorizontalGenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GenericAdapterInterface {


    public static final int PORTRAIT = 1001;
    public static final int LANDSCAPE = 1002;
    public static final int OWNED_PACKAGE = 2019;
    public static final int AVAILABLE_PACKAGES = 2018;

    private RecyclerView parentView;
    private DisplayProperties displayProperties;
    private static int[] colorArr = new int[]{R.drawable.blue_256, R.drawable.red_256,
            R.drawable.green_256, R.drawable.purple_256};

    public HorizontalGenericAdapter(){
        int orient = MyApplication.getInstance().getResources().getConfiguration().orientation;
        displayProperties = DisplayProperties.getInstance(orient);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentView = (RecyclerView) parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(getRootLayout(viewType), parent, false);
        RecyclerView.ViewHolder viewHolder = getViewHolder(view, viewType);
        try {
             if (viewType == AVAILABLE_PACKAGES) {
                int mImageWidth = (int) (displayProperties.getXPixelsPerCell() * 40);
                int mImageHeight = (int) (displayProperties.getYPixelsPerCell() * 25);
                ServicePackagesViewHolder temp = (ServicePackagesViewHolder) viewHolder;
                ViewGroup.LayoutParams viewGroup = temp.rvHolder.getLayoutParams();
                viewGroup.width = mImageWidth;
                viewGroup.height = mImageHeight;
                temp.ivThumbnail.setAdjustViewBounds(false);
                temp.ivThumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return viewHolder;
    }

    List<Object> dataList = new ArrayList<>();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseItemHolder) holder).onItemViewUpdate(dataList.get(position), holder, position);
    }

    @Override
    public int getRootLayout(int viewType) {
        if (viewType == PORTRAIT)
            return R.layout.viewholder_thumbnail;
        else if(viewType == LANDSCAPE)
            return R.layout.viewholder_wide_thumbnail;
        else if (viewType == OWNED_PACKAGE)
            return -1;
        else if (viewType == AVAILABLE_PACKAGES)
            return R.layout.viewholder_service_packages;
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof ServicePackageDetails) {
            return ((ServicePackageDetails) dataList.get(position)).itemViewType;
        }
        if (dataList.get(position) instanceof HeaderThumbnailData.HorizontalViewData){
            return ((HeaderThumbnailData.HorizontalViewData) dataList.get(position)).getViewType();
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        if (viewType == AVAILABLE_PACKAGES)
            return new ServicePackagesViewHolder(view);
        return new ThumbnailViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void changeData(List dataList) throws Exception {
        if (dataList == null)
            return;
        if (dataList.size() <= 0)
            return;
        /*if (!(dataList.get(0) instanceof HeaderThumbnailData.HorizontalViewData))
            throw new IllegalArgumentException("Required data type \"HeaderThumbnailData.HorizontalViewData\"");*/
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
        if (holder instanceof ServicePackagesViewHolder)
            holder.itemView.setOnClickListener((View.OnClickListener) holder);
        else ((ThumbnailViewHolder) holder).ivThumbnail.setOnClickListener((View.OnClickListener) holder);
    }

    class ServicePackagesViewHolder extends BaseItemHolder implements View.OnClickListener{


        @BindView(R.id.rvHolder)
        RelativeLayout rvHolder;
        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;


        public ServicePackagesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setOnClickListener(this);
            //tvDescription.setTypeface(Typeface.);
        }

        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(dataList.get(getAdapterPosition()));
        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            ServicePackageDetails details = (ServicePackageDetails) dataObject;
            tvTitle.setText(details.packageName);
            Map<String, String> map = details.subPackagesMap;
            List<String> subs = new ArrayList<>(map.values());
            StringBuilder builder = new StringBuilder();
            for (String str: subs) {
                builder.append(str).append("\n");
            }
            tvDescription.setText(builder.toString());
            int rnd = new Random().nextInt(colorArr.length);
            ivThumbnail.setImageDrawable(ResourceReader.getInstance()
                    .getDrawableFromResId(colorArr[rnd]));
        }
    }

    class ThumbnailViewHolder extends BaseItemHolder implements View.OnClickListener{

        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @Nullable
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public ThumbnailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(dataList.get(getAdapterPosition()));
        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            HeaderThumbnailData.HorizontalViewData data = (HeaderThumbnailData.HorizontalViewData) dataObject;
            if (tvTitle != null){
                if (!TextUtils.isEmpty(data.getTitle())) {
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText(data.getTitle());
                }
                else tvTitle.setVisibility(View.INVISIBLE);
            }
            if (HorizontalGenericAdapter.this.getItemViewType(position) == LANDSCAPE){
                ivThumbnail.setImageDrawable(new ColorDrawable(ResourceReader
                        .getInstance().getColorFromResource(R.color.darkorange)));
                return;
            }
            if (!TextUtils.isEmpty(data.getThumbnailUrl())){
                Picasso.with(itemView.getContext())
                        .load(data.getThumbnailUrl())
                        /*.placeholder(R.drawable.vector_icon_profile_white_outline)*/
                        .into(ivThumbnail);
            }
        }
    }
}
