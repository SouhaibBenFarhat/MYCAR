package com.esprit.nolife.carsstore.activities.login_activity.view_pager_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.esprit.nolife.carsstore.activities.login_activity.fragments.LoginFragment;
import com.esprit.nolife.carsstore.activities.login_activity.fragments.RegisterFragment;

/**
 * Created by Souhaib on 31/10/2016.
 */

public class LoginPagerAdapter extends FragmentPagerAdapter {

    public LoginPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();    //Fragment

            case 1:
                return new RegisterFragment();    //Fragment
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
                return "SE CONNECTER";    //String

            default:
                return "S'INSCRIRE";    //String
        }
    }
}


