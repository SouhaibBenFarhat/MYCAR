package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.view_pager_adapters.HomePagerAdapter;


public class AccueilFragment extends Fragment {

    ViewPager viewPager;
    TabLayout homeTabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accueil, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeTabLayout = (TabLayout) getActivity().findViewById(R.id.home_tab_layout);
        viewPager = (ViewPager) getActivity().findViewById(R.id.home_pager);
        android.support.v4.app.FragmentManager f = getActivity().getSupportFragmentManager();
        viewPager.setAdapter(new HomePagerAdapter(getActivity().getSupportFragmentManager()));
        homeTabLayout.setupWithViewPager(viewPager);
       // homeTabLayout.getTabAt(0).setIcon(R.drawable.new_cars_icon);
     //   homeTabLayout.getTabAt(1).setIcon(R.drawable.used_cars_icon);


        final FloatingActionButton actionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.entre_from_right, R.anim.exit_to_right)
                                                        .add(R.id.home_container, new FilterFragment(), FilterFragment.class.toString())
                                                        .addToBackStack(FilterFragment.class.toString())
                                                        .commit();
                                                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.fab_animation);
                                                actionButton.startAnimation(shake);
                                            }

                                        }

        );


    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
