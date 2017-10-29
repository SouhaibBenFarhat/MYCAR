package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.album_photo.PhotoGalleryActivity;
import com.esprit.nolife.carsstore.background_tasks.BrandManager;
import com.esprit.nolife.carsstore.background_tasks.FavorisManager;
import com.esprit.nolife.carsstore.background_tasks.GammeManager;
import com.esprit.nolife.carsstore.background_tasks.RatingManager;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Caracteristique;
import com.esprit.nolife.carsstore.entities.Gamme;
import com.esprit.nolife.carsstore.entities.Motorisation;
import com.esprit.nolife.carsstore.entities.Raffinement;
import com.esprit.nolife.carsstore.myapp.MyApplication;
import com.google.firebase.auth.FirebaseAuth;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class GammeDetailFragment extends Fragment {
    TextView gammeName, gammeDescription, gammePrix, tvFicheTechnique, tvGammeDetailPhoto, ratingValueTextView;
    ImageView gammeImg, gammeBrandLogoImage;
    GridView gridViewGammeDetail;
    AppCompatButton favorisButton, comparatorButton;
    RelativeLayout expandableRelativeLayoutFicheTechnique, expandableRelativeLayoutGammeDetailPhoto;
    Gamme gamme;
    RatingBar ratingSum;
    LinearLayout photoContainer;
    ProgressBar loadingProcess;
    AppCompatButton albumPhotoButton;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gamme_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadingProcess = (ProgressBar) getActivity().findViewById(R.id.loading_process);
        loadingProcess.setVisibility(View.INVISIBLE);
        gamme = (Gamme) getArguments().getSerializable("gamme");

        gammePrix = (TextView) getActivity().findViewById(R.id.gamme_prix);
        gammeDescription = (TextView) getActivity().findViewById(R.id.gamme_description);
        gammeName = (TextView) getActivity().findViewById(R.id.gamme_name);
        gammeImg = (ImageView) getActivity().findViewById(R.id.gamme_img);
        gammeBrandLogoImage = (ImageView) getActivity().findViewById(R.id.img_gamme_detail_brand_logo);
        Glide.with(getContext()).load(Uri.parse(gamme.getPictureUrl())).into(gammeImg);
        gammeName.setText(gamme.getGamme());
        gammeDescription.setText(gamme.getDescription());
        gammePrix.setText(gamme.getPrix());
        albumPhotoButton = (AppCompatButton) getActivity().findViewById(R.id.gamme_album_photo_button);
        photoContainer = (LinearLayout) getActivity().findViewById(R.id.photo_container);

        BrandManager brandManager = new BrandManager(getContext());
        brandManager.getBrandLogo(Integer.parseInt(gamme.getBrandId()), gammeBrandLogoImage);
        gridViewGammeDetail = (GridView) getActivity().findViewById(R.id.gv_gamme_album);

        ratingSum = (RatingBar) getActivity().findViewById(R.id.ratingSum);
        ratingValueTextView = (TextView) getActivity().findViewById(R.id.ratin_value_textview);

        RatingManager ratingManager = new RatingManager(getContext());
        ratingManager.getRatingValue(gamme.getModelId(), ratingSum, ratingValueTextView);

        favorisButton = (AppCompatButton) getActivity().findViewById(R.id.btn_favoris);

        favorisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavorisManager favorisManager = new FavorisManager(getActivity());
                favorisManager.addUserFavoris(FirebaseAuth.getInstance().getCurrentUser().getUid(), gamme.getId());
            }
        });
        tvGammeDetailPhoto = (TextView) getActivity().findViewById(R.id.tv_gamme_detail_photo);
        expandableRelativeLayoutGammeDetailPhoto = (RelativeLayout) getActivity().findViewById(R.id.photo_gamme_ex_layout);


        expandableRelativeLayoutFicheTechnique = (RelativeLayout) getActivity().findViewById(R.id.fiche_technique_ex_layout);
        tvFicheTechnique = (TextView) getActivity().findViewById(R.id.tv_fiche_technique);
        tvFicheTechnique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        AppCompatButton createPdfButton = (AppCompatButton) getActivity().findViewById(R.id.btn_telecharger_pdf);
        createPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });

        GammeManager gammeManager = new GammeManager(getContext());
        gammeManager.getGammePhoto(Integer.parseInt(gamme.getId()), gridViewGammeDetail, photoContainer);
        setGammeCaracteristique();
        setGammeMotorisation();
        setGammeRaffinement();


        albumPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(getActivity(), PhotoGalleryActivity.class);
                Bundle arg = new Bundle();
                arg.putSerializable("gamme",gamme);
                intent.putExtras(arg);
                getActivity().startActivity(intent);
            }
        });

        comparatorButton = (AppCompatButton) getActivity().findViewById(R.id.add_comparator_btn);
        comparatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyApplication app = (MyApplication) getActivity().getApplication();
                app.addToComparator(gamme, getActivity());

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        createPDF();

    }

    public void getPermission() {
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

        } else {
            createPDF();
        }
    }

    public void createPDF() {

        loadingProcess.setVisibility(View.INVISIBLE);
        TextView tvGammeCarroserrie = (TextView) getActivity().findViewById(R.id.gamme_carrosserie);
        TextView tvGammeNbPlace = (TextView) getActivity().findViewById(R.id.gamme_nb_place);
        TextView tvGammeNbPorte = (TextView) getActivity().findViewById(R.id.gamme_nb_porte);
        TextView tvGammeLongeur = (TextView) getActivity().findViewById(R.id.gamme_longueur);
        TextView tvGammeLargeur = (TextView) getActivity().findViewById(R.id.gamme_largeur);
        TextView tvGammeHauteur = (TextView) getActivity().findViewById(R.id.gamme_hauteur);
        TextView tvGammeVolumeCoffre = (TextView) getActivity().findViewById(R.id.gamme_volume_coffre);
        TextView tvGammeNbCylindre = (TextView) getActivity().findViewById(R.id.gamme_nb_cylindre);
        TextView tvGammeEnergie = (TextView) getActivity().findViewById(R.id.gamme_energie);
        TextView tvGammePuissanceFiscal = (TextView) getActivity().findViewById(R.id.gamme_puissance_fiscal);
        TextView tvGammePuissanceChDin = (TextView) getActivity().findViewById(R.id.gamme_puissance_chdin);
        TextView tvGammeCouple = (TextView) getActivity().findViewById(R.id.gamme_couple);
        TextView tvGammeCylindre = (TextView) getActivity().findViewById(R.id.gamme_cylindre);
        TextView tvGammeConsommationUrbaine = (TextView) getActivity().findViewById(R.id.gamme_consommation_urbaine);
        TextView tvGammeConsommationMixte = (TextView) getActivity().findViewById(R.id.gamme_consommation_mixte);
        TextView tvGammeZeroCent = (TextView) getActivity().findViewById(R.id.gamme_zero_cent);
        TextView tvGammeVitesseMax = (TextView) getActivity().findViewById(R.id.gamme_vitesse_max);
        TextView tvGammeConnectivite = (TextView) getActivity().findViewById(R.id.gamme_connectivite);
        TextView tvGammeNombreAirbag = (TextView) getActivity().findViewById(R.id.gamme_nb_airbag);
        TextView tvGammeJante = (TextView) getActivity().findViewById(R.id.gamme_jante);
        TextView tvGammeClimatisation = (TextView) getActivity().findViewById(R.id.gamme_climatisation);


        Document document = new Document();
        String storagePath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS) + "/" + gamme.getGamme() + ".pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(storagePath));
            document.open();


            document.add(new Paragraph(gamme.getGamme()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph(gamme.getDescription()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Prix: " + gamme.getPrix()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Carrosserie: " + tvGammeCarroserrie.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Nombre Place: " + tvGammeNbPlace.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Nombre Porte:" + tvGammeNbPorte.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Longueur: " + tvGammeLongeur.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Largeur: " + tvGammeLargeur.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Hauteur: " + tvGammeHauteur.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Volume coffre: " + tvGammeVolumeCoffre.getText().toString()));


            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Nombre cylindre: " + tvGammeEnergie.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Energie: " + tvGammePuissanceFiscal.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Puissance CHDIN: " + tvGammePuissanceChDin.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Couple Maxi: " + tvGammeCouple.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Nombre cylindre: " + tvGammeNbCylindre.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Cylindrée: " + tvGammeCylindre.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Consommation urbaine: " + tvGammeConsommationUrbaine.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Consommation Mixte: " + tvGammeConsommationMixte.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("0 - 100: " + tvGammeZeroCent.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Vitesse Max: " + tvGammeVitesseMax.getText().toString()));


            document.add(new Paragraph("Connectivitée: " + tvGammeConnectivite.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Nombre airbag: " + tvGammeNombreAirbag.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Jante: " + tvGammeJante.getText().toString()));
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());
            document.add(new Paragraph("Climatisation: " + tvGammeClimatisation.getText().toString()));

            document.close();

            loadingProcess.setVisibility(View.INVISIBLE);


            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Ouvrir le fichier?");
            alert.setMessage("");


            alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + gamme.getGamme() + ".pdf");
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(file), "application/pdf");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent intent = Intent.createChooser(target, "Open File");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {

                    }

                }
            });

            alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Toast.makeText(getContext(), "Fichier enregistrer dans Telechargments...", Toast.LENGTH_SHORT).show();

                }
            });

            alert.show();


        } catch (DocumentException e) {

        } catch (FileNotFoundException e) {
        }
    }

    public void setGammeCaracteristique() {


        final TextView tvGammeCarroserrie = (TextView) getActivity().findViewById(R.id.gamme_carrosserie);
        final TextView tvGammeNbPlace = (TextView) getActivity().findViewById(R.id.gamme_nb_place);
        final TextView tvGammeNbPorte = (TextView) getActivity().findViewById(R.id.gamme_nb_porte);
        final TextView tvGammeLongeur = (TextView) getActivity().findViewById(R.id.gamme_longueur);
        final TextView tvGammeLargeur = (TextView) getActivity().findViewById(R.id.gamme_largeur);
        final TextView tvGammeHauteur = (TextView) getActivity().findViewById(R.id.gamme_hauteur);
        final TextView tvGammeVolumeCoffre = (TextView) getActivity().findViewById(R.id.gamme_volume_coffre);


        String request = URL.GET_GAMME_CARACTERISTIQUE + "?caracteristique_id=" + String.valueOf(gamme.getCaracteristiqueId());
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
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getContext()).addToRequestque(jsonArrayRequest);

    }


    public void setGammeMotorisation() {
        final TextView tvGammeNbCylindre = (TextView) getActivity().findViewById(R.id.gamme_nb_cylindre);
        final TextView tvGammeEnergie = (TextView) getActivity().findViewById(R.id.gamme_energie);
        final TextView tvGammePuissanceFiscal = (TextView) getActivity().findViewById(R.id.gamme_puissance_fiscal);
        final TextView tvGammePuissanceChDin = (TextView) getActivity().findViewById(R.id.gamme_puissance_chdin);
        final TextView tvGammeCouple = (TextView) getActivity().findViewById(R.id.gamme_couple);
        final TextView tvGammeCylindre = (TextView) getActivity().findViewById(R.id.gamme_cylindre);
        final TextView tvGammeConsommationUrbaine = (TextView) getActivity().findViewById(R.id.gamme_consommation_urbaine);
        final TextView tvGammeConsommationMixte = (TextView) getActivity().findViewById(R.id.gamme_consommation_mixte);
        final TextView tvGammeZeroCent = (TextView) getActivity().findViewById(R.id.gamme_zero_cent);
        final TextView tvGammeVitesseMax = (TextView) getActivity().findViewById(R.id.gamme_vitesse_max);


        String request = URL.GET_GAMME_MOTORISATION + "?motorisation_id=" + String.valueOf(gamme.getMotorisationId());
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
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getContext()).addToRequestque(jsonArrayRequest);


    }

    public void setGammeRaffinement() {

        final TextView tvGammeConnectivite = (TextView) getActivity().findViewById(R.id.gamme_connectivite);
        final TextView tvGammeNombreAirbag = (TextView) getActivity().findViewById(R.id.gamme_nb_airbag);
        final TextView tvGammeJante = (TextView) getActivity().findViewById(R.id.gamme_jante);
        final TextView tvGammeClimatisation = (TextView) getActivity().findViewById(R.id.gamme_climatisation);


        String request = URL.GET_GAMME_RAFFINEMENT + "?raffinement_id=" + String.valueOf(gamme.getRaffinementId());
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
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ConnectionSingleton.getInstance(getContext()).addToRequestque(jsonArrayRequest);

    }


}
