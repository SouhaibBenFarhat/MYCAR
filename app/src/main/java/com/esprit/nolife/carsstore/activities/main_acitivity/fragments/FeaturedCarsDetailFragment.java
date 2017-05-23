package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.MainActivity;
import com.esprit.nolife.carsstore.entities.FeaturedCar;
import com.esprit.nolife.carsstore.utils.html_parser.DataFromTheWeb;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;


public class FeaturedCarsDetailFragment extends Fragment {

    TextView featuredCarTitle, featuredCarDescription, featuredCarDatePub;
    ImageView featuredCarImage;
    View view;
    FeaturedCar featuredCar;
    ActionBar actionBar;
    AppCompatButton facebookShareButton, plusDetailButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = container;
        return inflater.inflate(R.layout.fragment_featured_cars_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        featuredCar = (FeaturedCar) getArguments().get("featredCar");


        facebookShareButton = (AppCompatButton) getActivity().findViewById(R.id.btn_facebook_share);
        plusDetailButton = (AppCompatButton) getActivity().findViewById(R.id.btn_more_detail);


        facebookShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShareDialog shareDialog;
                FacebookSdk.sdkInitialize(getActivity());
                shareDialog = new ShareDialog(getActivity());
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(featuredCar.getTitle())
                        .setContentDescription(
                                featuredCar.getDescription())
                        .setContentUrl(Uri.parse(featuredCar.getLink())).build();
                shareDialog.show(linkContent);


            }
        });


        featuredCarTitle = (TextView) getActivity().findViewById(R.id.tv_featured_car_title);
        featuredCarDatePub = (TextView) getActivity().findViewById(R.id.tv_featured_car_date);
        featuredCarImage = (ImageView) getActivity().findViewById(R.id.img_featured_car);
        featuredCarDescription = (TextView) getActivity().findViewById(R.id.txv_featured_car_description);
        showActionBar();
        System.out.println(featuredCar.getThumbnailUrl());
        featuredCarTitle.setText(featuredCar.getTitle().trim());
        featuredCarDatePub.setText(featuredCar.getPubDate().substring(0, 16));
        featuredCarDescription.setText(featuredCar.getDescription());
        Picasso.with(getContext()).load(Uri.parse(featuredCar.getThumbnailUrl())).into(featuredCarImage);
        MainActivity.etSearchFeaturedCars.setVisibility(View.INVISIBLE);

        WebView carDetail = (WebView) getActivity().findViewById(R.id.car_spec);
        DataFromTheWeb dataFromTheWeb = new DataFromTheWeb(carDetail, featuredCar.getLink(), getContext(), featuredCar.getTitle());
        dataFromTheWeb.execute();


        plusDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = featuredCar.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }


    private void showActionBar() {
        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.featred_car_detail_menu, null);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(v);
        ImageButton btnBack = (ImageButton) getActivity().findViewById(R.id.btn1);
        final Fragment f = this;
        btnBack.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                actionBar
                        .setDisplayShowCustomEnabled(false);
                getActivity().getSupportFragmentManager().beginTransaction().remove(f)
                        .replace(R.id.home_container, new AccueilFragment()).addToBackStack(null)
                        .commit();
            }
        });
    }


}

