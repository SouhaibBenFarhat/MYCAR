package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.entities.Concessionnaire;
import com.esprit.nolife.carsstore.custom_implementation.LockableScrollView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ConcessionnaireDetailFragment extends Fragment implements OnMapReadyCallback {

    ImageView imgConcessionnaire;
    TextView concessionnaireName, descriptionConcessionnaire, addressConcessionnaire, phoneConcessionnaire, faxConcessionnaire, webConcessionnaire;
    Concessionnaire concessionnaire;
    LinearLayout mainLayout;
    ViewGroup container;
    AppCompatButton btnWebSite, btnCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.container = container;
        return inflater.inflate(R.layout.fragment_concessionnaires_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        imgConcessionnaire = (ImageView) getActivity().findViewById(R.id.concessionnaire_img);
        concessionnaireName = (TextView) getActivity().findViewById(R.id.concessionnaire_name);
        descriptionConcessionnaire = (TextView) getActivity().findViewById(R.id.concessionnaire_description);
        addressConcessionnaire = (TextView) getActivity().findViewById(R.id.concessionnaire_address);
        phoneConcessionnaire = (TextView) getActivity().findViewById(R.id.concessionnaire_phone_number);
        faxConcessionnaire = (TextView) getActivity().findViewById(R.id.concessionnaire_fax_number);
        webConcessionnaire = (TextView) getActivity().findViewById(R.id.concessionnaire_web_page);
        btnCall = (AppCompatButton) getActivity().findViewById(R.id.btn_call);
        btnWebSite = (AppCompatButton) getActivity().findViewById(R.id.btn_web_site);


        Bundle arg = getArguments();
        concessionnaire = (Concessionnaire) arg.getSerializable("concessionnaire");
        if (arg != null) {
            Picasso.with(getContext()).load(Uri.parse(concessionnaire.getLogo())).resize(1200, 800).into(imgConcessionnaire);
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
                    int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                getActivity(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                123);
                    } else {
                        startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + concessionnaire.getNumTel())));
                    }
                }
            });

        }


        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);


        mainLayout = (LinearLayout) getActivity().findViewById(R.id.main_layout);
        final LockableScrollView mainScrollView = (LockableScrollView) getActivity().findViewById(R.id.main_scrollview);
        final ImageView transparentImageView = (ImageView) getActivity().findViewById(R.id.transparent_image);

        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainScrollView.setScrollingEnabled(true);
                return false;
            }
        });

        transparentImageView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("grid touch");
                // Disallow the touch request for parent scroll on touch of
                // child view
                mainScrollView.setScrollingEnabled(false);
                return false;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        try {

            Geocoder selected_place_geocoder = new Geocoder(getContext());
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
}
