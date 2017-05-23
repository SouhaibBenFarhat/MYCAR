package com.esprit.nolife.carsstore.activities.main_acitivity.view_pager_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments.FirstStepSellFragment;
import com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments.SecondStepSellCarFragment;
import com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments.ThirdStepSellCarFragment;

/**
 * Created by Souhaib on 31/10/2016.
 */

public class SellCarPagerAdapter extends FragmentStatePagerAdapter {

    public SellCarPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //return new SecondStepSellCarFragment();    //Fragment
               return new FirstStepSellFragment();
              // return new ThirdStepSellCarFragment();
            case 1:
                // return new FirstStepSellFragment();    //Fragment
                return new SecondStepSellCarFragment();//Fragment
            case 2:
                // return new FirstStepSellFragment();    //Fragment
                return new ThirdStepSellCarFragment();//Fragment

            default:
                return null;    //Fragment
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Brands";    //String

            default:
                return null;    //String
        }
    }


}
