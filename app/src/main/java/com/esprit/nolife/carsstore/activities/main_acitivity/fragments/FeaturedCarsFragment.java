package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.Key;
import com.esprit.nolife.carsstore.activities.main_acitivity.MainActivity;
import com.esprit.nolife.carsstore.background_tasks.FeaturedCarsManager;


public class  FeaturedCarsFragment extends Fragment {

    RecyclerView lvFeaturedCars;
    LinearLayoutManager featuredCarsLayoutManager;
    FeaturedCarsManager featuredCarsManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_featured_cars, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.etSearchFeaturedCars.setVisibility(View.VISIBLE);
        featuredCarsLayoutManager
                = new GridLayoutManager(getContext(), 3);
        lvFeaturedCars = (RecyclerView) getActivity().findViewById(R.id.lv_featured_cars);

        lvFeaturedCars.setLayoutManager(featuredCarsLayoutManager);
        featuredCarsManager = new FeaturedCarsManager(getContext(), lvFeaturedCars, "grid");
        featuredCarsManager.getFeaturedCars(Key.ALL);


        MainActivity.etSearchFeaturedCars.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                featuredCarsManager = new FeaturedCarsManager(getContext(), lvFeaturedCars);
                featuredCarsManager.getFeaturedCarsByTitle(
                        MainActivity.etSearchFeaturedCars.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
        MainActivity.etSearchFeaturedCars.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.etSearchFeaturedCars.setVisibility(View.VISIBLE);

    }
}
