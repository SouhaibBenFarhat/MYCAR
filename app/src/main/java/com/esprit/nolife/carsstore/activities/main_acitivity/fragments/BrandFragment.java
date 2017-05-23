package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.BrandGridAdapter;
import com.esprit.nolife.carsstore.background_tasks.BrandManager;
import com.esprit.nolife.carsstore.entities.Brand;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class BrandFragment extends Fragment {

    GridView gridView;
    DatabaseReference databaseReference;
    ProgressDialog loadingBrands;
    BrandGridAdapter brandGridAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_brands, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingBrands = new ProgressDialog(getContext());
        loadingBrands.setTitle("Loading");
        loadingBrands.setMessage("loading available brands");

        final List<Brand> brands = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

//
//        Query myTopPostsQuery = databaseReference.child("brands");
//        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
//
//            public void onDataChange(DataSnapshot snapshot) {
//
//
//                for (DataSnapshot brandSnapShot : snapshot.getChildren()) {
//                    Brand b = new Brand();
//                    b.setBrand((String) brandSnapShot.child("brand").getValue());
////                    b.setBrandId((String) brandSnapShot.child("brandId").getValue());
//                    b.setLogo((String) brandSnapShot.child("logo").getValue());
//                    brands.add(b);
//
//
//                }
//
//                brandGridAdapter = new BrandGridAdapter(getContext(), R.layout.brand_item, brands);
//                gridView.setAdapter(brandGridAdapter);
//                gridView.setNumColumns(3);
//
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError firebaseError) {
//
//            }
//        });




        gridView = (GridView) getActivity().findViewById(R.id.gv_brands);
        BrandManager brandManager = new BrandManager(getContext(), gridView);
        brandManager.getBrand();




    }
}
