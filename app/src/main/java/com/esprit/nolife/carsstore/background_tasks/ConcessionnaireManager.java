package com.esprit.nolife.carsstore.background_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.ConcessionnaireRecyclerAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Concessionnaire;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Souhaib on 18/11/2016.
 */

public class ConcessionnaireManager {
    ProgressDialog progressDialog;
    Context context;
    RecyclerView recyclerView;
    ArrayList<Concessionnaire> concessionnaires;

    public ConcessionnaireManager(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.concessionnaires = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");
    }

    public void getConcessionnaire() {

        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_CONCESSIONNAIRE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        Concessionnaire concessionnaire = new Concessionnaire();
                        JSONObject jsonObject = response.getJSONObject(count);
                        concessionnaire.setConcessionnaireId(jsonObject.getString("id"));
                        concessionnaire.setName(jsonObject.getString("name"));
                        concessionnaire.setNumTel(jsonObject.getString("num_tel"));
                        concessionnaire.setNumFax(jsonObject.getString("num_fax"));
                        concessionnaire.setAddress(jsonObject.getString("address"));
                        concessionnaire.setGoogleAddress(jsonObject.getString("google_address"));
                        concessionnaire.setWebSite(jsonObject.getString("web_site"));
                        concessionnaire.setLogo(jsonObject.getString("logo"));
                        concessionnaire.setDescription(jsonObject.getString("description"));
                        concessionnaires.add(concessionnaire);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ConcessionnaireRecyclerAdapter concessionnaireRecyclerAdapter = new ConcessionnaireRecyclerAdapter(context, concessionnaires);
                recyclerView.setAdapter(concessionnaireRecyclerAdapter);
                progressDialog.dismiss();


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
