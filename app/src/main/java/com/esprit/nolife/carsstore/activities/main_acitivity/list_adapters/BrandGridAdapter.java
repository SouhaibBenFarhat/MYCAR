package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.models_activity.ModelsActivity;
import com.esprit.nolife.carsstore.entities.Brand;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Souhaib on 06/11/2016.
 */

public class BrandGridAdapter extends ArrayAdapter<Brand> {

    Context context;
    int resource;
    List<Brand> brands;


    public BrandGridAdapter(Context context, int resource, List<Brand> brands) {
        super(context, resource, brands);
        this.context = context;
        this.resource = resource;
        this.brands = brands;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, parent, false);

        TextView tvBrandTitle = (TextView) view.findViewById(R.id.brand_title);
        ImageView imgBrandLogo = (ImageView) view.findViewById(R.id.img_brand_logo);
        tvBrandTitle.setText(brands.get(position).getBrand());
        Glide.with(view.getContext()).load(Uri.parse(brands.get(position).getLogo()))
                .into(imgBrandLogo);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Activity activity = (Activity) context;
                Bundle arg = new Bundle();
                arg.putSerializable("brand", brands.get(position));
                Intent intent = new Intent(activity, ModelsActivity.class);
                intent.putExtras(arg);
                activity.startActivity(intent);



            }
        });


        Activity activity = (Activity) context;
        final ScrollView scrollView = (ScrollView) activity.findViewById(R.id.search_new_cars_scrollview);
        scrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("scroll touch");
                parent.findViewById(R.id.gv_brands).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });


        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("grid touch");
                // Disallow the touch request for parent scroll on touch of
                // child view
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        return view;
    }


}
