package com.esprit.nolife.carsstore.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.esprit.nolife.carsstore.activities.comparator_acitivity.ComparatorActivity;
import com.esprit.nolife.carsstore.entities.Gamme;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Souhaib on 04/11/2016.
 */

public class MyApplication extends MultiDexApplication {

    private boolean connected;
    private ArrayList<Gamme> gammes = new ArrayList<>();


    public MyApplication() {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(MyApplication.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.esprit.souhaibbenfarhat.carsstore",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


    }


    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void addToComparator(Gamme gamme, Context context) {

        gammes.add(gamme);
        if (gammes.size()<2){
            Toast.makeText(context, "Ajoutée", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Redirection...", Toast.LENGTH_SHORT).show();
        }
        if (gammes.size() == 2) {
            Intent intent = new Intent(context, ComparatorActivity.class);
            context.startActivity(intent);
        }

    }

    public Gamme getFirstCar() {
        return gammes.get(0);
    }

    public Gamme getSecondCar() {
        return gammes.get(1);
    }

    public void clearComparator() {
        gammes.clear();
    }
}
