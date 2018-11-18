package com.volunteam.mobilebijb.parking;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ParkingPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public ParkingPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BookingFragment tab1 = new BookingFragment();
                return tab1;
            case 1:
                CalculateFragment tab2 = new CalculateFragment();
                return tab2;
            case 2:
                HistoryFragment tab3 = new HistoryFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}