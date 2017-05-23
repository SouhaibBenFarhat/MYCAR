package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.background_tasks.ModelsManager;
import com.esprit.nolife.carsstore.entities.Brand;


public class ModelsFragment extends Fragment {


    RecyclerView modelsRecyclerView;
    LinearLayoutManager modeLinearLayoutManager;
    ModelsManager modelsManager;
    ImageView brandCover;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_models, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Brand brand = (Brand) getArguments().getSerializable("brand");
//        Picasso.with(getContext()).load(Uri.parse(brand.getCover())).fit().into(brandCover);
        modelsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_models);
        modeLinearLayoutManager = new GridLayoutManager(getContext(), 1);
        modelsRecyclerView.setLayoutManager(modeLinearLayoutManager);
        modelsManager = new ModelsManager(getContext(), modelsRecyclerView);
        modelsManager.getModels(Integer.parseInt(brand.getBrandId()));


    }
}
