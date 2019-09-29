package co.djphy.glance.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial;
import com.rey.material.widget.CircleCheckedTextView;
import com.sackcentury.shinebuttonlib.ShineButton;
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
import co.djphy.glance.model.CancelSubscriptionDataObject;
import co.djphy.glance.model.HeaderThumbnailData;
import co.djphy.glance.model.OwnedPackagesDataObject;
import co.djphy.glance.model.ServicePackageDetails;
import co.djphy.glance.model.WeeklyProgressDetails;
import co.djphy.glance.model.WeeklyReportDetails;
import co.djphy.glance.uiutils.DisplayProperties;
import co.djphy.glance.uiutils.ResourceReader;
import jp.wasabeef.blurry.Blurry;

/**
 * Created by User on 02-02-2018.
 */

public class HorizontalGenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GenericAdapterInterface {


    public static final int PORTRAIT = 1001;
    public static final int LANDSCAPE = 1002;
    public static final int OWNED_PACKAGE = 2019;
    public static final int AVAILABLE_PACKAGES = 2018;
    public static final int WEEKLY_REPORT = 2017;
    public static final int WEEKLY_DATE = 2016;
    public static final int CANCEL_SUBSCRIPTION = 2015;

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
            return R.layout.viewholder_packages_owned;
        else if (viewType == AVAILABLE_PACKAGES)
            return R.layout.viewholder_service_packages;
        else if (viewType == WEEKLY_REPORT)
            return R.layout.viewholder_report_weekly;
        else if (viewType == CANCEL_SUBSCRIPTION)
            return R.layout.viewholder_subscription_cancel;
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof ServicePackageDetails) {
            return ((ServicePackageDetails) dataList.get(position)).itemViewType;
        }
        if (dataList.get(position) instanceof OwnedPackagesDataObject) {
            return ((OwnedPackagesDataObject) dataList.get(position)).itemViewType;
        }
        if (dataList.get(position) instanceof CancelSubscriptionDataObject) {
            return ((CancelSubscriptionDataObject) dataList.get(position)).itemViewType;
        }
        if (dataList.get(position) instanceof HeaderThumbnailData.HorizontalViewData){
            return ((HeaderThumbnailData.HorizontalViewData) dataList.get(position)).getViewType();
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        if (viewType == WEEKLY_REPORT)
            return new WeeklyReportViewHolder(view);
        if (viewType == AVAILABLE_PACKAGES)
            return new ServicePackagesViewHolder(view);
        if (viewType == OWNED_PACKAGE)
            return new OwnedPackagesViewHolder(view);
        if (viewType == CANCEL_SUBSCRIPTION)
            return new CancelSubscriptionViewHolder(view);
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
            ((ServicePackagesViewHolder) holder).ivThumbnail.setOnClickListener((View.OnClickListener) holder);
        else if (holder instanceof WeeklyReportViewHolder){
            ((WeeklyReportViewHolder) holder).cTV.setOnClickListener((View.OnClickListener) holder);
        }
        else if (holder instanceof CancelSubscriptionViewHolder){
            ((CancelSubscriptionViewHolder) holder).fabCancel.setOnClickListener((View.OnClickListener) holder);
        }
        else ((ThumbnailViewHolder) holder).ivThumbnail.setOnClickListener((View.OnClickListener) holder);
    }

    class CancelSubscriptionViewHolder extends BaseItemHolder implements View.OnClickListener{

        @BindView(R.id.tvPackageName)
        TextView tvPackageName;
        @BindView(R.id.fabCancel)
        FloatingActionButton fabCancel;
        @BindView(R.id.cordinatorlayout)
        CoordinatorLayout cordinatorlayout;
        @BindView(R.id.rlMain)
        ViewGroup rlMain;
        @BindView(R.id.refView)
        View refView;

        public CancelSubscriptionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setUpDimensions();
            setOnClickListener(this);
        }

        private void setUpDimensions() {
            rlMain.getLayoutParams().height = (int) (16 * displayProperties.getYPixelsPerCell());
            ((CoordinatorLayout.LayoutParams) refView.getLayoutParams()).topMargin
                    = (int) (18.5 * displayProperties.getYPixelsPerCell());
        }

        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(v.getTag());
        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            CancelSubscriptionDataObject subscriptionDataObject = (CancelSubscriptionDataObject) dataObject;
            fabCancel.setTag(subscriptionDataObject);
            tvPackageName.setText(subscriptionDataObject.packageName);
        }
    }

    class OwnedPackagesViewHolder extends BaseItemHolder implements View.OnClickListener{

        @BindView(R.id.vgRoot)
        ViewGroup vgRoot;
        @BindView(R.id.ivBlurry)
        ImageView ivBlurry;
        @BindView(R.id.tvPurchasedOnDate)
        TextView tvPurchasedOnDate;
        @BindView(R.id.tvNextBillingDate)
        TextView tvNextBillingDate;
        @BindView(R.id.ivGlow)
        ShineButton ivGlow;
        @BindView(R.id.tvPackageName)
        TextView tvPackageName;
        @BindView(R.id.tvEvaporate)
        HTextView tvEvaporate;

        List<String> subServices = new ArrayList<>();

        public OwnedPackagesViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //setOnClickListener(this);
            ivGlow.init(MyApplication.getInstance().getVisibleActivity());
            tvEvaporate.setAnimationListener(new SimpleAnimationListener());
            //tvEvaporate.animateText(subServices.get(index));
            Runnable runnable = new Runnable() {
                public void run() {
                    Bitmap bm = BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bg_card_small);
                    Blurry.with(itemView.getContext()).from(bm).into(ivBlurry);
                    //Blurry.with(itemView.getContext()).radius(25).sampling(2).(vgRoot);
                }
            };
            new Handler(Looper.getMainLooper());//.postDelayed(runnable, 500);
        }

        int index = 0;

        class SimpleAnimationListener implements AnimationListener {

            //private Context context;

            public SimpleAnimationListener(/*Context context*/) {
                /*this.context = context;*/
            }
            @Override
            public void onAnimationEnd(final HTextView hTextView) {
                //Toast.makeText(context, "Animation finished", Toast.LENGTH_SHORT).show();
                Runnable runnable = new Runnable() {
                    public void run() {
                        ivGlow.performClick();
                        if (index + 1 >= subServices.size()) {
                            index = 0;
                        }
                        hTextView.animateText(subServices.get(index++));
                    }
                };
                new Handler(Looper.getMainLooper()).postDelayed(runnable, 1500);
            }
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            OwnedPackagesDataObject ownedPackagesDataObject = (OwnedPackagesDataObject) dataObject;
            tvPackageName.setText(ownedPackagesDataObject.packageName);
            tvPurchasedOnDate.setText(ownedPackagesDataObject.purchasedDate);
            tvNextBillingDate.setText(ownedPackagesDataObject.nextBillingDate);
            //Map<String, String> map = ownedPackagesDataObject.subServicesList;
            subServices = ownedPackagesDataObject.subServicesList;
            tvEvaporate.animateText(subServices.get(index));
        }
    }

    class WeeklyReportViewHolder extends BaseItemHolder implements View.OnClickListener{

        @BindView(R.id.viewSeparator)
        View viewSeparator;
        @BindView(R.id.cTV)
        CircleCheckedTextView cTV;
        @BindView(R.id.ivCheck)
        ImageView ivCheck;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.rlCheckHolder)
        ViewGroup rlCheckHolder;

        IconicsDrawable doneDrawable;
        IconicsDrawable undoneDrawable;
        ResourceReader resourceReader;

        public WeeklyReportViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setOnClickListener(this);
            resourceReader = ResourceReader.getInstance();
            doneDrawable = new IconicsDrawable(itemView.getContext())
                    .icon(GoogleMaterial.Icon.gmd_check_circle)
                    .color(com.mikepenz.iconics.IconicsColor
                            .colorInt(resourceReader
                                    .getColorFromResource(R.color.darkgreen)));
            undoneDrawable = new IconicsDrawable(itemView.getContext())
                    .icon(GoogleMaterial.Icon.gmd_cancel)
                    .color(com.mikepenz.iconics.IconicsColor
                            .colorInt(resourceReader
                                    .getColorFromResource(R.color.redStatus)));
        }

        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(view.getTag());
        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            WeeklyReportDetails progressDetails = (WeeklyReportDetails) dataObject;
            if (progressDetails.isFirstPackage){
                viewSeparator.setVisibility(View.VISIBLE);
                tvDate.setVisibility(View.VISIBLE);
                tvDate.setText(progressDetails.day);
            }else {
                viewSeparator.setVisibility(View.GONE);
                tvDate.setVisibility(View.GONE);
            }
            if (progressDetails.isOverAllPackageComplete)
                ivCheck.setImageDrawable(doneDrawable);
            else
                ivCheck.setImageDrawable(undoneDrawable);
            cTV.setTag(progressDetails);
            cTV.setText(progressDetails.packageShortName);
        }
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
            //tvDescription.setMovementMethod(new ScrollingMovementMethod());
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
