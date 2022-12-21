package com.example.elaislami.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.elaislami.View.Fragment.HomeFragment;
import com.example.elaislami.View.Fragment.PrayerFragment;
import com.example.elaislami.View.Fragment.QiblaFragment;
import com.example.elaislami.View.Fragment.SurahsListFragment;

/*
 * This is the tabs list for the view pager
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    /*
     * attributes for adapter
     */
    private final int nNumOfTabs;

    // Here is the constructor
    public TabsAdapter(FragmentManager fm, int nNumOfTabs) {
        super(fm);
        this.nNumOfTabs = nNumOfTabs;
    }

    /*
     * Change current visible fragment
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new HomeFragment();

            case 1:
                return new PrayerFragment();

            case 2:
                return new QiblaFragment();

            case 3:
                return new SurahsListFragment();

            default:
                return null;
        }

    }

    /*
     * Get the total number of tabs
     */
    @Override
    public int getCount() {
        return nNumOfTabs;
    }
}

