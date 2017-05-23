package com.esprit.nolife.carsstore.background_tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.FilterFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.GammeDetailFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.AlbumGridAdapter;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.GammeRecyclarViewAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Caracteristique;
import com.esprit.nolife.carsstore.entities.Gamme;
import com.esprit.nolife.carsstore.entities.Motorisation;
import com.esprit.nolife.carsstore.entities.Picture;
import com.esprit.nolife.carsstore.entities.Raffinement;
import com.esprit.nolife.carsstore.utils.PicassoImageLoader;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Souhaib on 12/11/2016.
 */

public class GammeManager {


    GammeRecyclarViewAdapter gammeRecyclarViewAdapter;
    Context context;
    public final ArrayList<Gamme> gammes = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;


    public GammeManager(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }

    public GammeManager(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");

    }


    public void getGammes(int modelId) {

        String request = URL.GET_GAMME_BY_MODEL + "?model_id=" + String.valueOf(modelId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


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
                GammeRecyclarViewAdapter modelRecyclarViewAdapter = new GammeRecyclarViewAdapter(context, gammes);
                recyclerView.setAdapter(modelRecyclarViewAdapter);


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

    public void getAllGammes(final AutoCompleteTextView autoCompleteTextView, final AppCompatButton appCompatButton) {

        String request = URL.GET_ALL_GAMMES;

        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String[] modelName = new String[response.length()];

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
                        modelName[count] = gamme.getGamme();
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.auto_complete_textview, modelName);
                autoCompleteTextView.setAdapter(arrayAdapter);
                autoCompleteTextView.setThreshold(1);

                appCompatButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (Gamme g : gammes) {
                                    if (g.getGamme().equals(autoCompleteTextView.getText().toString())) {
                                        Bundle arg = new Bundle();
                                        GammeDetailFragment gammeFragment = new GammeDetailFragment();
                                        arg.putSerializable("gamme", g);
                                        gammeFragment.setArguments(arg);
                                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                                .add(R.id.home_container, gammeFragment).addToBackStack(null)
                                                .commit();
                                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                                    }
                                }
                            }
                        }
                );

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


    public void getGammesName(int modelId) {

        final ArrayList<Gamme> gammeNames = new ArrayList<>();
        String request = URL.GET_GAMME_NAME_BY_MODEL + "?model_id=" + String.valueOf(modelId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                int count = 0;
                while (count < response.length()) {
                    try {
                        Gamme gamme = new Gamme();
                        JSONObject jsonObject = response.getJSONObject(count);

                        gamme.setId(jsonObject.getString("id"));
                        gamme.setGamme(jsonObject.getString("gamme"));
                        gamme.setPrix(jsonObject.getString("prix"));
                        gamme.setModelId(jsonObject.getString("model_id"));
                        gammeNames.add(gamme);
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


    public void getGammeCaracteristique(int caracteristiqueId, final Caracteristique caracteristique) {

        String request = URL.GET_GAMME_CARACTERISTIQUE + "?caracteristique_id=" + String.valueOf(caracteristiqueId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                int count = 0;
                while (count < response.length()) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(count);

                        caracteristique.setCaracteristiqueId(jsonObject.getString("id"));
                        caracteristique.setCarroserie(jsonObject.getString("carrosserie"));
                        caracteristique.setNombrePlace(jsonObject.getString("nombre_place"));
                        caracteristique.setLongueur(jsonObject.getString("longueur"));
                        caracteristique.setLargeur(jsonObject.getString("largeur"));
                        caracteristique.setHauteur(jsonObject.getString("hauteur"));
                        caracteristique.setVolumeCoffre(jsonObject.getString("volume_coffre"));


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


    public Motorisation getGammeMotorisation(int motorisationId) {

        final Motorisation motorisation = new Motorisation();
        String request = URL.GET_GAMME_MOTORISATION + "?motorisation_id=" + String.valueOf(motorisationId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(count);

                        motorisation.setMotorisationId(jsonObject.getString("id"));
                        motorisation.setNombreCylindre(jsonObject.getString("nombre_cylindre"));
                        motorisation.setEnergie(jsonObject.getString("energie"));
                        motorisation.setPuissanceFiscal(jsonObject.getString("puissance_fiscal"));
                        motorisation.setPuissanceChdin(jsonObject.getString("puissance_chdin"));
                        motorisation.setCouple(jsonObject.getString("couple"));
                        motorisation.setCylindree(jsonObject.getString("cylindree"));
                        motorisation.setConsommationUrbaine(jsonObject.getString("consommation_urbaine"));
                        motorisation.setConsommationMixte(jsonObject.getString("consommation_mixte"));
                        motorisation.setZeroToCent(jsonObject.getString("zero_cent"));

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
        return motorisation;
    }

    public void getGammeRaffinement(int raffinementId, final Raffinement raffinement) {

        String request = URL.GET_GAMME_RAFFINEMENT + "?raffinement_id=" + String.valueOf(raffinementId);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                int count = 0;
                while (count < response.length()) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(count);

                        raffinement.setRaffinementId(jsonObject.getString("id"));
                        raffinement.setConnectivite(jsonObject.getString("connectivite"));
                        raffinement.setNombreAirbag(jsonObject.getString("nombre_airbag"));
                        raffinement.setJante(jsonObject.getString("jante"));
                        raffinement.setClimatisation(jsonObject.getString("climatisation"));
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

    public void getGammePhoto(int gammeId, final GridView gridView, final LinearLayout linearLayout) {

        final String request = URL.GET_PHOTO_BY_GAMME + "?gamme_id=" + String.valueOf(gammeId);
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
                        picture.setUrl(jsonObject.getString("url").replace("<br", ""));
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
                    linearLayout.getLayoutParams().height = 0;
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

    public void getGammePhoto(int gammeId, final ScrollGalleryView scrollGalleryView) {

        final String request = URL.GET_PHOTO_BY_GAMME + "?gamme_id=" + String.valueOf(gammeId);
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
                        picture.setUrl(jsonObject.getString("url").replace("<br", ""));
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

    public void fetchFilter(final RecyclerView recyclerView, final TextView textView, final TextView textView2) {
        final ArrayList<Gamme> gms = new ArrayList<>();
        String request = URL.FILTER + "brand_id=" + FilterFragment.selectedBrand.brandId
                + "&" + "body_cars_id=" + FilterFragment.selectedBodyType.getId() + "&" + "fuel_type=" + FilterFragment.carburant
                + "&" + "prix_max=" + FilterFragment.prixMax + "000" + "&" + "prix_min=" + FilterFragment.prixMin + "000";
        System.out.println(request);
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


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
                        gms.add(gamme);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (gms.size() > 0) {
                    textView.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 2);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    GammeRecyclarViewAdapter gammeRecyclarViewAdapter = new GammeRecyclarViewAdapter(context, gms);
                    recyclerView.setAdapter(gammeRecyclarViewAdapter);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
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

}
