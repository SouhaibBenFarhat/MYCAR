package com.esprit.nolife.carsstore.background_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.AlbumGridAdapter;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.ModelRecyclarViewAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Model;
import com.esprit.nolife.carsstore.entities.Picture;
import com.esprit.nolife.carsstore.utils.PicassoImageLoader;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Souhaib on 11/11/2016.
 */

public class ModelsManager {


    ModelRecyclarViewAdapter modelRecyclarViewAdapter;
    Context context;
    public final ArrayList<Model> models = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Spinner spinnerModel;
    int selected;

    public ModelsManager(Spinner spinnerModel, Context context, int selected) {
        this.spinnerModel = spinnerModel;
        this.context = context;
        this.selected = selected;
    }

    public ModelsManager(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }

    public ModelsManager(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }


    public void getModels(int brandId) {
        progressDialog.show();
        String request = URL.GET_MODELS_BY_BRAND + "?brand_id=" + String.valueOf(brandId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                int count = 0;
                while (count < response.length()) {
                    try {
                        Model model = new Model();
                        JSONObject jsonObject = response.getJSONObject(count);
                        model.setId(jsonObject.getString("id"));
                        model.setBrandId(jsonObject.getString("brand_id"));
                        model.setName(jsonObject.getString("name"));
                        model.setPriceFrom(jsonObject.getString("price_from"));
                        model.setYear(jsonObject.getString("year"));
                        model.setPicture(new Picture(jsonObject.getString("picture_url")));
                        model.setDescription(jsonObject.getString("description"));
                        models.add(model);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ModelRecyclarViewAdapter modelRecyclarViewAdapter = new ModelRecyclarViewAdapter(context, models);
                recyclerView.setAdapter(modelRecyclarViewAdapter);
                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(context).addToRequestque(jsonArrayRequest);

    }

    public void getModelsName(int brandId) {
        progressDialog.show();
        String request = URL.GET_MODELS_BY_BRAND + "?brand_id=" + String.valueOf(brandId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                int count = 0;
                while (count < response.length()) {
                    try {
                        Model model = new Model();
                        JSONObject jsonObject = response.getJSONObject(count);
                        model.setId(jsonObject.getString("id"));
                        model.setBrandId(jsonObject.getString("brand_id"));
                        model.setName(jsonObject.getString("name"));
                        model.setPriceFrom(jsonObject.getString("price_from"));
                        model.setYear(jsonObject.getString("year"));
                        model.setPicture(new Picture(jsonObject.getString("picture_url")));
                        model.setDescription(jsonObject.getString("description"));
                        models.add(model);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public void getModelsBrand(int brandId) {
        String request = URL.GET_MODELS_BY_BRAND + "?brand_id=" + String.valueOf(brandId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                int count = 0;
                while (count < response.length()) {
                    try {
                        Model model = new Model();
                        JSONObject jsonObject = response.getJSONObject(count);
                        model.setId(jsonObject.getString("id"));
                        model.setBrandId(jsonObject.getString("brand_id"));
                        model.setName(jsonObject.getString("name"));
                        model.setPriceFrom(jsonObject.getString("price_from"));
                        model.setYear(jsonObject.getString("year"));
                        model.setPicture(new Picture(jsonObject.getString("picture_url")));
                        model.setDescription(jsonObject.getString("description"));
                        models.add(model);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<String> spinnerArray = new ArrayList<String>();
                if (models.size() != 0) {

                    for (int i = 0; i < models.size(); i++) {
                        spinnerArray.add(models.get(i).getName());

                    }


                } else {
                    spinnerArray.add("none");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinnerModel.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(context).addToRequestque(jsonArrayRequest);

    }


    public void getModelPhoto(int modelId, final GridView gridView, final RelativeLayout relativeLayout, final LinearLayout linearLayout) {

        String request = URL.GET_PHOTO_BY_MODEL + "?model_id=" + String.valueOf(modelId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Picture> pictures = new ArrayList<>();
                int count = 0;

                while (count < response.length()) {
                    try {
                        Picture picture = new Picture();
                        JSONObject jsonObject = response.getJSONObject(count);
                        picture.setUrl(jsonObject.getString("url"));
                        pictures.add(picture);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                AlbumGridAdapter brandGridAdapter = new AlbumGridAdapter(context, R.layout.album_item, pictures);
                gridView.setAdapter(brandGridAdapter);
                gridView.setNumColumns(5);

                if (pictures.size() == 0) {
                    relativeLayout.getLayoutParams().height = 0;
                    linearLayout.getLayoutParams().height = 0;

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(context).addToRequestque(jsonArrayRequest);

    }


    public void getModelPhoto(int modelId, final ScrollGalleryView scrollGalleryView) {

        String request = URL.GET_PHOTO_BY_MODEL + "?model_id=" + String.valueOf(modelId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Picture> pictures = new ArrayList<>();
                int count = 0;

                while (count < response.length()) {
                    try {
                        Picture picture = new Picture();
                        JSONObject jsonObject = response.getJSONObject(count);
                        picture.setUrl(jsonObject.getString("url"));
                        pictures.add(picture);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<MediaInfo> infos = new ArrayList<>(pictures.size());
                for (Picture url : pictures) {
                    infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url.getUrl())));
                }
                scrollGalleryView
                        .setThumbnailSize(100)
                        .setZoom(true)
                        .setFragmentManager(((FragmentActivity) context).getSupportFragmentManager())
                        .addMedia(infos);


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
