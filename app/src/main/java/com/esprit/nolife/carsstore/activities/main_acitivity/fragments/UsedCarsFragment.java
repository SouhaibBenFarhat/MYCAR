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
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.AnnonceRecyclerAdapter;
import com.esprit.nolife.carsstore.background_tasks.AnnonceManager;


public class UsedCarsFragment extends Fragment {

    RecyclerView recyclerView;
    AnnonceRecyclerAdapter annonceRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_used_cars, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rc_annoce);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        AnnonceManager annonceManager = new AnnonceManager(getContext(), recyclerView);
        annonceManager.getAnnonce();
    }
}
//    public static List<Annonce> getDataAnnonce()
//    {
//        List<Annonce> annonces = new ArrayList<>();
//        String [] brands  = {"fiat","alpha romeo","ford","audi"};
//        String [] etas  = {"used","new","used","new"};
//        String [] transmitions  = {"automatique","manuelle","autre","automatique"};
//        String [] kilometrages  = {"20.000","10.000","30.000","50.000"};
//        String [] carburants  = {"gaz","diesel","electrique","Essence"};
//        String [] prices  = {"2000DTN","1000DTN","1800DTN","1500DTN"};
//
//        for(int i= 0; i<brands.length ; i++)
//        {
//            Annonce current = new Annonce();
//            current.setBrand(brands[i]);
//            current.setEtat(etas[i]);
//            current.setTransmission(transmitions[i]);
//            current.setKilometrage(kilometrages[i]);
//            current.setCarburant(carburants[i]);
//            current.setPrix(prices[i]);
//            annonces.add(current);
//        }
//        return annonces;
//    }