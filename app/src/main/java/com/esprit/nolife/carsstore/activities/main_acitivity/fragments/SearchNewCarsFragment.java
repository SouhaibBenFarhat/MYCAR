package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.view_pager_adapters.SearchNewCarsPagerAdapter;


public class SearchNewCarsFragment extends Fragment {

    ViewPager searchNewCarsContainer;
    TabLayout tabLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search_new_cars, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchNewCarsContainer = (ViewPager) getActivity().findViewById(R.id.search_new_cars_container);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.search_new_cars_container_tab_layout);
        PagerAdapter searchNewCarsPagerAdapter
                = new SearchNewCarsPagerAdapter(getActivity().getSupportFragmentManager());
        tabLayout.setupWithViewPager(searchNewCarsContainer);
        searchNewCarsContainer.setAdapter(searchNewCarsPagerAdapter);






//        LinearLayoutManager featuredCarsLayoutManager
//                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        RecyclerView featrudCars = (RecyclerView) getActivity().findViewById(R.id.rv_featured_cars_new_cars);
//        featrudCars.setLayoutManager(featuredCarsLayoutManager);
//
//        FeaturedCarsManager featuredCarsManager = new FeaturedCarsManager(getContext(), featrudCars);
//        featuredCarsManager.getFeaturedCars(Key.LIMIT);
//        System.out.println("here");



    }
}
