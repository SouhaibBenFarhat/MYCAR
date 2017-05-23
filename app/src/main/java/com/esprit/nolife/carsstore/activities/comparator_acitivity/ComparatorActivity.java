package com.esprit.nolife.carsstore.activities.comparator_acitivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.background_tasks.BrandManager;
import com.esprit.nolife.carsstore.background_tasks.RatingManager;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Caracteristique;
import com.esprit.nolife.carsstore.entities.Gamme;
import com.esprit.nolife.carsstore.entities.Motorisation;
import com.esprit.nolife.carsstore.entities.Raffinement;
import com.esprit.nolife.carsstore.myapp.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ComparatorActivity extends AppCompatActivity {


    ImageView firstCarImage, secondCarImage;
    TextView firstCarName, secondCarName, comparatorTextView;
    Gamme firstCar, secondCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparator);


        comparatorTextView = (TextView) findViewById(R.id.comparator_title);
        ImageView image = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(getApplication()).load(Uri.parse("https://s-media-cache-ak0.pinimg.com/originals/56/f8/6c/56f86c6f2671bb359cb59ebd81ca9810.jpg"))
                .into(image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comparator);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                MyApplication app = (MyApplication) getApplication();
                app.clearComparator();
            }
        });

        MyApplication app = (MyApplication) getApplication();
        firstCar = app.getFirstCar();
        secondCar = app.getSecondCar();
        setupFirstCar();
        setupSecondCar();

        comparatorTextView.setText(firstCar.getGamme() + " VS " + secondCar.getGamme());


        ImageView firstGammeLogo = (ImageView) findViewById(R.id.first_gamme_brand_logo);
        ImageView secondGammeLogo = (ImageView) findViewById(R.id.second_gamme_brand_logo);


        BrandManager brandManager = new BrandManager(this);
        brandManager.getBrandLogo(Integer.parseInt(firstCar.getBrandId()), firstGammeLogo);
        brandManager.getBrandLogo(Integer.parseInt(secondCar.getBrandId()), secondGammeLogo);


    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


    public void setupFirstCar() {
        firstCarImage = (ImageView) findViewById(R.id.first_car_image);
        Picasso.with(getApplicationContext()).load(Uri.parse(firstCar.getPictureUrl())).into(firstCarImage);
        firstCarName = (TextView) findViewById(R.id.first_car_name);
        firstCarName.setText(firstCar.getGamme());
        setFirstGammeCaracteristique();
        setFirstGammeMotorisation();
        setFirstGammeRaffinement();

        RatingBar ratingBar = (RatingBar) findViewById(R.id.first_gamme_rating_bar);
        TextView ratingValue = (TextView) findViewById(R.id.first_gamme_rating_value);

        RatingManager ratingManager = new RatingManager(this);
        ratingManager.getRatingValue(firstCar.getModelId(), ratingBar, ratingValue);
    }

    public void setFirstGammeCaracteristique() {


        final TextView tvGammeCarroserrie = (TextView) findViewById(R.id.first_gamme_carrosserie);
        final TextView tvGammeNbPlace = (TextView) findViewById(R.id.first_gamme_nb_place);
        final TextView tvGammeNbPorte = (TextView) findViewById(R.id.first_gamme_nb_porte);
        final TextView tvGammeLongeur = (TextView) findViewById(R.id.first_gamme_longueur);
        final TextView tvGammeLargeur = (TextView) findViewById(R.id.first_gamme_largeur);
        final TextView tvGammeHauteur = (TextView) findViewById(R.id.first_gamme_hauteur);
        final TextView tvGammeVolumeCoffre = (TextView) findViewById(R.id.first_gamme_volume_coffre);


        String request = URL.GET_GAMME_CARACTERISTIQUE + "?caracteristique_id=" + String.valueOf(firstCar.getCaracteristiqueId());
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                Caracteristique caracteristique = new Caracteristique();
                while (count < response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(count);
                        caracteristique.setCaracteristiqueId(jsonObject.getString("id"));
                        caracteristique.setCarroserie(jsonObject.getString("carrosserie"));
                        caracteristique.setNombrePlace(jsonObject.getString("nombre_place"));
                        caracteristique.setNombrePorte(jsonObject.getString("nombre_porte"));
                        caracteristique.setLongueur(jsonObject.getString("longueur"));
                        caracteristique.setLargeur(jsonObject.getString("largeur"));
                        caracteristique.setHauteur(jsonObject.getString("hauteur"));
                        caracteristique.setVolumeCoffre(jsonObject.getString("volume_coffre"));
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tvGammeCarroserrie.setText(caracteristique.getCarroserie());
                    tvGammeNbPlace.setText(caracteristique.getNombrePlace());
                    tvGammeNbPorte.setText(caracteristique.getNombrePorte());
                    tvGammeLongeur.setText(caracteristique.getLongueur());
                    tvGammeLargeur.setText(caracteristique.getLargeur());
                    tvGammeHauteur.setText(caracteristique.getHauteur());
                    tvGammeVolumeCoffre.setText(caracteristique.getVolumeCoffre());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getApplicationContext()).addToRequestque(jsonArrayRequest);

    }

    public void setFirstGammeMotorisation() {
        final TextView tvGammeNbCylindre = (TextView) findViewById(R.id.first_gamme_nb_cylindre);
        final TextView tvGammeEnergie = (TextView) findViewById(R.id.first_gamme_energie);
        final TextView tvGammePuissanceFiscal = (TextView) findViewById(R.id.first_gamme_puissance_fiscal);
        final TextView tvGammePuissanceChDin = (TextView) findViewById(R.id.first_gamme_puissance_chdin);
        final TextView tvGammeCouple = (TextView) findViewById(R.id.first_gamme_couple);
        final TextView tvGammeCylindre = (TextView) findViewById(R.id.first_gamme_cylindre);
        final TextView tvGammeConsommationUrbaine = (TextView) findViewById(R.id.first_gamme_consommation_urbaine);
        final TextView tvGammeConsommationMixte = (TextView) findViewById(R.id.first_gamme_consommation_mixte);
        final TextView tvGammeZeroCent = (TextView) findViewById(R.id.first_gamme_zero_cent);
        final TextView tvGammeVitesseMax = (TextView) findViewById(R.id.first_gamme_vitesse_max);


        String request = URL.GET_GAMME_MOTORISATION + "?motorisation_id=" + String.valueOf(firstCar.getMotorisationId());
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                Motorisation motorisation = new Motorisation();
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
                        motorisation.setVitesseMax(jsonObject.getString("vitesse_max"));
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tvGammeNbCylindre.setText(motorisation.getNombreCylindre());
                    tvGammeEnergie.setText(motorisation.getEnergie());
                    tvGammePuissanceChDin.setText(motorisation.getPuissanceChdin());
                    tvGammePuissanceFiscal.setText(motorisation.getPuissanceFiscal());
                    tvGammeCouple.setText(motorisation.getCouple());
                    tvGammeCylindre.setText(motorisation.getCylindree());
                    tvGammeConsommationMixte.setText(motorisation.getConsommationMixte());
                    tvGammeConsommationUrbaine.setText(motorisation.getConsommationUrbaine());
                    tvGammeZeroCent.setText(motorisation.getZeroToCent());
                    tvGammeVitesseMax.setText(motorisation.getVitesseMax());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getApplicationContext()).addToRequestque(jsonArrayRequest);


    }

    public void setFirstGammeRaffinement() {

        final TextView tvGammeConnectivite = (TextView) findViewById(R.id.first_gamme_connectivite);
        final TextView tvGammeNombreAirbag = (TextView) findViewById(R.id.first_gamme_airbag);
        final TextView tvGammeJante = (TextView) findViewById(R.id.first_gamme_jante);
        final TextView tvGammeClimatisation = (TextView) findViewById(R.id.first_gamme_climatisation);


        String request = URL.GET_GAMME_RAFFINEMENT + "?raffinement_id=" + String.valueOf(firstCar.getRaffinementId());
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                Raffinement raffinement = new Raffinement();
                while (count < response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(count);
                        raffinement.setRaffinementId(jsonObject.getString("id"));
                        raffinement.setConnectivite(jsonObject.getString("connectivite"));
                        raffinement.setNombreAirbag(jsonObject.getString("nombre_airbag"));
                        raffinement.setClimatisation(jsonObject.getString("climatisation"));
                        raffinement.setJante(jsonObject.getString("jante"));
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tvGammeConnectivite.setText(raffinement.getConnectivite());
                    tvGammeNombreAirbag.setText(raffinement.getNombreAirbag());
                    tvGammeJante.setText(raffinement.getJante());
                    tvGammeClimatisation.setText(raffinement.getClimatisation());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getApplicationContext()).addToRequestque(jsonArrayRequest);

    }


    public void setupSecondCar() {
        secondCarImage = (ImageView) findViewById(R.id.second_car_image);
        Picasso.with(getApplicationContext()).load(Uri.parse(secondCar.getPictureUrl())).into(secondCarImage);
        secondCarName = (TextView) findViewById(R.id.second_car_name);
        secondCarName.setText(secondCar.getGamme());
        setSecondGammeCaracteristique();
        setSeconGammeMotorisation();
        setSecondGammeRaffinement();


        RatingBar ratingBar = (RatingBar) findViewById(R.id.second_gamme_rating_bar);
        TextView ratingValue = (TextView) findViewById(R.id.second_gamme_rating_value);

        RatingManager ratingManager = new RatingManager(this);
        ratingManager.getRatingValue(secondCar.getModelId(), ratingBar, ratingValue);
    }

    public void setSecondGammeCaracteristique() {


        final TextView tvGammeCarroserrie = (TextView) findViewById(R.id.second_gamme_carrosserie);
        final TextView tvGammeNbPlace = (TextView) findViewById(R.id.second_gamme_nb_place);
        final TextView tvGammeNbPorte = (TextView) findViewById(R.id.second_gamme_nb_porte);
        final TextView tvGammeLongeur = (TextView) findViewById(R.id.second_gamme_longueur);
        final TextView tvGammeLargeur = (TextView) findViewById(R.id.second_gamme_largeur);
        final TextView tvGammeHauteur = (TextView) findViewById(R.id.second_gamme_hauteur);
        final TextView tvGammeVolumeCoffre = (TextView) findViewById(R.id.second_gamme_volume_coffre);


        String request = URL.GET_GAMME_CARACTERISTIQUE + "?caracteristique_id=" + String.valueOf(secondCar.getCaracteristiqueId());
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                Caracteristique caracteristique = new Caracteristique();
                while (count < response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(count);
                        caracteristique.setCaracteristiqueId(jsonObject.getString("id"));
                        caracteristique.setCarroserie(jsonObject.getString("carrosserie"));
                        caracteristique.setNombrePlace(jsonObject.getString("nombre_place"));
                        caracteristique.setNombrePorte(jsonObject.getString("nombre_porte"));
                        caracteristique.setLongueur(jsonObject.getString("longueur"));
                        caracteristique.setLargeur(jsonObject.getString("largeur"));
                        caracteristique.setHauteur(jsonObject.getString("hauteur"));
                        caracteristique.setVolumeCoffre(jsonObject.getString("volume_coffre"));
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tvGammeCarroserrie.setText(caracteristique.getCarroserie());
                    tvGammeNbPlace.setText(caracteristique.getNombrePlace());
                    tvGammeNbPorte.setText(caracteristique.getNombrePorte());
                    tvGammeLongeur.setText(caracteristique.getLongueur());
                    tvGammeLargeur.setText(caracteristique.getLargeur());
                    tvGammeHauteur.setText(caracteristique.getHauteur());
                    tvGammeVolumeCoffre.setText(caracteristique.getVolumeCoffre());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getApplicationContext()).addToRequestque(jsonArrayRequest);

    }

    public void setSeconGammeMotorisation() {
        final TextView tvGammeNbCylindre = (TextView) findViewById(R.id.second_gamme_nb_cylindre);
        final TextView tvGammeEnergie = (TextView) findViewById(R.id.second_gamme_energie);
        final TextView tvGammePuissanceFiscal = (TextView) findViewById(R.id.second_gamme_puissance_fiscal);
        final TextView tvGammePuissanceChDin = (TextView) findViewById(R.id.second_gamme_puissance_chdin);
        final TextView tvGammeCouple = (TextView) findViewById(R.id.second_gamme_couple);
        final TextView tvGammeCylindre = (TextView) findViewById(R.id.second_gamme_cylindre);
        final TextView tvGammeConsommationUrbaine = (TextView) findViewById(R.id.second_gamme_consommation_urbaine);
        final TextView tvGammeConsommationMixte = (TextView) findViewById(R.id.second_gamme_consommation_mixte);
        final TextView tvGammeZeroCent = (TextView) findViewById(R.id.second_gamme_zero_cent);
        final TextView tvGammeVitesseMax = (TextView) findViewById(R.id.second_gamme_vitesse_max);


        String request = URL.GET_GAMME_MOTORISATION + "?motorisation_id=" + String.valueOf(secondCar.getMotorisationId());
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                Motorisation motorisation = new Motorisation();
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
                        motorisation.setVitesseMax(jsonObject.getString("vitesse_max"));
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tvGammeNbCylindre.setText(motorisation.getNombreCylindre());
                    tvGammeEnergie.setText(motorisation.getEnergie());
                    tvGammePuissanceChDin.setText(motorisation.getPuissanceChdin());
                    tvGammePuissanceFiscal.setText(motorisation.getPuissanceFiscal());
                    tvGammeCouple.setText(motorisation.getCouple());
                    tvGammeCylindre.setText(motorisation.getCylindree());
                    tvGammeConsommationMixte.setText(motorisation.getConsommationMixte());
                    tvGammeConsommationUrbaine.setText(motorisation.getConsommationUrbaine());
                    tvGammeZeroCent.setText(motorisation.getZeroToCent());
                    tvGammeVitesseMax.setText(motorisation.getVitesseMax());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getApplicationContext()).addToRequestque(jsonArrayRequest);
    }

    public void setSecondGammeRaffinement() {

        final TextView tvGammeConnectivite = (TextView) findViewById(R.id.second_gamme_connectivite);
        final TextView tvGammeNombreAirbag = (TextView) findViewById(R.id.second_gamme_airbag);
        final TextView tvGammeJante = (TextView) findViewById(R.id.second_gamme_jante);
        final TextView tvGammeClimatisation = (TextView) findViewById(R.id.second_gamme_climatisation);


        String request = URL.GET_GAMME_RAFFINEMENT + "?raffinement_id=" + String.valueOf(secondCar.getRaffinementId());
        JsonArrayRequest
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                Raffinement raffinement = new Raffinement();
                while (count < response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(count);
                        raffinement.setRaffinementId(jsonObject.getString("id"));
                        raffinement.setConnectivite(jsonObject.getString("connectivite"));
                        raffinement.setNombreAirbag(jsonObject.getString("nombre_airbag"));
                        raffinement.setClimatisation(jsonObject.getString("climatisation"));
                        raffinement.setJante(jsonObject.getString("jante"));
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tvGammeConnectivite.setText(raffinement.getConnectivite());
                    tvGammeNombreAirbag.setText(raffinement.getNombreAirbag());
                    tvGammeJante.setText(raffinement.getJante());
                    tvGammeClimatisation.setText(raffinement.getClimatisation());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getApplicationContext()).addToRequestque(jsonArrayRequest);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                MyApplication app = (MyApplication) getApplication();
                app.clearComparator();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication app = (MyApplication) getApplication();
        app.clearComparator();
    }
}
