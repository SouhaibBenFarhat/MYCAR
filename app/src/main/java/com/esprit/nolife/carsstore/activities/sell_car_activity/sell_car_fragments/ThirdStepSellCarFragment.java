package com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.mes_annonces_acitivity.MesAnnoncesActivity;
import com.esprit.nolife.carsstore.background_tasks.AnnonceController;
import com.esprit.nolife.carsstore.custom_implementation.CustomViewPager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdStepSellCarFragment extends Fragment {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public ThirdStepSellCarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third_step_sell_car, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefs = getActivity().getPreferences(0);
        editor = prefs.edit();


        final String type = prefs.getString("type", "auto");
        final String brand = prefs.getString("brand", "none");
        final String model = prefs.getString("model", "none");
        final String transmition = prefs.getString("transmition", "none");
        final String condition = prefs.getString("condition", "none");
        final String couleur = prefs.getString("couleur", "none");
        final String prix = prefs.getString("prix", "none");
        final String kilomertage = prefs.getString("kilomertage", "none");
        final String faceUrl = prefs.getString("vueArriere", "non");
        final String coteUrl = prefs.getString("vueCote", "non");
        final String arrierUrl = prefs.getString("vueArriere", "non");
        final String internUrl = prefs.getString("vueInterieur", "non");

        String description = brand + "produit des voitures connues pour le qualité dans" +
                "toute sa gamme, comme le prouve celle-ci. Cette " + condition + " " +
                model + " possède une boite de vitesse " + transmition + " pour un prix " +
                "exceptionnel de " + prix + " .Ne manquez pas cette maghnifique opportunité." +
                " Merci d'avance de mentionner Car Store lorsque vous contactez le vendeur!";

        final CustomViewPager viewpager = (CustomViewPager) getActivity().findViewById(R.id.viewpager);
        Button addPost = (Button) getActivity().findViewById(R.id.btn_post_add);
        final EditText annee = (EditText) getActivity().findViewById(R.id.et_anne_annonce);
        final EditText puissance = (EditText) getActivity().findViewById(R.id.et_puissance_annonce);
        final EditText numTel = (EditText) getActivity().findViewById(R.id.et_numTel_Annonce);
        final Spinner spinnerCarburant = (Spinner) getActivity().findViewById(R.id.sp_carburant_annonce);
        final Spinner spinnerPortes = (Spinner) getActivity().findViewById(R.id.sp_nbPort_annonce);
        final TextView Description = (TextView) getActivity().findViewById(R.id.et_description_annonce);


        Description.setText(description);


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String queryFace = null;
                String queryCote = null;
                String queryArr = null;
                String queryInt = null;
                String queryBrand = null;
                String queryModel = null;
                try {
                    queryFace = URLEncoder.encode(faceUrl, "utf-8");
                    queryCote = URLEncoder.encode(coteUrl, "utf-8");
                    queryArr = URLEncoder.encode(arrierUrl, "utf-8");
                    queryInt = URLEncoder.encode(internUrl, "utf-8");
                    queryBrand = URLEncoder.encode(brand.trim(), "utf-8");
                    queryModel = URLEncoder.encode(model.trim(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String urll = URL.SET_Annonce + "brand=" + "'" + queryBrand
                        + "'&&model=" + "'" + queryModel
                        + "'&&transmition=" + "'" + transmition
                        + "'&&condition=" + "'" + condition
                        + "'&&couleur=" + "'" + couleur
                        + "'&&prix=" + "'" + prix
                        + "'&&annee=" + "'" + annee.getText().toString()
                        + "'&&puissance=" + "'" + puissance.getText().toString()
                        + "'&&portes=" + "'" + spinnerPortes.getSelectedItem().toString()
                        + "'&&num_tel=" + "'" + numTel.getText().toString()
                        + "'&&carburant=" + "'" + spinnerCarburant.getSelectedItem().toString()
                        + "'&&type=" + "'" + type
                        + "'&&face=" + "'" + queryFace//faceUrl//encodeFace
                        + "'&&arriere=" + "'" + queryArr
                        + "'&&cote=" + "'" + queryCote
                        + "'&&intern=" + "'" + queryInt
                        + "'&&user_id=" + "'" + user.getUid().toString()
                        + "'&&kilometrage=" + "'" + kilomertage + "'";

                System.out.println(urll);
                System.out.println(brand.trim());
                StringRequest request = new StringRequest(Request.Method.GET, urll, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        // parameters.put("brand", constructeurSpinner.getSelectedItem().toString());
                        parameters.put("type", "LandRover");
                        parameters.put("brand", "LandRover");

                        return parameters;
                    }
                };


                AnnonceController.getmInstance(getActivity()).addToRequestQue(request);
                Intent intent = new Intent(getActivity(), MesAnnoncesActivity.class);
                startActivity(intent);
                //  viewpager.setCurrentItem(0);
            }
        });
    }


}
