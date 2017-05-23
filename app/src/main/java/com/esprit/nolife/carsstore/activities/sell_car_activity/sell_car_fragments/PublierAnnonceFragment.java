package com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.sell_car_activity.SellCarActivity;


public class PublierAnnonceFragment extends Fragment {

    AppCompatButton btnPublierAnnonce;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publier_annonce, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout mainLayout = (LinearLayout) getActivity().findViewById(R.id.main_annonce_layout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnPublierAnnonce = (AppCompatButton) getActivity().findViewById(R.id.btn_publier_annonce);
        btnPublierAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(getActivity(), SellCarActivity.class);
                startActivity(intent);
            }
        });
    }
}
