package com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.view_pager_adapters.SellCarPagerAdapter;
import com.esprit.nolife.carsstore.custom_implementation.CustomViewPager;

import me.relex.circleindicator.CircleIndicator;


public class SellCarFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell_car, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CustomViewPager viewpager = (CustomViewPager) getActivity().findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) getActivity().findViewById(R.id.indicator);
        viewpager.setAdapter(new SellCarPagerAdapter(getFragmentManager()));
        indicator.setViewPager(viewpager);
        indicator.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        viewpager.setCurrentItem(0);
        viewpager.setPagingEnabled(false);

    }
}
