package com.nextclick.grocery;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter1 extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter1(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Eapp1 tab1 = new Eapp1();
                return tab1;
            case 1:
                Eapp0 tab0 = new Eapp0();
                return tab0;
            case 2:
                Eapp4 tab4 = new Eapp4();
                return tab4;
            case 3:
                Eapp3 tab3 = new Eapp3();
                return tab3;
            case 4:
                Eapp6 tab6 = new Eapp6();
                return tab6;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}