package com.nextclick.grocery;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapterShipping extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterShipping(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Shipping tab1 = new Shipping();
                return tab1;
            case 1:
                Confirmation tab2 = new Confirmation();
                return tab2;
            case 2:
                Payment tab3 = new Payment();
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