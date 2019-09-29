package co.djphy.glance.adapters;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial;
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.djphy.glance.R;
import co.djphy.glance.model.DailyProgressDetails;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.modules.customise.OptionValue;
import co.djphy.glance.uiutils.ResourceReader;
import co.djphy.glance.uiutils.UiRandomUtils;

/**
 * Created by User on 02-02-2018.
 */

public class VerticalGenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GenericAdapterInterface {

    public static final int HORIZONTAL_VIEW = 1990;
    public static final int CUSTOMIZE_BUTTON = 1889;
    public static final int VIEW_PROCEDURE = 1888;
    public static final int TYPE_OF_SERVICE = 1887;
    public static final int REPORT_DAILY = 1886;

    private List<HeaderThumbnailData> dataList = new ArrayList<>();

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
        else if (viewType == VIEW_PROCEDURE)
            return new ViewProcedureViewHolder(view);
        else if (viewType == TYPE_OF_SERVICE)
            return new SelectServicesViewholder(view);
        else if (viewType == REPORT_DAILY)
            return new DailyReportViewHolder(view);
        else return null;
    }

    @Override
    public int getRootLayout(int viewType) {
        if (viewType == HORIZONTAL_VIEW)
            return R.layout.viewholder_header_thumbnail;
        else if (viewType == CUSTOMIZE_BUTTON)
            return R.layout.viewholder_customize_button;
        else if (viewType == VIEW_PROCEDURE)
            return R.layout.viewholder_view_procedure;
        else if (viewType == TYPE_OF_SERVICE)
            return R.layout.viewholder_services_select;
        else if (viewType == REPORT_DAILY)
            return R.layout.viewholder_daily_report;
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

    public void notifyIndexChange(int index){
        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemChanged(index);
            }
        }, 100);
    }

    @Override
    public void setOnClickListener(RecyclerView.ViewHolder holder) {
        if (holder instanceof SelectServicesViewholder)
            ((SelectServicesViewholder) holder).tvServices.setOnClickListener((View.OnClickListener) holder);

        if (holder instanceof CustomizeButtonViewHolder)
            ((CustomizeButtonViewHolder) holder).btnCustomize.setOnClickListener((View.OnClickListener) holder);
    }


    class DailyReportViewHolder extends BaseItemHolder {

        @BindView(R.id.tvServicesName)
        TextView tvServicesName;
        @BindView(R.id.tvSubServicesName)
        TextView tvSubServicesName;
        @BindView(R.id.ivDropdown)
        ImageView ivDropdown;
        @BindView(R.id.ivEmoticon)
        ImageView ivEmoticon;
        @BindView(R.id.extra_layout)
        ViewGroup extra_layout;
        @BindView(R.id.llServiceNameHolder)
        ViewGroup llServiceNameHolder;

        DailyProgressDetails data;
        ResourceReader resourceReader;
        List<DailyProgressDetails.ChecklistData> subPackagesChecklistMapValues;
        IconicsDrawable happyDrawable;
        IconicsDrawable sadDrawable;

        public DailyReportViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            resourceReader = ResourceReader.getInstance();
            IconicsDrawable expand_more = new IconicsDrawable(extra_layout.getContext())
                    .icon(GoogleMaterial.Icon.gmd_expand_more)
                    .color(com.mikepenz.iconics.IconicsColor
                            .colorInt(resourceReader
                                    .getColorFromResource(R.color.colorMaterialBlack)));
            IconicsDrawable expand_less = new IconicsDrawable(extra_layout.getContext())
                    .icon(GoogleMaterial.Icon.gmd_expand_less)
                    .color(com.mikepenz.iconics.IconicsColor
                            .colorInt(resourceReader
                                    .getColorFromResource(R.color.colorMaterialBlack)));
            happyDrawable = new IconicsDrawable(extra_layout.getContext())
                    .icon(CommunityMaterial.Icon.cmd_emoticon)
                    .color(com.mikepenz.iconics.IconicsColor
                            .colorInt(resourceReader
                                    .getColorFromResource(R.color.darkgreen)));
            sadDrawable = new IconicsDrawable(extra_layout.getContext())
                    .icon(CommunityMaterial.Icon.cmd_emoticon_sad)
                    .color(com.mikepenz.iconics.IconicsColor
                            .colorInt(resourceReader
                                    .getColorFromResource(R.color.darkred)));

            ivDropdown.setImageDrawable(expand_more);
            ivEmoticon.setImageDrawable(happyDrawable);
            ivDropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (extra_layout.getVisibility() == View.VISIBLE) {
                        //addRemoveButton.setImageResource(R.drawable.add);
                        ivDropdown.setImageDrawable(expand_more);
                        extra_layout.setVisibility(View.GONE);
                    } else {
                        //addRemoveButton.setImageResource(R.drawable.close);
                        ivDropdown.setImageDrawable(expand_less);
                        extra_layout.setVisibility(View.VISIBLE);
                        populateExtraLayout();
                    }
                }
            });
        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            HeaderThumbnailData thumbnailData = (HeaderThumbnailData) dataObject;
            data = (DailyProgressDetails) thumbnailData.getDataList().get(0);
            TreeMap<String, DailyProgressDetails.ChecklistData> subPackagesChecklistMap = data.subPackagesChecklistMap;
            subPackagesChecklistMapValues = new ArrayList<>(subPackagesChecklistMap.values());
            if (TextUtils.isEmpty(data.packageName)) {
                llServiceNameHolder.setVisibility(View.GONE);
            }
            else {
                llServiceNameHolder.setVisibility(View.VISIBLE);
                tvServicesName.setText(data.packageName);
                if (!data.isOverAllPackageComplete){
                    ivEmoticon.setImageDrawable(sadDrawable);
                }
            }
            tvSubServicesName.setText(data.subPackageName);
        }

        private void populateExtraLayout() {
            //TreeMap<String, DailyProgressDetails.ChecklistData> subPackagesChecklistMap = data.subPackagesChecklistMap;
            //List<DailyProgressDetails.ChecklistData> list = new ArrayList<>(subPackagesChecklistMap.values());
            for (int i = 0; i < subPackagesChecklistMapValues.size(); i++) {
                DailyProgressDetails.ChecklistData checklistData = subPackagesChecklistMapValues.get(i);
                View child = extra_layout.getChildAt(i);
                View rootView = LayoutInflater.from(extra_layout.getContext())
                        .inflate(R.layout.viewholder_checklist, extra_layout, false);
                rootView.setTag(checklistData);
                TextView tvTaskName = rootView.findViewById(R.id.tvTaskName);
                ImageView ivDoneOrNo = rootView.findViewById(R.id.ivDoneOrNo);
                ViewGroup viewGroup = rootView.findViewById(R.id.llBubble);
                TextView tvComment = rootView.findViewById(R.id.tvComment);
                tvTaskName.setText(checklistData.taskName);
                if (checklistData.isComplete) {
                    viewGroup.setVisibility(View.GONE);
                    IconicsDrawable drawable = new IconicsDrawable(extra_layout.getContext())
                            .icon(GoogleMaterial.Icon.gmd_beenhere)
                            .color(com.mikepenz.iconics.IconicsColor
                                    .colorInt(resourceReader
                                            .getColorFromResource(R.color.darkgreen)));
                    ivDoneOrNo.setImageDrawable(drawable);
                } else {
                    viewGroup.setVisibility(View.VISIBLE);
                    IconicsDrawable drawable = new IconicsDrawable(extra_layout.getContext())
                            .icon(GoogleMaterial.Icon.gmd_highlight_off)
                            .color(com.mikepenz.iconics.IconicsColor
                                    .colorInt(resourceReader
                                            .getColorFromResource(R.color.darkred)));
                    ivDoneOrNo.setImageDrawable(drawable);
                    tvComment.setText(checklistData.comment);
                }

                rootView.setVisibility(View.VISIBLE);
                if (child == null) {
                    extra_layout.addView(rootView);
                }
            }
            if (extra_layout.getChildCount() > subPackagesChecklistMapValues.size()) {
                for (int i = subPackagesChecklistMapValues.size(); i < extra_layout.getChildCount(); i++) {
                    extra_layout.getChildAt(i).setVisibility(View.GONE);
                }
            }
        }

    }

    class SelectServicesViewholder extends BaseItemHolder implements View.OnClickListener {

        @BindView(R.id.tvServices)
        TextView tvServices;
        @BindView(R.id.tvHeader)
        TextView tvHeader;

        public SelectServicesViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            HeaderThumbnailData thumbnailData = (HeaderThumbnailData) dataObject;
            if (TextUtils.isEmpty(thumbnailData.getHeaderTitle())) {
                tvHeader.setVisibility(View.GONE);
            } else {
                tvHeader.setVisibility(View.VISIBLE);
                tvHeader.setText(thumbnailData.getHeaderTitle());
            }
            List<Object> stringList = thumbnailData.getDataList();
            tvServices.setText((String) stringList.get(0));
        }
    }

    class ViewProcedureViewHolder extends BaseItemHolder implements View.OnClickListener {

        @BindView(R.id.tvDescription)
        TextView tvDescription;

        public ViewProcedureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            HeaderThumbnailData thumbnailData = (HeaderThumbnailData) dataObject;
            List<Object> stringList = thumbnailData.getDataList();
            StringBuilder builder = new StringBuilder();
            for (Object str : stringList) {
                builder.append("\u25CF Bullet").append(str).append("\n");
            }
            tvDescription.setText(builder.toString());
        }
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
            EventBus.getDefault().post(btnCustomize);
            //Toast.makeText(itemView.getContext(), "go to details screen", Toast.LENGTH_SHORT).show();
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
