package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.featured_car_activity.FeatuderCarDetailActivity;
import com.esprit.nolife.carsstore.entities.FeaturedCar;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Souhaib on 01/11/2016.
 */

public class FeaturedCarsRecyclerAdapter extends RecyclerView.Adapter<FeaturedCarsRecyclerAdapter.CustomViewHolder> {
    private List<FeaturedCar> featuredCars;
    private Context mContext;
    public int position;

    public FeaturedCarsRecyclerAdapter(Context context, List<FeaturedCar> featuredCars) {
        this.featuredCars = featuredCars;
        this.mContext = context;


    }

    @Override
    public CustomViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_featured_car, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        FeaturedCar featuredCar = featuredCars.get(i);

        //Render image using Picasso library
//        if (!TextUtils.isEmpty(carName.getThumbnail())) {
//            Picasso.with(mContext).load(carName.getThumbnail())
//                    .error(R.drawable.placeholder)
//                    .placeholder(R.drawable.placeholder)
//                    .into(customViewHolder.concessionnaireLogo);
//        }

        //Setting text view title
        customViewHolder.tvTitle.setText(featuredCar.getTitle());
        customViewHolder.tvDescription.setText(featuredCar.getDescription());
        Glide.with(mContext).load(Uri.parse(featuredCar.getThumbnailUrl())).into(customViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return (null != featuredCars ? featuredCars.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView tvTitle;
        protected TextView tvDescription;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.iv_featured_car);
            this.tvTitle = (TextView) view.findViewById(R.id.tv_featured_cars);
            this.tvDescription = (TextView) view.findViewById(R.id.tv_featured_cars_description);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle arg = new Bundle();
                    arg.putSerializable("featredCar", featuredCars.get(getLayoutPosition()));

                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(activity, FeatuderCarDetailActivity.class);
                    intent.putExtras(arg);
                    activity.startActivity(intent);


//                    ActionBar mActionBar = ((AppCompatActivity) mContext).getSupportActionBar();
//                    mActionBar.hide();


                }
            });
        }


    }


}

