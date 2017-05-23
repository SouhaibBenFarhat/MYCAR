package com.esprit.nolife.carsstore.utils;

/**
 * Created by Souhaib on 02/11/2016.
 */

public class InputValidation {

    int passwordLength = 8;

    public Boolean mailIsValid(String mail) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            return true;
        }
        return false;

    }


    public Boolean passwordIsValid(String password) {
        if (password.length() > passwordLength) {
            return true;
        }
        return false;
    }


}
