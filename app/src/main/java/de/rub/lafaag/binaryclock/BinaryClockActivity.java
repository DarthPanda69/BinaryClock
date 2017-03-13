package de.rub.lafaag.binaryclock;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import de.rub.lafaag.binaryclock.fragments.AboutLafaagFragment;
import de.rub.lafaag.binaryclock.fragments.BinaryAlarmFragment;
import de.rub.lafaag.binaryclock.fragments.BinaryClockFragment;
import de.rub.lafaag.binaryclock.fragments.BinaryTimerFragment;

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

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_clock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(Pages.CLOCK).select();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //Nothing to do here
            }

            @Override
            public void onPageSelected(int i) {
                updateFabLayout(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //Set these beautiful tiny icons
        setTabIcons();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_binary_clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setTabIcons() {
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        for(int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            if(mSectionsPagerAdapter.getPageIconResource(i) > 0)
                tabLayout.getTabAt(i).setIcon(mSectionsPagerAdapter.getPageIconResource(i));
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
                case Pages.TIMER:
                    return BinaryTimerFragment.newInstance();
                case Pages.CLOCK:
                    return BinaryClockFragment.newInstance();
                case Pages.ALARM:
                    return BinaryAlarmFragment.newInstance();
                case Pages.ABOUT:
                    return AboutLafaagFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) { // Selection Text
            return "";
        }

        int getPageIconResource(int position) {
            switch(position) {
                case Pages.TIMER:
                    return R.drawable.ic_timer_white_48dp;
                case Pages.CLOCK:
                    return R.drawable.ic_access_time_white_48dp;
                case Pages.ALARM:
                    return R.drawable.ic_alarm_white_48dp;
                case Pages.ABOUT:
                    return R.drawable.ic_info_outline_white_48dp;
            }
            return -1;
        }
    }

    static class Pages {
        static final int TIMER = 0;
        static final int CLOCK = 1;
        static final int ALARM = 2;
        static final int ABOUT = 3;
    }
}
