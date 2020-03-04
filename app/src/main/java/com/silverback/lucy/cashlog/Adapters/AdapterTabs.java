package com.silverback.lucy.cashlog.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class AdapterTabs extends FragmentStatePagerAdapter{

    ArrayList<Fragment> fragments;

    public AdapterTabs(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }       //end getNewItem()

    @Override
    public int getCount() {
        return fragments.size();
    }       //end getCount()

}       //end class

