package dj.example.main.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.example.main.R;
import dj.example.main.activities.MyApplication;
import dj.example.main.activities.TwoTabsBaseActivity;
import dj.example.main.redundant.BaseFragment;

public class HomePage extends BaseFragment implements ViewPager.OnPageChangeListener{

    @Bind(R.id.disableApp)
    View disableApp;

    public static final int FILTER_APPLY = 2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    protected void garbageCollectorCall() {

    }

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    FragmentStatePagerAdapter pagerAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        pagerAdapter = new TempPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        setUpTabLayout(view);
        viewPager.addOnPageChangeListener(this);
    }

    RecyclerTabLayout tabLayoutPrimary;

    private void setUpTabLayout(View rootView) {
        final View tabLayout = rootView.findViewById(R.id.indicator);
        if (getActivity() instanceof TwoTabsBaseActivity){
            if (((TwoTabsBaseActivity) getActivity()).getPageIndicator() != null) {
                if (tabLayout != null)
                    tabLayout.setVisibility(View.GONE);
                tabLayoutPrimary = (RecyclerTabLayout) ((TwoTabsBaseActivity) getActivity()).getPageIndicator();
                RecyclerTabLayout.Adapter indicatorAdapter = getRecyclerTabIndicator(viewPager);
                if (indicatorAdapter != null)
                    tabLayoutPrimary.setUpWithAdapter(indicatorAdapter);
                tabLayoutPrimary.getAdapter().notifyDataSetChanged();
                tabLayoutPrimary.invalidate();
            }
        }
        if (tabLayout instanceof TabLayout) {
            Runnable runnable = new Runnable() {
                public void run() {
                    ((TabLayout) tabLayout).setupWithViewPager(viewPager);
                }
            };
            MyApplication.getInstance().getUiHandler().postDelayed(runnable, 100);
        }
    }

    private final int numOfPages = 2;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class TempPagerAdapter extends FragmentStatePagerAdapter{

        public TempPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return numOfPages;
        }

       /* @Override
        public int getItemPosition(Object object) {
            //return super.getItemPosition(object);
            if (!(object instanceof SocialFeedFragment))
                return POSITION_NONE;
            return POSITION_UNCHANGED;
        }*/

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new TabFragment1();
            else if (position == 1)
                return new TabFragment2();
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d(TAG, "destroyed items FragPageAdapter");
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d(TAG, "destroyed items FragPageAdapter");
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "TabFragment1";
            else if (position == 1)
                return "TabFragment2";
            return super.getPageTitle(position);
        }
    }

    private final String TAG = "HomeFragment";

    private SparseArray<String> positionTitle = new SparseArray<>();

    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public Fragment getRegisteredFragment(int fragmentPosition) {
        return registeredFragments.get(fragmentPosition);
    }

    TabIndicatorRecyclerViewAdapter tabIndicatorAdapter;
    protected RecyclerTabLayout.Adapter<?> getRecyclerTabIndicator(ViewPager viewPager) {
        return tabIndicatorAdapter =  new TabIndicatorRecyclerViewAdapter(viewPager);
    }

    private void gotoData(Object data) {
        if(this.viewPager != null) {
            List<String> dataList = tabIndicatorAdapter.getDataList();
            for (String txt: dataList) {
                if (txt.equals(data))
                    this.viewPager.setCurrentItem(dataList.indexOf(txt), false);
            }
        }
    }

    /*private AppTourGuideHelper mTourHelper;
    private void setUpTourGuideForPeople() {
        Log.d("djhomePage", "onClickPeopleTab");
        *//*resRdr = ResourceReader.getInstance(getApplicationContext());
        coachMarkMgr = DjphyPreferenceManager.getInstance(getApplicationContext());*//*
        mTourHelper = AppTourGuideHelper.getInstance(Application.getInstance());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                *//*if (!coachMarkMgr.isHomeScreenTourDone())
                    testTourGuide();*//*
                mTourHelper.displayPeopleScreenTour(getActivity(), ((MainActivity) getActivity()).getCenterView());
            }
        }, 800);
    }*/

    //public static ViewPager viewPager;

    public class TabIndicatorRecyclerViewAdapter extends RecyclerTabLayout.Adapter<TabIndicatorRecyclerViewAdapter.MyViewHolder>{

        public TabIndicatorRecyclerViewAdapter(ViewPager viewPager) {
            super(viewPager);
            //HomePage.viewPager = viewPager;
            dataList.clear();
            dataList.add("Item-1");
            dataList.add("Item-2");
        }

        List<String> dataList = new ArrayList<>();

        public List<String> getDataList() {
            return dataList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_tab_view, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String item = dataList.get(position);
            holder.itemView.setVisibility(View.VISIBLE);
            if(position == this.getCurrentIndicatorPosition()) {
                holder.updatedSelectedItem(item);
            } else {
                holder.updatedNormalItem(item);
            }
        }

        public int getItemCount() {
            return dataList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.textView)
            public TextView textView;
            @Bind(R.id.selected)
            public View selected;

            @Bind(R.id.tabParent)
            RelativeLayout tabParent;
            String data;


            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoData(data);
                    }
                });
                setTabWidth();
            }

            private void setTabWidth() {
                ViewTreeObserver vto = tabLayoutPrimary.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        try {
                            int indicatorWidth = tabLayoutPrimary.getWidth();
                            Log.d("dj", " indicator width: " + indicatorWidth);
                            tabParent.getLayoutParams().width = indicatorWidth / 2;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                tabLayoutPrimary.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            } else
                                tabLayoutPrimary.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            //NavigationDataObject data;

            public void updatedSelectedItem(Object o) {
                data = (String) o;
                textView.setText(data);
                textView.setTextColor(textView.getResources().getColor(R.color.colorPrimary));
                selected.setVisibility(View.VISIBLE);
            }

            public void updatedNormalItem(Object o) {
                data = (String) o;
                textView.setText(data);
                textView.setTextColor(textView.getResources().getColor(R.color.colorPrimaryTrans));
                selected.setVisibility(View.INVISIBLE);
            }
        }
    }
}
