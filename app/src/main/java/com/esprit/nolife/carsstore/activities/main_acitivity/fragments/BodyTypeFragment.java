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
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.BodyTypesGridAdapter;
import com.esprit.nolife.carsstore.background_tasks.BodyCarsManager;
import com.esprit.nolife.carsstore.entities.BodyType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class BodyTypeFragment extends Fragment {

    GridView gridView;
    DatabaseReference databaseReference;
    ProgressDialog loadingBodyTypes;
    BodyTypesGridAdapter bodyTypesGridAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_body_type, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingBodyTypes = new ProgressDialog(getContext());
        loadingBodyTypes.setTitle("Loading");
        loadingBodyTypes.setMessage("loading data from server");
        final List<BodyType> bodyTypes = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

//
//        Query myTopPostsQuery = databaseReference.child("bodyCars");
//        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
//
//            public void onDataChange(DataSnapshot snapshot) {
//
//
//                for (DataSnapshot brandSnapShot : snapshot.getChildren()) {
//                    BodyType b = new BodyType();
//                    b.setBody((String) brandSnapShot.child("body").getValue());
////                    b.setBrandId((String) brandSnapShot.child("brandId").getValue());
//                    b.setLogo((String) brandSnapShot.child("logo").getValue());
//                    bodyTypes.add(b);
//
//
//                }
//
//                bodyTypesGridAdapter = new BodyTypesGridAdapter(getContext(), R.layout.body_type_item, bodyTypes);
//                gridView.setAdapter(bodyTypesGridAdapter);
//                gridView.setNumColumns(3);
//                loadingBodyTypes.dismiss();
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError firebaseError) {
//
//            }
//        });

        gridView = (GridView) getActivity().findViewById(R.id.gv_body_types);
        BodyCarsManager bodyCarsManager = new BodyCarsManager(getContext(), gridView);
        bodyCarsManager.getBodyTypes();

    }
}

