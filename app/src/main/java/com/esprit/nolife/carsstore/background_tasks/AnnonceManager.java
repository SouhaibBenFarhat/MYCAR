package com.esprit.nolife.carsstore.background_tasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.AnnonceRecyclerAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Annonce;
import com.esprit.nolife.carsstore.entities.Picture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Firas on 20/12/2016.
 */

public class AnnonceManager {
    ProgressDialog progressDialog;
    Context context;
    RecyclerView recyclerView;
    ArrayList<Annonce> annonces;
    ImageView face, cote, arr, inter;
    String id, urlFace, urlCote, urlArr, urlInter;

    public AnnonceManager(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.annonces = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");
    }

    public AnnonceManager(Context context, String id, ImageView face, ImageView cote, ImageView arr, ImageView inter) {
        this.context = context;
        this.face = face;
        this.cote = cote;
        this.arr = arr;
        this.inter = inter;
        this.id = id;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Récupération des données du serveur");
    }

    public void getMesAnnonce() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_My_ANNONCE+user.getUid(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        Annonce annonce = new Annonce();
                        JSONObject jsonObject = response.getJSONObject(count);
                        annonce.setId(jsonObject.getString("id"));
                        annonce.setType(jsonObject.getString("type"));
                        annonce.setBrand(jsonObject.getString("brand"));
                        annonce.setModel(jsonObject.getString("model"));
                        annonce.setAnnee(jsonObject.getString("annee"));
                        annonce.setTransmission(jsonObject.getString("transmission"));
                        annonce.setPuissance(jsonObject.getString("puissance"));
                        annonce.setKilometrage(jsonObject.getString("kilometrage"));
                        annonce.setColor(jsonObject.getString("couleur"));
                        annonce.setNobrePortes(jsonObject.getString("portes"));
                        annonce.setCarburant(jsonObject.getString("carburant"));
                        annonce.setEtat(jsonObject.getString("condition_vehicule"));
                        annonce.setPrix(jsonObject.getString("prix"));
                        annonce.setPhoto(jsonObject.getString("annonce_photo_url"));
                        annonce.setNum_tel(jsonObject.getString("num_tel"));
                        annonce.setUser_id(jsonObject.getString("user_id"));
                        annonces.add(annonce);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                AnnonceRecyclerAdapter annonceRecyclerAdapter = new AnnonceRecyclerAdapter(context, annonces);
                recyclerView.setAdapter(annonceRecyclerAdapter);
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




    public void getAnnonce() {

        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_ALL_ANNONCE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        Annonce annonce = new Annonce();
                        JSONObject jsonObject = response.getJSONObject(count);
                        annonce.setId(jsonObject.getString("id"));
                        annonce.setType(jsonObject.getString("type"));
                        annonce.setBrand(jsonObject.getString("brand"));
                        annonce.setModel(jsonObject.getString("model"));
                        annonce.setAnnee(jsonObject.getString("annee"));
                        annonce.setTransmission(jsonObject.getString("transmission"));
                        annonce.setPuissance(jsonObject.getString("puissance"));
                        annonce.setKilometrage(jsonObject.getString("kilometrage"));
                        annonce.setColor(jsonObject.getString("couleur"));
                        annonce.setNobrePortes(jsonObject.getString("portes"));
                        annonce.setCarburant(jsonObject.getString("carburant"));
                        annonce.setEtat(jsonObject.getString("condition_vehicule"));
                        annonce.setPrix(jsonObject.getString("prix"));
                        annonce.setPhoto(jsonObject.getString("annonce_photo_url"));
                        annonce.setNum_tel(jsonObject.getString("num_tel"));
                        annonce.setUser_id(jsonObject.getString("user_id"));
                        annonces.add(annonce);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                AnnonceRecyclerAdapter annonceRecyclerAdapter = new AnnonceRecyclerAdapter(context, annonces);
                recyclerView.setAdapter(annonceRecyclerAdapter);
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

    public void getPhoto(String y) {

        String request = URL.GET_PHOTO_ANNONCE + "annoce_id=" + String.valueOf(y);
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

                if (pictures.size() != 0) {
                    Picasso.with(context).load(Uri.parse(pictures.get(0).getUrl())).into(face);
                    urlFace = pictures.get(0).getUrl();
                    Picasso.with(context).load(Uri.parse(pictures.get(1).getUrl())).into(cote);
                    urlCote = pictures.get(1).getUrl();
                    Picasso.with(context).load(Uri.parse(pictures.get(2).getUrl())).into(arr);
                    urlArr = pictures.get(2).getUrl();
                    Picasso.with(context).load(Uri.parse(pictures.get(3).getUrl())).into(inter);
                    urlInter = pictures.get(3).getUrl();
                    face.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showImage(urlFace);
                        }
                    });
                    cote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showImage(urlCote);
                        }
                    });
                    arr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showImage(urlArr);
                        }
                    });
                    inter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showImage(urlInter);
                        }
                    });
                } else {
                    face.setImageResource(R.drawable.vue_de_face);

                    cote.setImageResource(R.drawable.vue_de_cotee);
                    arr.setImageResource(R.drawable.vue_arriere);
                    inter.setImageResource(R.drawable.vue_interieuree);
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

    public void showImage(String url) {
        Dialog builder = new Dialog(context);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(url).into(imageView);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

//    public void showImageNoUrl(String image) {
//        Dialog builder = new Dialog(context);
//        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        builder.getWindow().setBackgroundDrawable(
//                new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                //nothing;
//            }
//        });
//        String s = "R.drawable." + image;
//        ImageView imageView = new ImageView(context);
//        imageView.setImageResource(Integer.valueOf(s));
//        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        builder.show();
//    }
}