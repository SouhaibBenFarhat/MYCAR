package com.esprit.nolife.carsstore.URL;

/**
 * Created by Souhaib on 11/11/2016.
 */

public abstract class URL {


    public static String GET_FEATURED_CARS;
    public static String GET_BODY_CARS;
    public static String GET_BRANDS;
    public static String GET_MODELS_BY_BRAND;
    public static String GET_GAMME_BY_MODEL;
    public static String GET_GAMME_NAME_BY_MODEL;
    public static String GET_BRAND_LOGO;
    public static String GET_PHOTO_BY_MODEL;
    public static String GET_GAMME_CARACTERISTIQUE;
    public static String GET_GAMME_MOTORISATION;
    public static String GET_GAMME_RAFFINEMENT;
    public static String GET_PHOTO_BY_GAMME;
    public static String GET_CONCESSIONNAIRE;
    public static String INSERT_USER_FAVORIS;
    public static String GET_USER_FAVORIS;
    public static String INSERT_MODEL_RATING;
    public static String GET_RATING_VALUE;
    public static String GET_USER_RATING;
    public static String DID_USER_RATE;
    public static String DELETE_USER_FAVOPRIS;
    public static String GET_ALL_GAMMES;
    public static String FILTER;


    public static String GET_ALL_ANNONCE;
    public static String GET_My_ANNONCE;
    public static String SET_Annonce;
    public static String GET_PHOTO_ANNONCE;

    public static void setIpServer() {

        GET_FEATURED_CARS = "http://inceptumapps.com/carsstore/mobile/android/get_featured_cars.php";
        GET_BODY_CARS = "http://inceptumapps.com/carsstore/mobile/android/get_body_cars.php";
        GET_BRANDS = "http://inceptumapps.com/carsstore/mobile/android/get_brands.php";
        GET_MODELS_BY_BRAND = "http://inceptumapps.com/carsstore/mobile/android/get_models_by_brand.php";
        GET_GAMME_BY_MODEL = "http://inceptumapps.com/carsstore/mobile/android/get_gamme_by_model.php";
        GET_ALL_GAMMES = "http://inceptumapps.com/carsstore/mobile/android/get_all_gamme.php";
        GET_GAMME_NAME_BY_MODEL = "http://inceptumapps.com/carsstore/mobile/android/get_name_gamme_by_model.php";
        GET_BRAND_LOGO = "http://inceptumapps.com/carsstore/mobile/android/get_brand_logo.php";
        GET_PHOTO_BY_MODEL = "http://inceptumapps.com/carsstore/mobile/android/get_photo_by_model.php";
        GET_GAMME_CARACTERISTIQUE = "http://inceptumapps.com/carsstore/mobile/android/get_gamme_caracteristique.php";
        GET_GAMME_RAFFINEMENT = "http://inceptumapps.com/carsstore/mobile/android/get_gamme_raffinement.php";
        GET_GAMME_MOTORISATION = "http://inceptumapps.com/carsstore/mobile/android/get_gamme_motorisation.php";
        GET_PHOTO_BY_GAMME = "http://inceptumapps.com/carsstore/mobile/android/get_photo_by_gamme.php";
        GET_CONCESSIONNAIRE = "http://inceptumapps.com/carsstore/mobile/android/get_concessionnaire.php";
        INSERT_USER_FAVORIS = "http://inceptumapps.com/carsstore/mobile/android/insert_into_favoris.php";
        GET_USER_FAVORIS = "http://inceptumapps.com/carsstore/mobile/android/get_user_favoris.php?";
        INSERT_MODEL_RATING = "http://inceptumapps.com/carsstore/mobile/android/insert_user_rating.php";
        GET_RATING_VALUE = "http://inceptumapps.com/carsstore/mobile/android/get_model_rating.php";
        GET_USER_RATING = "http://inceptumapps.com/carsstore/mobile/android/get_user_rating.php";
        DID_USER_RATE = "http://inceptumapps.com/carsstore/mobile/android/did_user_rate.php";
        DELETE_USER_FAVOPRIS = "http://inceptumapps.com/carsstore/mobile/android/delete_user_favoris.php";
        FILTER = "http://inceptumapps.com/carsstore/mobile/android/filter.php?";


        GET_ALL_ANNONCE = "http://inceptumapps.com/carsstore/mobile/android/get_annonce.php";
        GET_My_ANNONCE = "http://inceptumapps.com/carsstore/mobile/android/get_my_annonce.php?user_id=";
        SET_Annonce = "http://inceptumapps.com/carsstore/mobile/android/insert_annoce.php?";
        GET_PHOTO_ANNONCE = "http://inceptumapps.com/carsstore/mobile/android/get_photo_annance.php?";


    }

}
