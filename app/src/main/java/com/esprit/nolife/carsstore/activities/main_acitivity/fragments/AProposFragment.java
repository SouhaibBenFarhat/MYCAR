package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.esprit.nolife.carsstore.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class AProposFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apropos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView imageView = (ImageView) getActivity().findViewById(R.id.esprit_mobile);
        Picasso.with(getActivity())
                .load(Uri.parse("https://www.inceptumapps.com/carsstore/mobile/shared/esprit_logo.png"))
                .into(imageView);

        CircleImageView souhaib = (CircleImageView) getActivity().findViewById(R.id.souhaib);
        CircleImageView firas = (CircleImageView) getActivity().findViewById(R.id.firas);

        Picasso.with(getActivity()).load(Uri.parse("http://inceptumapps.com/carsstore/mobile/shared/1.jpg")).into(souhaib);
        Picasso.with(getActivity()).load(Uri.parse("http://inceptumapps.com/carsstore/mobile/shared/2.jpg")).into(firas);


    }
}
