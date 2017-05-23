package com.esprit.nolife.carsstore.background_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.esprit.nolife.carsstore.URL.Key;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.FeaturedCarsRecyclerAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.FeaturedCar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Souhaib on 11/11/2016.
 */

public class FeaturedCarsManager {
    int nbElement;
    FeaturedCarsRecyclerAdapter featuredCarsRecyclerAdapter;
    Context context;
    public final ArrayList<FeaturedCar> featuredCars = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String orientation = "";


    public FeaturedCarsManager(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;


    }

    public FeaturedCarsManager(Context context, RecyclerView recyclerView, String orientation) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.orientation = orientation;


    }


    public void getFeaturedCars(final String nbItems) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_FEATURED_CARS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                if (nbItems.equals(Key.ALL)) {
                    nbElement = response.length();
                } else {
                    nbElement = 10;
                }

                int count = 0;
                while (count < nbElement) {
                    try {
                        FeaturedCar f = new FeaturedCar();
                        JSONObject jsonObject = response.getJSONObject(count);

                        f.setTitle(jsonObject.getString("title"));
                        f.setPubDate(jsonObject.getString("pubdate"));
                        f.setAuthor(jsonObject.getString("author"));
                        f.setCategory(jsonObject.getString("category"));
                        f.setDescription(jsonObject.getString("description"));
                        f.setEnclosure(jsonObject.getString("enclosure"));
                        f.setLink(jsonObject.getString("link"));
                        f.setThumbnailUrl(jsonObject.getString("thumbnail"));
                        featuredCars.add(f);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (orientation.equals("")) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    featuredCarsRecyclerAdapter = new FeaturedCarsRecyclerAdapter(context, featuredCars);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(featuredCarsRecyclerAdapter);
                    progressDialog.dismiss();
                } else {
                    LinearLayoutManager linearLayoutManager = new GridLayoutManager(context, 2);
                    featuredCarsRecyclerAdapter = new FeaturedCarsRecyclerAdapter(context, featuredCars);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(featuredCarsRecyclerAdapter);
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(context).addToRequestque(jsonArrayRequest);

    }


    public void getFeaturedCarsByTitle(final String carName) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_FEATURED_CARS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        FeaturedCar f = new FeaturedCar();
                        JSONObject jsonObject = response.getJSONObject(count);
                        String title = jsonObject.getString("title");

                        if (title.toLowerCase().contains(carName)) {

                            f.setTitle(jsonObject.getString("title"));
                            f.setPubDate(jsonObject.getString("pubdate"));
                            f.setAuthor(jsonObject.getString("author"));
                            f.setCategory(jsonObject.getString("category"));
                            f.setDescription(jsonObject.getString("description"));
                            f.setEnclosure(jsonObject.getString("enclosure"));
                            f.setLink(jsonObject.getString("link"));
                            f.setThumbnailUrl(jsonObject.getString("thumbnail"));
                            featuredCars.add(f);
                        }

                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                featuredCarsRecyclerAdapter = new FeaturedCarsRecyclerAdapter(context, featuredCars);
                recyclerView.setAdapter(featuredCarsRecyclerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(context).addToRequestque(jsonArrayRequest);
    }


}
