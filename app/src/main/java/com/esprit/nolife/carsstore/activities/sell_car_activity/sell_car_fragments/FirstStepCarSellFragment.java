package com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.background_tasks.BrandManager;
import com.esprit.nolife.carsstore.custom_implementation.CustomViewPager;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstStepCarSellFragment extends Fragment implements Spinner.OnItemSelectedListener {

    Spinner constructeurSpinner;
    Spinner sItems;
    Spinner modelSpinner;
    public FirstStepCarSellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_step_car_sell, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sItems = (Spinner) getActivity().findViewById(R.id.brandSpinner);
        Spinner spinnerModel = (Spinner) getActivity().findViewById(R.id.modelSpinner);
        BrandManager brandManager = new BrandManager(getContext(), sItems, spinnerModel);
        brandManager.getBrandName();

        constructeurSpinner = (Spinner) getActivity().findViewById(R.id.brandSpinner);

        modelSpinner = (Spinner) getActivity().findViewById(R.id.modelSpinner);
        final Spinner trasmitionSpinner = (Spinner) getActivity().findViewById(R.id.transmitionSpinner);
        final Spinner conditionSpinner = (Spinner) getActivity().findViewById(R.id.conditionSpinner);
        final CustomViewPager viewpager = (CustomViewPager) getActivity().findViewById(R.id.viewpager);
        final CircleIndicator indicator = (CircleIndicator) getActivity().findViewById(R.id.indicator);
        final EditText kilometrageField = (EditText) getActivity().findViewById(R.id.kilometrage);
        final EditText couleurField = (EditText) getActivity().findViewById(R.id.couleur);
        final EditText prixField = (EditText) getActivity().findViewById(R.id.prixAnnonce);
        sItems.setOnItemSelectedListener(this);

        Button secondStep = (Button) getActivity().findViewById(R.id.btn_next1);


            secondStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(couleurField.getText().toString().length()==0
                            ||  prixField.getText().toString().length()==0
                            || kilometrageField.toString().length()==0)
                    {
                        Toast.makeText(getActivity(),"please fill all the blinks",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        final SharedPreferences prefs = getActivity().getPreferences(0);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("type", "car");
                        editor.putString("brand", constructeurSpinner.getSelectedItem().toString());
                        editor.putString("model", modelSpinner.getSelectedItem().toString());
                        editor.putString("transmition", trasmitionSpinner.getSelectedItem().toString());
                        editor.putString("condition", conditionSpinner.getSelectedItem().toString());
                        editor.putString("couleur", couleurField.getText().toString());
                        editor.putString("prix", prixField.getText().toString());
                        editor.putString("kilomertage", kilometrageField.getText().toString());
                        editor.apply();

                        viewpager.setCurrentItem(1);
                        viewpager.setPagingEnabled(true);

                    }


                }
            });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       BrandManager brandManager = new BrandManager(getActivity(),sItems,modelSpinner,sItems.getSelectedItemId());
        brandManager.getBrandNameModel();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }





}
//    String urll =  URL.SET_Annonce+"brand="+"'"+constructeurSpinner.getSelectedItem().toString()
//            +"'&&model="+"'"+modelSpinner.getSelectedItem().toString()
//            +"'&&transmition="+"'"+trasmitionSpinner.getSelectedItem().toString()
//            +"'&&condition="+"'"+conditionSpinner.getSelectedItem().toString()
//            +"'&&couleur="+"'"+couleurField.getText().toString()
//            +"'&&prix="+"'"+prixField.getText().toString()
//            +"'&&kilometrage="+"'"+kilometrageField.getText().toString()+"'";
//
//    StringRequest request = new StringRequest(Request.Method.GET, URL.SET_PHOTO_Annonce, new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//
//
//        }
//    }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            Toast.makeText(getActivity(),"cant post",Toast.LENGTH_SHORT).show();
//        }
//    }){
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//            Map<String, String> parameters = new HashMap<String, String>();
//            // parameters.put("brand", constructeurSpinner.getSelectedItem().toString());
//            parameters.put("brand", "LandRover");
//            return parameters;
//        }
//    };
//// ConnectionSingleton.getInstance(getActivity()).addToRequestque(request);
//AnnonceController.getmInstance(getActivity()).addToRequestQue(request);

//                    if(couleurField.getText().toString().length()==0
//                            ||  prixField.getText().toString().length()==0
//                            || kilometrageField.toString().length()==0)
//                    {
//                        Toast.makeText(getActivity(),"please fill all the blinks",Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        final SharedPreferences prefs = getActivity().getPreferences(0);
//                        SharedPreferences.Editor editor = prefs.edit();
//                        editor.putString("type", "auto");
//                        editor.putString("brand", constructeurSpinner.getSelectedItem().toString());
//                        editor.putString("model", modelSpinner.getSelectedItem().toString());
//                        editor.putString("transmition", trasmitionSpinner.getSelectedItem().toString());
//                        editor.putString("condition", conditionSpinner.getSelectedItem().toString());
//                        editor.putString("couleur", couleurField.getText().toString());
//                        editor.putString("prix", prixField.getText().toString());
//                        editor.putString("kilomertage", kilometrageField.getText().toString());
//                        editor.apply();
//
//                        viewpager.setCurrentItem(1);
//                        viewpager.setPagingEnabled(true);
//
//                    }