package com.example.tapojit.hamphack;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        startService(new Intent(this,NotificationService.class));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0) {
                    tab.setIcon(R.drawable.bell_nf);
                    getSupportActionBar().setTitle("Feeds");
                }
                else if(tab.getPosition()==1) {
                    tab.setIcon(R.drawable.qr_nf);
                    getSupportActionBar().setTitle("QR code");
                }
                else if(tab.getPosition()==2){
                    tab.setIcon(R.drawable.wp_nf);
                    getSupportActionBar().setTitle("Webpage");
//                    Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://hamphack.hampshire.edu"));
//                    startActivity(browserIntent);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition()==0) tab.setIcon(R.drawable.bell_f);
                else if(tab.getPosition()==1) tab.setIcon(R.drawable.qr_f);
                else tab.setIcon(R.drawable.bw_f);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getSupportActionBar().setTitle("Feeds");
        tabLayout.getTabAt(0).setIcon(R.drawable.bell_nf);
        tabLayout.getTabAt(1).setIcon(R.drawable.qr_f);
        tabLayout.getTabAt(2).setIcon(R.drawable.bw_f);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.organizer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_organizer) {
            startActivity(new Intent(this,Authentication.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

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
            switch (position){
                case 0:
                    Feeds t1=new Feeds();
                    return t1;
                case 1:
                    QR t2=new QR();
                    return t2;
                case 2:
                    Frame3 f=new Frame3();
                    return f;

                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "SECTION 1";
//                case 1:
//                    return "SECTION 2";
//                case 2:
//                    return "SECTION 3";
//            }
//            return null;
//        }
    }
}
