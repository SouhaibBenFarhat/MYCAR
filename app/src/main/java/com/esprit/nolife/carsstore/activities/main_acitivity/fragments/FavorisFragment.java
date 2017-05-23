package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.background_tasks.FavorisManager;
import com.google.firebase.auth.FirebaseAuth;


public class FavorisFragment extends Fragment {

    TextView desoleTv, aucunTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favoris, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView rvFavoris = (RecyclerView) getActivity().findViewById(R.id.rv_favoris);
        FavorisManager favorisManager = new FavorisManager(getActivity(), rvFavoris);
        ImageView noDataFound = (ImageView) getActivity().findViewById(R.id.no_data_found);


        desoleTv = (TextView) getActivity().findViewById(R.id.desole_tv);
        aucunTv = (TextView) getActivity().findViewById(R.id.aucun_tv);
        desoleTv.setVisibility(View.VISIBLE);
        aucunTv.setVisibility(View.VISIBLE);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        rvFavoris.setLayoutManager(linearLayoutManager);
        favorisManager.getUserFavoris(FirebaseAuth.getInstance().getCurrentUser().getUid(), noDataFound, desoleTv, aucunTv);


        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.favoris_fragment);
        frameLayout.setSoundEffectsEnabled(false);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView rvFavoris = (RecyclerView) getActivity().findViewById(R.id.rv_favoris);
        FavorisManager favorisManager = new FavorisManager(getActivity(), rvFavoris);
        ImageView noDataFound = (ImageView) getActivity().findViewById(R.id.no_data_found);
        desoleTv = (TextView) getActivity().findViewById(R.id.desole_tv);
        aucunTv = (TextView) getActivity().findViewById(R.id.aucun_tv);
        desoleTv.setVisibility(View.VISIBLE);
        aucunTv.setVisibility(View.VISIBLE);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        rvFavoris.setLayoutManager(linearLayoutManager);
        favorisManager.getUserFavoris(FirebaseAuth.getInstance().getCurrentUser().getUid(), noDataFound, desoleTv, aucunTv);
    }
}
