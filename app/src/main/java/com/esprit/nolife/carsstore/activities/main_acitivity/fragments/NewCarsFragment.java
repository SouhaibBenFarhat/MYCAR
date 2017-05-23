package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.Key;
import com.esprit.nolife.carsstore.background_tasks.BrandManager;
import com.esprit.nolife.carsstore.background_tasks.ConcessionnaireManager;
import com.esprit.nolife.carsstore.background_tasks.FeaturedCarsManager;
import com.esprit.nolife.carsstore.background_tasks.GammeManager;
import com.esprit.nolife.carsstore.utils.xml_parsers.CarsMagazineRssReader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NewCarsFragment extends Fragment {

    RecyclerView featrudCars;
    RecyclerView popularBrands;
    RecyclerView newsMagazine;
    DatabaseReference databaseReference;
    ProgressDialog loadingBrandsProgress;
    LinearLayoutManager featuredCarslayoutManager, featuredCarsLayoutManager;
    CarsMagazineRssReader carsMagazineRssReader;
    AppCompatButton btnFeaturedCarsSeeAll, btnNewsMagazineSeeAll, btnSeeAllConcessionnaire, searchButton;
    RecyclerView rvConcessionnaire;
    EditText etSearchPopularBrands;
    GammeManager gammeManager;
    AutoCompleteTextView autoCompleteTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_cars, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingBrandsProgress = new ProgressDialog(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnFeaturedCarsSeeAll = (AppCompatButton) getActivity().findViewById(R.id.btn_featured_cars_see_all);
        btnFeaturedCarsSeeAll.setOnClickListener(new View.OnClickListener() {
            final Fragment f = getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.home_container);

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.home_container, new FeaturedCarsFragment()).addToBackStack(null).commit();
            }
        });

        popularBrands = (RecyclerView) getActivity().findViewById(R.id.rv_popular_brands);
        BrandManager brandManager = new BrandManager(getActivity(), popularBrands);
        brandManager.getPopularBrands();

        etSearchPopularBrands = (EditText) getActivity().findViewById(R.id.et_search_brands);
        etSearchPopularBrands.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BrandManager brandManager = new BrandManager(getActivity(), popularBrands);
                brandManager.searchPopularBrand(etSearchPopularBrands.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        featuredCarslayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        featuredCarsLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        newsMagazine = (RecyclerView) getActivity().findViewById(R.id.rv_news_magazine);
        carsMagazineRssReader = new CarsMagazineRssReader(getContext(), newsMagazine);
        carsMagazineRssReader.execute();


        featrudCars = (RecyclerView) getActivity().findViewById(R.id.rv_featured_cars);
        featrudCars.setLayoutManager(featuredCarsLayoutManager);

        FeaturedCarsManager featuredCarsManager = new FeaturedCarsManager(getContext(), featrudCars);
        featuredCarsManager.getFeaturedCars(Key.LIMIT);


        btnNewsMagazineSeeAll = (AppCompatButton) getActivity().findViewById(R.id.btn_see_all_news);
        btnNewsMagazineSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.home_container, new NewsMagazineFragment(), "NewsMagazineFragment")
                        .addToBackStack("NewsMagazineFragment").commit();
            }
        });

        rvConcessionnaire = (RecyclerView) getActivity().findViewById(R.id.rv_concessionnaire);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvConcessionnaire.setLayoutManager(linearLayoutManager);
        ConcessionnaireManager concessionnaireManager = new ConcessionnaireManager(getContext(), rvConcessionnaire);
        concessionnaireManager.getConcessionnaire();


        btnSeeAllConcessionnaire = (AppCompatButton) getActivity().findViewById(R.id.btn_concessionnaire_see_all);
        btnSeeAllConcessionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.home_container, new AllConcessionnairesFragment(), AllConcessionnairesFragment.class.toString())
                        .addToBackStack(AllConcessionnairesFragment.class.toString()).commit();

            }
        });

        searchButton = (AppCompatButton) getActivity().findViewById(R.id.btn_search);
        autoCompleteTextView = (AutoCompleteTextView) getActivity().findViewById(R.id.autoCompleteTextView);
        gammeManager = new GammeManager(getActivity());
        gammeManager.getAllGammes(autoCompleteTextView,searchButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        btnFeaturedCarsSeeAll = (AppCompatButton) getActivity().findViewById(R.id.btn_featured_cars_see_all);
        btnNewsMagazineSeeAll = (AppCompatButton) getActivity().findViewById(R.id.btn_see_all_news);


    }
}
