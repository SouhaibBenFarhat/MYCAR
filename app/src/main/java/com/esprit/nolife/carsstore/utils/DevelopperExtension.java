package com.esprit.nolife.carsstore.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.google.android.gms.common.SignInButton;

/**
 * Created by Souhaib on 05/11/2016.
 */

public class DevelopperExtension {

    public void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
            if (v instanceof ImageView) {
                ((ImageView) v).setImageResource(R.drawable.btn_google_signin_dark_normal_mdpi);
                return;
            }
        }
    }


    public void setGoogleBackgroundImage(SignInButton signInButton, int ressource) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof ImageView) {
                ((ImageView) v).setImageResource(ressource);
                return;
            }
        }
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }
}
