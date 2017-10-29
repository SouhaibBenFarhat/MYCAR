package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.MainActivity;
import com.esprit.nolife.carsstore.activities.models_activity.ModelsActivity;
import com.esprit.nolife.carsstore.entities.Brand;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Souhaib on 01/11/2016.
 */

public class PopularBrandsRecyclerViewAdapter extends RecyclerView.Adapter<PopularBrandsRecyclerViewAdapter.CustomViewHolder> {
    private List<Brand> brandList;
    private Context mContext;

    public PopularBrandsRecyclerViewAdapter(Context context, List<Brand> brandList) {
        this.brandList = brandList;
        this.mContext = context;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_popular_brands, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        customViewHolder.textView.setText(brandList.get(i).getBrand());
        System.out.println(brandList.get(i).getLogo());

        Glide
                .with(mContext)
                .load(brandList.get(i).getLogo())
                .override(250, 200) // resizes the image to these dimensions (in pixel). resize does not respect aspect ratio
                .into(customViewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return (null != brandList ? brandList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.iv_brand_logo);
            this.textView = (TextView) view.findViewById(R.id.tv_brand_title);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Activity activity = (Activity) mContext;
                    Bundle arg = new Bundle();
                    arg.putSerializable("brand", brandList.get(getLayoutPosition()));
                    Intent intent = new Intent(activity, ModelsActivity.class);
                    intent.putExtras(arg);
                    activity.startActivity(intent);


                }
            });

        }
    }
}

