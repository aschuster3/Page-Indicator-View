package com.centaurii.views.pageindicator;

import com.centaurii.views.pageindicator.lib.PageIndicator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity
{

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final PageIndicator pageTracker = (PageIndicator) findViewById(R.id.page_tracker);
        
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        
        mViewPager.setOnPageChangeListener(new OnPageChangeListener()
        {

            @Override
            public void onPageScrollStateChanged(int arg0) {}

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageSelected(int position)
            {
                pageTracker.setCurrentPosition(position);
                pageTracker.invalidate();
            }
            
        });

    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter
    {
        public DemoCollectionPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int i)
        {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount()
        {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return "OBJECT " + (position + 1);
        }

    }

    public static class DemoObjectFragment extends Fragment
    {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState)
        {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_main, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(R.id.text_view)).setText(Integer
                    .toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }

}
