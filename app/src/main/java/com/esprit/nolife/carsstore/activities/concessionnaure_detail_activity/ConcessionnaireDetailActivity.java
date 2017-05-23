package com.esprit.nolife.carsstore.activities.concessionnaure_detail_activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.entities.Concessionnaire;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ConcessionnaireDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView imgConcessionnaire;
    TextView concessionnaireName, descriptionConcessionnaire, addressConcessionnaire, phoneConcessionnaire, faxConcessionnaire, webConcessionnaire;
    Concessionnaire concessionnaire;
    LinearLayout mainLayout;
    ViewGroup container;
    AppCompatButton btnWebSite, btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concessionnaire_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_model);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgConcessionnaire = (ImageView) findViewById(R.id.backdrop);
        concessionnaireName = (TextView) findViewById(R.id.concessionnaire_name);
        descriptionConcessionnaire = (TextView) findViewById(R.id.concessionnaire_description);
        addressConcessionnaire = (TextView) findViewById(R.id.concessionnaire_address);
        phoneConcessionnaire = (TextView) findViewById(R.id.concessionnaire_phone_number);
        faxConcessionnaire = (TextView) findViewById(R.id.concessionnaire_fax_number);
        webConcessionnaire = (TextView) findViewById(R.id.concessionnaire_web_page);
        btnCall = (AppCompatButton) findViewById(R.id.btn_call);
        btnWebSite = (AppCompatButton) findViewById(R.id.btn_web_site);


        Bundle arg = getIntent().getExtras();
        concessionnaire = (Concessionnaire) arg.getSerializable("concessionnaire");
        if (arg != null) {
            Picasso.with(this).load(Uri.parse(concessionnaire.getLogo())).resize(1200, 800).into(imgConcessionnaire);
            concessionnaireName.setText(concessionnaire.getName());
            descriptionConcessionnaire.setText(concessionnaire.getDescription());
            addressConcessionnaire.setText(concessionnaire.getAddress());
            phoneConcessionnaire.setText(concessionnaire.getNumTel());
            faxConcessionnaire.setText(concessionnaire.getNumFax());
            webConcessionnaire.setText(concessionnaire.getWebSite());


            btnWebSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = concessionnaire.getWebSite();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int permissionCheck = ContextCompat.checkSelfPermission(ConcessionnaireDetailActivity.this, Manifest.permission.CALL_PHONE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                ConcessionnaireDetailActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                123);
                    } else {
                        startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + concessionnaire.getNumTel())));
                    }
                }
            });

        }


      //  SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

      //  fragment.getMapAsync(this);


        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
//        final LockableScrollView mainScrollView = (LockableScrollView) findViewById(R.id.main_scrollview);
//        final ImageView transparentImageView = (ImageView) findViewById(R.id.transparent_image);

//        mainLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mainScrollView.setScrollingEnabled(true);
//                return false;
//            }
//        });
//
//        transparentImageView.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                System.out.println("grid touch");
//                // Disallow the touch request for parent scroll on touch of
//                // child view
//                mainScrollView.setScrollingEnabled(false);
//                return false;
//            }
//        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        try {

            Geocoder selected_place_geocoder = new Geocoder(this);
            List<Address> address;

            address = selected_place_geocoder.getFromLocationName(concessionnaire.getGoogleAddress(), 5);
            if (address == null) {

            } else {
                Address location = address.get(0);
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                LatLng marker = new LatLng(lat, lng);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));
                googleMap.addMarker(new MarkerOptions().title(concessionnaire.getName()).position(marker));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
