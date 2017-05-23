package com.esprit.nolife.carsstore.dao;

import com.esprit.nolife.carsstore.entities.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Souhaib on 05/11/2016.
 */

public class UserDao {


    public void bringUserToMySide(User user) {

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        dataRef.child("users").child(user.getUserUID()).setValue(user);

    }

}
