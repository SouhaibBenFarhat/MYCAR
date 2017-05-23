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

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.background_tasks.ConcessionnaireManager;

public class AllConcessionnairesFragment extends Fragment {

    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_concessionnaires, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_all_concessionnaire);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConcessionnaireManager concessionnaireManager = new ConcessionnaireManager(getContext(), recyclerView);
        concessionnaireManager.getConcessionnaire();

    }
}
