package com.shrinkcom.medicale.fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.shrinkcom.medicale.R;
import com.shrinkcom.medicale.fragment.MyOrderFragment;
import com.shrinkcom.medicale.fragment.MyPrescriptionFragment;

import java.util.ArrayList;
import java.util.List;

public class MyOrderOneFragment extends Fragment {
    TabLayout mTabs;
    View mIndicator;
    ViewPager mViewPager;
    private int indicatorWidth;
    TabFragmentAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=  inflater.inflate(R.layout.fragment_order_one_product, container, false);

        mTabs = view.findViewById(R.id.tab);
        mIndicator = view.findViewById(R.id.indicator);
        mViewPager = view.findViewById(R.id.viewPager);


        adapter = new TabFragmentAdapter(getFragmentManager());
        adapter.addFragment(new MyPrescriptionFragment(), "My Prescription");
        adapter.addFragment(new MyOrderFragment(), "My Order");

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs));
        mTabs.setupWithViewPager(mViewPager);

        mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        /*mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });*/
        return view;
    }
    public class TabFragmentAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public TabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                return new MyPrescriptionFragment();

                case 1:
                    return new MyOrderFragment();

            }

            return null;
        }

        @Override
        public int getCount() {
            Log.d("TAG", "getCount: "+fragmentList.size());
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }
    }
}