package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.NewsMagazineFragment;
import com.esprit.nolife.carsstore.activities.news_detail_activity.NewsDetailActivity;
import com.esprit.nolife.carsstore.entities.CarMagazine;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Souhaib on 07/11/2016.
 */

public class NewsMagazineRecyclerAdapter extends RecyclerView.Adapter<NewsMagazineRecyclerAdapter.CustomViewHolder> {
    private List<CarMagazine> carMagazines;
    private Context mContext;

    public NewsMagazineRecyclerAdapter(Context context, List<CarMagazine> carMagazines) {
        this.carMagazines = carMagazines;
        this.mContext = context;


    }

    @Override
    public NewsMagazineRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cars_magazine, null);
        NewsMagazineRecyclerAdapter.CustomViewHolder viewHolder = new NewsMagazineRecyclerAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NewsMagazineRecyclerAdapter.CustomViewHolder customViewHolder, int i) {
        final CarMagazine carMagazine = carMagazines.get(i);
        customViewHolder.textView.setText(carMagazine.getTitle());
        customViewHolder.txtNewsAuthor.setText(carMagazine.getAuthor());
        customViewHolder.txtNewsPubDate.setText(carMagazine.getPubDate());
        Picasso.with(mContext).load(Uri.parse(carMagazine.getThumbnailUrl())).into(customViewHolder.imageView);
        customViewHolder.tvNewsDescription.setText(carMagazine.getDescription().replaceAll("\\<[^>]*>", ""));
        customViewHolder.tvSeeMore.
                setPaintFlags(customViewHolder.tvSeeMore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        customViewHolder.tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewHolder.elNewsDescription.toggle();
            }
        });

        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.home_container);
        if (f instanceof NewsMagazineFragment) {
            customViewHolder.tvSeeMore.setVisibility(View.VISIBLE);
        } else {
            customViewHolder.tvSeeMore.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != carMagazines ? carMagazines.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView txtNewsAuthor;
        protected TextView txtNewsPubDate;
        protected TextView tvSeeMore;
        protected ExpandableRelativeLayout elNewsDescription;
        protected TextView tvNewsDescription;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.iv_news_img);
            this.textView = (TextView) view.findViewById(R.id.tv_news_title);
            this.txtNewsAuthor = (TextView) view.findViewById(R.id.tv_news_author);
            this.txtNewsPubDate = (TextView) view.findViewById(R.id.tv_news_pubdate);
            this.tvSeeMore = (TextView) view.findViewById(R.id.tv_see_more);
            this.elNewsDescription = (ExpandableRelativeLayout) view.findViewById(R.id.el_news_description);
            this.tvNewsDescription = (TextView) view.findViewById(R.id.tv_news_description);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CarMagazine carMagazine = carMagazines.get(getLayoutPosition());
                    Bundle arg = new Bundle();
                    arg.putSerializable("carMagazine", carMagazine);
                    Intent intent = new Intent(((Activity) mContext), NewsDetailActivity.class);
                    intent.putExtras(arg);
                    ((Activity) mContext).startActivity(intent);

//                    NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
//                    newsDetailFragment.setArguments(arg);
//                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
//                            .add(R.id.home_container, newsDetailFragment, newsDetailFragment.getClass().toString())
//                            .addToBackStack(newsDetailFragment.getClass().toString()).commit();


                }
            });
        }
    }
}
