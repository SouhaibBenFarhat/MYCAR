package com.esprit.nolife.carsstore.background_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.GammeRecyclarViewAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Gamme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Souhaib on 29/12/2016.
 */

public class FavorisManager {

    Context mContrext;
    RecyclerView recyclerView;

    public FavorisManager(Context mContrext) {
        this.mContrext = mContrext;
    }

    public FavorisManager(Context mContrext, RecyclerView recyclerView) {
        this.mContrext = mContrext;
        this.recyclerView = recyclerView;
    }


    public boolean addUserFavoris(final String userId, final String gammeId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.INSERT_USER_FAVORIS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")) {
                            Toast.makeText(mContrext, "Ajout effectué", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mContrext, "Existe déja...", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContrext, "Probléme de connexion", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_gamme", gammeId);
                params.put("id_user", userId);

                return params;
            }

        };


        ConnectionSingleton.getInstance(mContrext).addToRequestque(stringRequest);
        return true;
    }


    public boolean deleteUserFavoris(final String userId, final String gammeId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DELETE_USER_FAVOPRIS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("New record created successfully")) {
                            Toast.makeText(mContrext, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContrext, "Probléme de connexion", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gamme_id", gammeId);
                params.put("user_id", userId);

                return params;
            }

        };


        ConnectionSingleton.getInstance(mContrext).addToRequestque(stringRequest);
        return true;
    }


    public void getUserFavoris(String userID, final ImageView noDataFound, final TextView textView1, final TextView textView2) {

        final ProgressDialog progressDialog = new ProgressDialog(mContrext);
        progressDialog.setMessage("Chargement en cours");
        progressDialog.show();

        String request = URL.GET_USER_FAVORIS + "user_id=" + userID;
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Gamme> gammes = new ArrayList<>();


                int count = 0;
                while (count < response.length()) {
                    try {
                        Gamme gamme = new Gamme();
                        JSONObject jsonObject = response.getJSONObject(count);

                        gamme.setId(jsonObject.getString("id"));
                        gamme.setGamme(jsonObject.getString("gamme"));
                        gamme.setPrix(jsonObject.getString("prix"));
                        gamme.setBodyCarId(jsonObject.getString("body_car_id"));
                        gamme.setCaracteristiqueId(jsonObject.getString("caracteristique_id"));
                        gamme.setModelId(jsonObject.getString("model_id"));
                        gamme.setPictureUrl(jsonObject.getString("picture_url").trim());
                        gamme.setRaffinementId(jsonObject.getString("raffinement_id"));
                        gamme.setMotorisationId(jsonObject.getString("motorisation_id"));
                        gamme.setDescription(jsonObject.getString("description"));
                        gamme.setBrandId(jsonObject.getString("brand_id"));
                        gammes.add(gamme);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (gammes.size() == 0) {

                    ViewGroup.LayoutParams params = noDataFound.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    noDataFound.setLayoutParams(params);
                    textView1.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();

                } else {
                    final GammeRecyclarViewAdapter gammeRecyclarViewAdapter = new GammeRecyclarViewAdapter(mContrext, gammes);
                    recyclerView.setAdapter(gammeRecyclarViewAdapter);
                    textView1.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);


                    ViewGroup.LayoutParams params = textView1.getLayoutParams();
                    params.height = 0;
                    textView1.setLayoutParams(params);
                    textView2.setLayoutParams(params);

                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContrext, "Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(mContrext).addToRequestque(jsonArrayRequest);

    }
}
