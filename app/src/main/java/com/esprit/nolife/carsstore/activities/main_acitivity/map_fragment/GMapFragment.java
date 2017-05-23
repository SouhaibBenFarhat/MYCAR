package com.esprit.nolife.carsstore.activities.main_acitivity.map_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.entities.Concessionnaire;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Souhaib on 20/11/2016.
 */

public class GMapFragment extends Fragment implements OnMapReadyCallback {
    String concessionnaireAddress;
    ExpandableRelativeLayout expandableRelativeLayout;

    public GMapFragment() {
    }

    LinearLayout mapContainer;
    Concessionnaire concessionnaire;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {





    }


}
