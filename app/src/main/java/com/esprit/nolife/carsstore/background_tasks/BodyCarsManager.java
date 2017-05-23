package com.esprit.nolife.carsstore.background_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.BodyTypesGridAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.BodyType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Souhaib on 11/11/2016.
 */

public class BodyCarsManager {


    BodyTypesGridAdapter bodyTypesGridAdapter;
    Context context;
    public final ArrayList<BodyType> bodyTypes = new ArrayList<>();
    GridView gridView;
    ProgressDialog progressDialog;


    public BodyCarsManager(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }


    public void getBodyTypes() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_BODY_CARS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                int count = 0;
                while (count < response.length()) {
                    try {
                        BodyType bodyType = new BodyType();
                        JSONObject jsonObject = response.getJSONObject(count);
                        bodyType.setId(jsonObject.getString("id"));
                        bodyType.setBody(jsonObject.getString("body"));
                        bodyType.setLogo(jsonObject.getString("logo"));
                        bodyTypes.add(bodyType);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                bodyTypesGridAdapter = new BodyTypesGridAdapter(context, R.layout.body_type_item, bodyTypes);
                gridView.setAdapter(bodyTypesGridAdapter);
                gridView.setNumColumns(3);
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
