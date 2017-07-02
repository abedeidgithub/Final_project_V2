package com.example.abedeid.myapplication.activites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.example.abedeid.myapplication.Fragments.Ask;
import com.example.abedeid.myapplication.Fragments.News;
import com.example.abedeid.myapplication.Fragments.PostOrAsk;
import com.example.abedeid.myapplication.Fragments.Profile;
import com.example.abedeid.myapplication.Fragments.ScheduleFragment;
import com.example.abedeid.myapplication.Fragments.material;
import com.example.abedeid.myapplication.R;
import com.example.abedeid.myapplication.model.Section;
import com.example.abedeid.myapplication.model.news;
import com.example.abedeid.myapplication.utils.Session;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {


    public TabLayout tabLayout;
    public ViewPager viewPager;
    public int[] tabIcons = {

            R.drawable.post,
            R.drawable.profile,
            R.drawable.news,
            R.drawable.material,
            R.drawable.schadual,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseMessaging.getInstance().subscribeToTopic(Session.getInstance().getUser().year_id+Session.getInstance().getUser().depart_id+Session.getInstance().getUser().section_id);

          viewPager = (ViewPager) findViewById(R.id.viewpager);
          setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
        setupTabIcons();
    }

    public void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[0]);
        tabLayout.getTabAt(2).setIcon(tabIcons[1]);
        tabLayout.getTabAt(3).setIcon(tabIcons[2]);
        tabLayout.getTabAt(4).setIcon(tabIcons[3]);
        tabLayout.getTabAt(5).setIcon(tabIcons[4]);

    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PostOrAsk() );
        adapter.addFragment(new Ask() );
        adapter.addFragment(new Profile() );
        adapter.addFragment(new News() );
        adapter.addFragment(new material() );
        adapter.addFragment(new ScheduleFragment() );

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragmentList = new ArrayList<>();

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


        public void addFragment(Fragment fragment ) {
            mFragmentList.add(fragment);
         }


    }


}
