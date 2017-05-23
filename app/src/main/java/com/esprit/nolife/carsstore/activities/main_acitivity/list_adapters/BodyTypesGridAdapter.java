package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

/**
 * Created by Souhaib on 07/11/2016.
 */

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.entities.BodyType;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Souhaib on 06/11/2016.
 */

public class BodyTypesGridAdapter extends ArrayAdapter<BodyType> {

    Context context;
    int resource;
    List<BodyType> bodyTypes;


    public BodyTypesGridAdapter(Context context, int resource, List<BodyType> bodyTypes) {
        super(context, resource, bodyTypes);
        this.context = context;
        this.resource = resource;
        this.bodyTypes = bodyTypes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, parent, false);
        TextView tvBrandTitle = (TextView) view.findViewById(R.id.body_type_title);
        ImageView imgBrandLogo = (ImageView) view.findViewById(R.id.img_body_type_logo);
        tvBrandTitle.setText(bodyTypes.get(position).getBody());
        Picasso.with(view.getContext()).load(Uri.parse(bodyTypes.get(position).getLogo()))
                .into(imgBrandLogo);
        return view;
    }

}

