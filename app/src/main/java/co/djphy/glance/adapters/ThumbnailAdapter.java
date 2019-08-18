package co.djphy.glance.adapters;

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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.uiutils.ResourceReader;
import co.djphy.glance.utils.IDUtils;

/**
 * Created by User on 02-02-2018.
 */

public class ThumbnailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GenericAdapterInterface {


    public static final int PORTRAIT = IDUtils.generateViewId();
    public static final int LANDSCAPE = IDUtils.generateViewId();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getRootLayout(viewType), parent, false);
        return getViewHolder(view, viewType);
    }

    public ThumbnailAdapter(){

    }

    List<HeaderThumbnailData.ThumbnailData> dataList = new ArrayList<>();

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

    @Override
    public void changeData(List dataList) throws Exception {
        if (dataList == null)
            return;
        if (dataList.size() <= 0)
            return;
        if (!(dataList.get(0) instanceof HeaderThumbnailData.ThumbnailData))
            throw new IllegalArgumentException("Required data type \"HeaderThumbnailData.ThumbnailData\"");
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
    public int getRootLayout(int viewType) {
        if (viewType == PORTRAIT)
            return R.layout.viewholder_thumbnail;
        else if(viewType == LANDSCAPE)
            return R.layout.viewholder_wide_thumbnail;
        return 0;
    }

    @Override
    public void setOnClickListener(RecyclerView.ViewHolder holder) {
        ((ThumbnailViewHolder) holder).ivThumbnail.setOnClickListener((View.OnClickListener) holder);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        return new ThumbnailViewHolder(view);
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
            HeaderThumbnailData.ThumbnailData data = (HeaderThumbnailData.ThumbnailData) dataObject;
            if (tvTitle != null){
                if (!TextUtils.isEmpty(data.getTitle())) {
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText(data.getTitle());
                }
                else tvTitle.setVisibility(View.INVISIBLE);
            }
            if (ThumbnailAdapter.this.getItemViewType(position) == LANDSCAPE){
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
