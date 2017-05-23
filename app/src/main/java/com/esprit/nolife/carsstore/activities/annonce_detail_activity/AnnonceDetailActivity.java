package com.esprit.nolife.carsstore.activities.annonce_detail_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.background_tasks.AnnonceManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class AnnonceDetailActivity extends AppCompatActivity {

    ImageView imageView, face, cote, arr, inter,imedit;
    TextView tvedit,tvBrand, tvEtat, tvTransmition, tvKilometrage, tvCarburant, tvPrix, tvDescription, tvPortes, tvPuissance, tvAnnee, tvColor;
    Button call, share,btnedit;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_annonce_detail);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        imageView = (ImageView) findViewById(R.id.iv_Dannonce);
        imedit = (ImageView) findViewById(R.id.img_edit_post);
          tvBrand = (TextView) findViewById(R.id.tv_Dbrand_annoce);
        tvEtat = (TextView) findViewById(R.id.tv_Detat_annonce);
        tvTransmition = (TextView) findViewById(R.id.tv_Dtransmission_annonce);
        tvKilometrage = (TextView) findViewById(R.id.tv_Dkilometrage_annonce);
        tvCarburant = (TextView) findViewById(R.id.tv_Dcarburant_annonce);
        tvPrix = (TextView) findViewById(R.id.tv_dprice_annoce);
        tvDescription = (TextView) findViewById(R.id.tv_ddescription_annonce);
        tvPortes = (TextView) findViewById(R.id.tv_dportes);
        tvPuissance = (TextView) findViewById(R.id.tv_dpuissance);
        tvColor = (TextView) findViewById(R.id.tv_dcolor);
        tvAnnee = (TextView) findViewById(R.id.tv_dannee);
        face = (ImageView) findViewById(R.id.iv_dface);
        cote = (ImageView) findViewById(R.id.iv_dcote);
        arr = (ImageView) findViewById(R.id.iv_darr);
        inter = (ImageView) findViewById(R.id.iv_dinter);
        call = (Button) findViewById(R.id.btn_Dcall);
        share = (Button) findViewById(R.id.btn_dshare);
        String y = getIntent().getStringExtra("id");
        AnnonceManager annonceManager = new AnnonceManager(this, y, face, cote, arr, inter);
        annonceManager.getPhoto(y);



        Picasso.with(this).load(Uri.parse(getIntent().getStringExtra("photo"))).into(imageView);
        tvBrand.setText(getIntent().getStringExtra("model"));
        tvEtat.setText(getIntent().getStringExtra("etat"));
        tvPortes.setText(getIntent().getStringExtra("portes"));
        tvTransmition.setText(getIntent().getStringExtra("transmission"));
        tvKilometrage.setText(getIntent().getStringExtra("kilometrage") + " KM");
        tvCarburant.setText(getIntent().getStringExtra("carburant"));
        tvPuissance.setText(getIntent().getStringExtra("puissance") + " CH");
        tvPrix.setText(getIntent().getStringExtra("prix"));
        tvColor.setText(getIntent().getStringExtra("color"));
        tvAnnee.setText(getIntent().getStringExtra("annee"));

        String description = getIntent().getStringExtra("brand") + "produit des voitures connues pour le qualité dans" +
                "toute sa gamme, comme le prouve celle-ci. Cette " + getIntent().getStringExtra("etat") + " " +
                getIntent().getStringExtra("model") + " possède une boite de vitesse " + getIntent().getStringExtra("transmission") + " pour un prix " +
                "exceptionnel de " + getIntent().getStringExtra("prix") + " .Ne manquez pas cette maghnifique opportunité." +
                " Merci d'avance de mentionner Car Store lorsque vous contactez le vendeur!";
        tvDescription.setText(description);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dial = new Intent();
                dial.setAction("android.intent.action.DIAL");
                dial.setData(Uri.parse("tel:" + getIntent().getStringExtra("num_tel")));
                startActivity(dial);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShareDialog shareDialog;
                FacebookSdk.sdkInitialize(AnnonceDetailActivity.this);
                shareDialog = new ShareDialog(AnnonceDetailActivity.this);
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(getIntent().getStringExtra("model"))
                        .setContentDescription(
                                tvDescription.getText().toString())
                        .setContentUrl(Uri.parse(getIntent().getStringExtra("photo"))).build();
                shareDialog.show(linkContent);
                //shareFacebook();
            }
        });


    }

    public void shareFacebook() {
        List<String> permissionNeeded = Arrays.asList("publish_actions");
        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(this, permissionNeeded);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image).setCaption(tvDescription.getText().toString())
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareApi.share(content, null);
            }

            @Override
            public void onCancel() {
                System.out.println("cancel");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println(error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
