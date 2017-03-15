package de.rub.lafaag.binaryclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;

import java.util.Calendar;

import de.rub.lafaag.binaryclock.fragments.AboutLafaagFragment;
import de.rub.lafaag.binaryclock.fragments.BinaryAlarmFragment;
import de.rub.lafaag.binaryclock.fragments.BinaryClockFragment;
import de.rub.lafaag.binaryclock.fragments.BinaryStopwatchFragment;
import de.rub.lafaag.binaryclock.fragments.BinaryTimerFragment;
import de.rub.lafaag.binaryclock.widget.ClockAppWidgetProvider;

public class BinaryClockActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_clock);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //Set these beautiful tiny icons
        setTabIcons();
        //Update ActionButton per tab and highlight current tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateFabLayout(tab.getPosition());
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tabActive), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tabInactive), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Select clock as startpage
        tabLayout.getTabAt(Pages.CLOCK).select();

        ClockAppWidgetProvider.setAlarmToNextMinute(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_binary_clock, menu);
        return true;
    }

    private void setTabIcons() {
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        for(int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            int iconResource = mSectionsPagerAdapter.getPageIconResource(i);
            if(iconResource > 0) {
                tabLayout.getTabAt(i).setIcon(iconResource);
                //Add filter for inactive tab
                tabLayout.getTabAt(i).getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tabInactive), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private void updateFabLayout(int newPage) {
        FloatingActionButton fab = ((FloatingActionButton)findViewById(R.id.fab));
        if(newPage != Pages.ALARM)
            fab.hide();
        else
            fab.show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case Pages.ALARM:
                    return BinaryAlarmFragment.newInstance();
                case Pages.CLOCK:
                    return BinaryClockFragment.newInstance();
                case Pages.TIMER:
                    return BinaryTimerFragment.newInstance();
                case Pages.STOPWATCH:
                    return BinaryStopwatchFragment.newInstance();
                case Pages.ABOUT:
                    return AboutLafaagFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) { // Selection Text
            return "";
        }

        int getPageIconResource(int position) {
            switch(position) {
                case Pages.ALARM:
                    return R.drawable.ic_alarm_white_48dp;
                case Pages.CLOCK:
                    return R.drawable.ic_access_time_white_48dp;
                case Pages.TIMER:
                    return R.drawable.ic_hourglass_empty_white_48dp;
                case Pages.STOPWATCH:
                    return R.drawable.ic_timer_white_48dp;
                case Pages.ABOUT:
                    return R.drawable.ic_info_outline_white_48dp;
            }
            return -1;
        }
    }

    static class Pages {
        static final int ALARM = 0;
        static final int CLOCK = 1;
        static final int TIMER = 2;
        static final int STOPWATCH = 3;
        static final int ABOUT = 4;
    }
}
