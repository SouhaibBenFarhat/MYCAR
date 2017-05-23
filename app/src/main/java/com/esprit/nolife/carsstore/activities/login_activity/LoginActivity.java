package com.esprit.nolife.carsstore.activities.login_activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.MainActivity;
import com.esprit.nolife.carsstore.activities.login_activity.view_pager_adapters.LoginPagerAdapter;
import com.esprit.nolife.carsstore.utils.ManageUserPreferences;
import com.facebook.FacebookSdk;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton btnLogin;
    ViewPager loginContainer;
    ManageUserPreferences manageUserPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.sf);
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();
        FacebookSdk.sdkInitialize(getApplicationContext());

        manageUserPreferences = new ManageUserPreferences();
        if (manageUserPreferences.getUserState(this).equals("connected")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        loginContainer = (ViewPager) findViewById(R.id.login_container);
        PagerAdapter loginPagerAdapter
                = new LoginPagerAdapter(getSupportFragmentManager());
        loginContainer.setAdapter(loginPagerAdapter);


    }
}
