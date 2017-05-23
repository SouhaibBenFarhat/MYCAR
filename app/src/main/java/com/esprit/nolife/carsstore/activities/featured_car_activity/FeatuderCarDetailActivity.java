package com.esprit.nolife.carsstore.activities.featured_car_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
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

public class FeatuderCarDetailActivity extends AppCompatActivity {


    TextView featuredCarTitle, featuredCarDescription, featuredCarDatePub;
    ImageView featuredCarImage;
    View view;
    FeaturedCar featuredCar;
    ActionBar actionBar;
    AppCompatButton facebookShareButton, plusDetailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featuder_car_detail);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        featuredCar = (FeaturedCar) getIntent().getExtras().getSerializable("featredCar");
        facebookShareButton = (AppCompatButton) findViewById(R.id.btn_facebook_share);
        plusDetailButton = (AppCompatButton) findViewById(R.id.btn_more_detail);


        facebookShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShareDialog shareDialog;
                FacebookSdk.sdkInitialize(FeatuderCarDetailActivity.this);
                shareDialog = new ShareDialog(FeatuderCarDetailActivity.this);
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(featuredCar.getTitle())
                        .setContentDescription(
                                featuredCar.getDescription())
                        .setContentUrl(Uri.parse(featuredCar.getLink())).build();
                shareDialog.show(linkContent);


            }
        });


        featuredCarTitle = (TextView) findViewById(R.id.tv_featured_car_title);
        featuredCarDatePub = (TextView) findViewById(R.id.tv_featured_car_date);
        featuredCarImage = (ImageView) findViewById(R.id.img_featured_car);
        featuredCarDescription = (TextView) findViewById(R.id.txv_featured_car_description);
        System.out.println(featuredCar.getThumbnailUrl());
        featuredCarTitle.setText(featuredCar.getTitle().trim());
        featuredCarDatePub.setText(featuredCar.getPubDate().substring(0, 16));
        featuredCarDescription.setText(featuredCar.getDescription());
        Picasso.with(getApplicationContext()).load(Uri.parse(featuredCar.getThumbnailUrl())).into(featuredCarImage);
        MainActivity.etSearchFeaturedCars.setVisibility(View.INVISIBLE);

        WebView carDetail = (WebView) findViewById(R.id.car_spec);
        DataFromTheWeb dataFromTheWeb = new DataFromTheWeb(carDetail, featuredCar.getLink(), getApplicationContext(), featuredCar.getTitle());
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
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
