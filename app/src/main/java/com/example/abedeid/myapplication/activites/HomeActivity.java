package com.example.abedeid.myapplication.activites;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.abedeid.myapplication.Fragments.PostOrAsk;
import com.example.abedeid.myapplication.Fragments.Profile;
import com.example.abedeid.myapplication.Fragments.ScheduleFragment;
import com.example.abedeid.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

     public TabLayout tabLayout;
    public ViewPager viewPager;
    public int[] tabIcons = {

            R.drawable.ic_discount,
            R.drawable.ic_food,
            R.drawable.ic_travel,
            R.drawable.ic_discount,
            R.drawable.ic_food,
            R.drawable.ic_travel,
            R.drawable.ic_discount,
            R.drawable.ic_food,
            R.drawable.ic_travel,
            R.drawable.ic_discount,
            R.drawable.ic_food,
            R.drawable.ic_travel
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
        setupTabIcons();
    }

    public void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
        tabLayout.getTabAt(6).setIcon(tabIcons[6]);
        tabLayout.getTabAt(7).setIcon(tabIcons[7]);
        tabLayout.getTabAt(8).setIcon(tabIcons[8]);
        tabLayout.getTabAt(9).setIcon(tabIcons[9]);
        tabLayout.getTabAt(10).setIcon(tabIcons[10]);
        tabLayout.getTabAt(11).setIcon(tabIcons[11]);
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScheduleFragment(), "Schedule");
        adapter.addFragment(new Profile(), "profile");
        adapter.addFragment(new PostOrAsk(), "Poats");
        adapter.addFragment(new ScheduleFragment(), "ONE");
        adapter.addFragment(new ScheduleFragment(), "TWO");
        adapter.addFragment(new ScheduleFragment(), "THREE");
        adapter.addFragment(new ScheduleFragment(), "ONE");
        adapter.addFragment(new ScheduleFragment(), "TWO");
        adapter.addFragment(new ScheduleFragment(), "THREE");
        adapter.addFragment(new ScheduleFragment(), "ONE");
        adapter.addFragment(new ScheduleFragment(), "TWO");
        adapter.addFragment(new ScheduleFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragmentList = new ArrayList<>();
        public final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
