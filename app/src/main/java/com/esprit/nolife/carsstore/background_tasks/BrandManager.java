package com.esprit.nolife.carsstore.background_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.BrandGridAdapter;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.PopularBrandsRecyclerViewAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Brand;
import com.esprit.nolife.carsstore.entities.BrandLogo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Souhaib on 11/11/2016.
 */

public class BrandManager {


    BrandGridAdapter brandGridAdapter;
    Context context;
    public final ArrayList<Brand> brands = new ArrayList<>();

    GridView gridView;
    Spinner spinnerBrand, spinnerModel;
    ProgressDialog progressDialog;
    long selected;
    RecyclerView popularBrands;

    public BrandManager(Context context, Spinner spinnerBrand, Spinner spinnerModel, long selected) {
        this.context = context;
        this.spinnerBrand = spinnerBrand;
        this.spinnerModel = spinnerModel;
        this.selected = selected;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");
    }

    public BrandManager(Context context, Spinner spinnerBrand, Spinner spinnerModel) {
        this.context = context;
        this.spinnerBrand = spinnerBrand;
        this.spinnerModel = spinnerModel;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }

    public BrandManager(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }

    public BrandManager(Context context, RecyclerView popularBrands) {
        this.context = context;
        this.popularBrands = popularBrands;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }

    public BrandManager(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }


    public void getBrand() {

        //   progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_BRANDS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        Brand brand = new Brand();
                        JSONObject jsonObject = response.getJSONObject(count);
                        brand.setBrandId(jsonObject.getString("id"));
                        brand.setBrand(jsonObject.getString("name"));
                        brand.setLogo(jsonObject.getString("logo"));
                        brand.setCover(jsonObject.getString("cover"));
                        brands.add(brand);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                BrandGridAdapter brandGridAdapter = new BrandGridAdapter(context, R.layout.brand_item, brands);
                gridView.setAdapter(brandGridAdapter);
                gridView.setNumColumns(3);
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

    public void getBrandName() {

        //   progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_BRANDS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        Brand brand = new Brand();
                        JSONObject jsonObject = response.getJSONObject(count);
                        brand.setBrandId(jsonObject.getString("id"));
                        brand.setBrand(jsonObject.getString("name"));
                        brand.setLogo(jsonObject.getString("logo"));
                        brand.setCover(jsonObject.getString("cover"));
                        brands.add(brand);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                List<String> spinnerArray = new ArrayList<String>();
                for (int i = 0; i < brands.size(); i++) {
                    spinnerArray.add(brands.get(i).brand);

                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinnerBrand.setAdapter(adapter);
                int x = (int) selected;
                int y = Integer.parseInt(brands.get(x).brandId);
                ModelsManager modelsManager = new ModelsManager(spinnerModel, context, y);
                modelsManager.getModelsBrand(y);


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

    public void getBrandNameModel() {

        //   progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_BRANDS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        Brand brand = new Brand();
                        JSONObject jsonObject = response.getJSONObject(count);
                        brand.setBrandId(jsonObject.getString("id"));
                        brand.setBrand(jsonObject.getString("name"));
                        brand.setLogo(jsonObject.getString("logo"));
                        brand.setCover(jsonObject.getString("cover"));
                        brands.add(brand);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                int x = (int) selected;
                int y = Integer.parseInt(brands.get(x).brandId);
                ModelsManager modelsManager = new ModelsManager(spinnerModel, context, y);
                modelsManager.getModelsBrand(y);


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

    public void getBrandLogo(int brandId, final ImageView img) {

        String request = URL.GET_BRAND_LOGO + "?brand_id=" + String.valueOf(brandId);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                BrandLogo brandLogo = new BrandLogo();

                int count = 0;
                while (count < response.length()) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(count);
                        brandLogo.setUrl(jsonObject.getString("logo"));
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Picasso.with(context).load(Uri.parse(brandLogo.getUrl())).into(img);

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


    public void getPopularBrands() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_BRANDS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < 11) {
                    try {
                        Brand brand = new Brand();
                        JSONObject jsonObject = response.getJSONObject(count);
                        brand.setBrandId(jsonObject.getString("id"));
                        brand.setBrand(jsonObject.getString("name"));
                        brand.setLogo(jsonObject.getString("logo"));
                        brand.setCover(jsonObject.getString("cover"));
                        brands.add(brand);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LinearLayoutManager featuredCarslayoutManager
                        = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                popularBrands.setLayoutManager(featuredCarslayoutManager);
                brands.remove(1);
                PopularBrandsRecyclerViewAdapter popularBrandsRecyclerViewAdapter = new PopularBrandsRecyclerViewAdapter(context, brands);
                popularBrands.setAdapter(popularBrandsRecyclerViewAdapter);
                popularBrands.setHasFixedSize(true);
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


    public void searchPopularBrand(final String brandName) {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_BRANDS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        Brand brand = new Brand();
                        JSONObject jsonObject = response.getJSONObject(count);
                        brand.setBrandId(jsonObject.getString("id"));
                        brand.setBrand(jsonObject.getString("name"));
                        brand.setLogo(jsonObject.getString("logo"));
                        brand.setCover(jsonObject.getString("cover"));
                        if (brand.getBrand().toLowerCase().contains(brandName.toLowerCase())) {
                            brands.add(brand);
                        }
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LinearLayoutManager featuredCarslayoutManager
                        = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                popularBrands.setLayoutManager(featuredCarslayoutManager);
                PopularBrandsRecyclerViewAdapter popularBrandsRecyclerViewAdapter = new PopularBrandsRecyclerViewAdapter(context, brands);
                popularBrands.setAdapter(popularBrandsRecyclerViewAdapter);

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
