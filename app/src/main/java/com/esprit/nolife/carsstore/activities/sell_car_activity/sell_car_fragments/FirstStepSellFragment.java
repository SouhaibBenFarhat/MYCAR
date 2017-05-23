package com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.roughike.swipeselector.OnSwipeItemSelectedListener;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;


public class FirstStepSellFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    int dayStart, monthStart, yearStart;
    TextView selectedDate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_step_sell, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        final ImageView imgType = (ImageView) getActivity().findViewById(R.id.car_type);
//        imgType.setImageResource(R.drawable.car_on_weight);
        SwipeSelector swipeSelector = (SwipeSelector) getActivity().findViewById(R.id.swipeSelector);
        swipeSelector.setItems(
                new SwipeItem(0, "Car", ""),
                new SwipeItem(1, "Bike", ""),
                new SwipeItem(2, "Truck", "")
        );
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sellCarFragment, new FirstStepCarSellFragment()).commit();
        swipeSelector.setOnItemSelectedListener(new OnSwipeItemSelectedListener() {
            @Override
            public void onItemSelected(SwipeItem item) {

                int value = (Integer) item.value;

                if (value == 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sellCarFragment, new FirstStepCarSellFragment()).commit();

                }
                if (value == 1) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sellCarFragment, new FirstStepCarSellFragment()).commit();
                }
                if (value == 2) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sellCarFragment, new FirstStepCarSellFragment()).commit();
                }

            }
        });

    }








    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
