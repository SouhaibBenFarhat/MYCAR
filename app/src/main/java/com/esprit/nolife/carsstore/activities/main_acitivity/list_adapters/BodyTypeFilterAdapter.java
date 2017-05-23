package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.FilterFragment;
import com.esprit.nolife.carsstore.entities.BodyType;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Souhaib on 18/11/2016.
 */

public class BodyTypeFilterAdapter extends RecyclerView.Adapter<BodyTypeFilterAdapter.CustomViewHolder> {


    private List<BodyType> bodyTypes;
    private Context mContext;
    public int position;

    public BodyTypeFilterAdapter(Context context, List<BodyType> bodyTypes) {
        this.bodyTypes = bodyTypes;
        this.mContext = context;


    }

    @Override
    public BodyTypeFilterAdapter.CustomViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.body_type_item, null);
        BodyTypeFilterAdapter.CustomViewHolder viewHolder = new BodyTypeFilterAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BodyTypeFilterAdapter.CustomViewHolder customViewHolder, int i) {


        BodyType bodyType = bodyTypes.get(i);
        customViewHolder.tvTitle.setText(bodyType.getBody());
        Picasso.with(mContext).load(Uri.parse(bodyType.getLogo())).into(customViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return (null != bodyTypes ? bodyTypes.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView tvTitle;
        protected TextView tvDescription;
        TextView tv;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img_body_type_logo);
            this.tvTitle = (TextView) view.findViewById(R.id.body_type_title);

            tv = (TextView) view.findViewById(R.id.selected_body);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilterFragment.selectedBodyTypeTv.setText(bodyTypes.get(getLayoutPosition()).getBody().toUpperCase());
                    FilterFragment.selectedBodyType = bodyTypes.get(getLayoutPosition());
                }
            });
        }


    }


}