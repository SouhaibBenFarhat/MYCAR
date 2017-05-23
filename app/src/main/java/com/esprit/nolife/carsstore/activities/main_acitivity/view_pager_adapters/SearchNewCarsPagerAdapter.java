package com.esprit.nolife.carsstore.activities.main_acitivity.view_pager_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.BrandFragment;

/**
 * Created by Souhaib on 31/10/2016.
 */

public class SearchNewCarsPagerAdapter extends FragmentStatePagerAdapter {

    public SearchNewCarsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BrandFragment();    //Fragment

            default:
                return null;    //Fragment
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Brands";    //String
            default:
                return "";    //String
        }
    }


}
