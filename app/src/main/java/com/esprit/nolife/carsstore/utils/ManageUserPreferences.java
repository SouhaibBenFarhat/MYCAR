package com.esprit.nolife.carsstore.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Souhaib on 02/11/2016.
 */

public class ManageUserPreferences {

    SharedPreferences sp;
    public static List<String> signinParametrs;

    public ManageUserPreferences() {
        signinParametrs = new ArrayList<String>();
    }


    public void insertSigninParameters(Activity activity, String username, String password) {
        sp = activity.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString("username", username);
        spe.putString("password", password);
        spe.commit();

    }

    public List<String> getSigninParameters(Activity activity, String fileName) {
        sp = activity.getSharedPreferences(fileName, MODE_PRIVATE);

        signinParametrs.add(0, sp.getString("username", ""));
        signinParametrs.add(1, sp.getString("password", ""));
        return signinParametrs;
    }

    public void setUserState(Activity activity, String state) {
        sp = activity.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString("state", state);
        spe.commit();
    }

    public String getUserState(Activity activity) {
        sp = activity.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        return sp.getString("state", "");

    }
}
