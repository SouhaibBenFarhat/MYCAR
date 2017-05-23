package com.esprit.nolife.carsstore.background_tasks;

import android.content.Context;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Souhaib on 30/12/2016.
 */

public class RatingManager {


    Context mContext;

    public RatingManager(Context mContext) {


        this.mContext = mContext;
    }


    public void insertModelRating(final String modelId, final String userId, final String value) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.INSERT_MODEL_RATING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Probléme de connexion", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("model_id", modelId);
                params.put("user_id", userId);
                params.put("value", value);

                return params;
            }

        };


        ConnectionSingleton.getInstance(mContext).addToRequestque(stringRequest);


    }


    public void getRatingValue(final String modelId, final RatingBar ratingBar, final TextView textView) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.GET_RATING_VALUE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("")) {
                            ratingBar.setRating(Float.parseFloat(response));
                            if (response.length()>4){
                                textView.setText(response.substring(0,3)+"/5");
                            }else{
                                textView.setText(response+"/5");
                            }

                        } else {
                            ratingBar.setRating(0);
                            textView.setText("0");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Probléme de connexion", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("model_id", modelId);
                return params;
            }

        };


        ConnectionSingleton.getInstance(mContext).addToRequestque(stringRequest);


    }






    public void getUserRating(final String modelId, final String userId, final RatingBar ratingBar, final TextView textView) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.GET_USER_RATING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals("")) {
                            ratingBar.setRating(Float.parseFloat(response));
                            ratingBar.setIsIndicator(true);
                            textView.setText(response+ "/5");
                        } else {
                            ratingBar.setRating(0);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Probléme de connexion", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("model_id", modelId);
                params.put("user_id", userId);
                return params;
            }

        };


        ConnectionSingleton.getInstance(mContext).addToRequestque(stringRequest);


    }

}
