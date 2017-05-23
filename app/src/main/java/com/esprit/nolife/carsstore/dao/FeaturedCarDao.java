package com.esprit.nolife.carsstore.dao;

import com.esprit.nolife.carsstore.entities.FeaturedCar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Souhaib on 09/11/2016.
 */

public class FeaturedCarDao {


    public ArrayList<FeaturedCar> featuredCars;


    public FeaturedCarDao() {
        featuredCars = new ArrayList<>();
    }


    public void bringFeaturedCarsToMySide(FeaturedCar car) {

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        SecureRandom random = new SecureRandom();
        String id = new BigInteger(130, random).toString(32);
        car.setFeaturedCarId(id + car.hashCode());
        dataRef.child("featuredCars").child(car.getFeaturedCarId()).setValue(car);

    }

    public void getFeaturedCars(final ArrayList<FeaturedCar> list) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery = databaseReference.child("featuredCars");

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot featuredCarSnapShot : snapshot.getChildren()) {
                    FeaturedCar featuredCar = new FeaturedCar();
                    featuredCar.setFeaturedCarId((String) featuredCarSnapShot.child("featuredCarId").getValue());
                    featuredCar.setAuthor((String) featuredCarSnapShot.child("author").getValue());
                    featuredCar.setCategory((String) featuredCarSnapShot.child("category").getValue());
                    featuredCar.setDescription((String) featuredCarSnapShot.child("description").getValue());
                    featuredCar.setLink((String) featuredCarSnapShot.child("link").getValue());
                    featuredCar.setPubDate((String) featuredCarSnapShot.child("pubDate").getValue());
                    featuredCar.setThumbnailUrl((String) featuredCarSnapShot.child("thumbnailUrl").getValue());
                    featuredCar.setTitle((String) featuredCarSnapShot.child("title").getValue());
                    list.add(featuredCar);

                }


            }


            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }

        });

    }


}