package com.esprit.nolife.carsstore.activities.main_acitivity.view_pager_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.NewCarsFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.UsedCarsFragment;

/**
 * Created by Souhaib on 31/10/2016.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    public HomePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewCarsFragment();    //Fragment

            case 1:
                return new UsedCarsFragment();    //Fragment
            default:
                return null;    //Fragment
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "NOUVEAU";    //String

            default:
                return "OCCASSION";    //String
        }
    }


}
