package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.appyvet.rangebar.RangeBar;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.filter_search_result.FilterResultActivity;
import com.esprit.nolife.carsstore.activities.main_acitivity.MainActivity;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.BodyTypeFilterAdapter;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.BrandFilterListViewAdapter;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.BodyType;
import com.esprit.nolife.carsstore.entities.Brand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FilterFragment extends Fragment {

    RangeBar priceRange;
    TextView rangeMaxValue, rangeMinValue;
    GridView lvFilterBrands;
    LinearLayout leftDrawer;
    FrameLayout filterFragment;
    RecyclerView rvBodyType;
    AppCompatButton runFilterButton;
    public static TextView selectedBodyTypeTv;
    public static BodyType selectedBodyType;
    public static String carburant = "";
    public static String prixMax = "";
    public static String prixMin = "";
    public static Brand selectedBrand;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FloatingActionButton actionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        actionButton.setImageResource(R.drawable.ic_close);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(FilterFragment.class.toString());

                if (f != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.entre_from_right, R.anim.exit_to_right)
                            .remove(f).commit();
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.enableFabButton(actionButton);
                }
            }
        });

        final CheckBox sansLimite = (CheckBox) getActivity().findViewById(R.id.sans_limite_checkbox);
        sansLimite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rangeMaxValue.setText("UNDEFINED");
                    rangeMinValue.setText("UNDEFINED");
                }
            }
        });
        rangeMaxValue = (TextView) getActivity().findViewById(R.id.range_max_value);
        rangeMinValue = (TextView) getActivity().findViewById(R.id.range_min_value);
        priceRange = (RangeBar) getActivity().findViewById(R.id.price_range);
        priceRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                rangeMaxValue.setText(rightPinValue + " 000 DT");
                rangeMinValue.setText(leftPinValue + " 000 DT");
                prixMax = rightPinValue;
                prixMin = leftPinValue;

            }
        });

        runFilterButton = (AppCompatButton) getActivity().findViewById(R.id.run_filter_button);
        runFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedBrand != null && selectedBodyType != null && !carburant.equals("")) {

                    if (sansLimite.isChecked()) {
                        prixMin = "0";
                        prixMax = "100000000000";
                        Intent intent
                                = new Intent(getActivity(), FilterResultActivity.class);
                        getActivity().startActivity(intent);
                    } else {
                        if (!rangeMaxValue.getText().equals("") && !rangeMinValue.getText().equals("")) {

                            Intent intent
                                    = new Intent(getActivity(), FilterResultActivity.class);
                            getActivity().startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Veuillez renseignez le prix max et le prix min", Toast.LENGTH_LONG).show();

                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Veuillez renseignez tous les champs", Toast.LENGTH_LONG).show();
                }
            }
        });
        selectedBodyTypeTv = (TextView) getActivity().findViewById(R.id.selected_body);
        final ProgressBar loadingBrandsProgress = (ProgressBar) getActivity().findViewById(R.id.loading_brands_progress);


        final LinearLayout essenceLayout = (LinearLayout) getActivity().findViewById(R.id.essence);
        final LinearLayout dieselLayout = (LinearLayout) getActivity().findViewById(R.id.diesel);
        final LinearLayout hybridLayout = (LinearLayout) getActivity().findViewById(R.id.hybrid);

        essenceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                essenceLayout.setBackgroundResource(R.drawable.layout_shadow);
                dieselLayout.setBackgroundResource(0);
                hybridLayout.setBackgroundResource(0);
                carburant = "ESSENCE";
            }
        });
        dieselLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dieselLayout.setBackgroundResource(R.drawable.layout_shadow);
                essenceLayout.setBackgroundResource(0);
                hybridLayout.setBackgroundResource(0);
                carburant = "DIESEL";
            }
        });

        hybridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hybridLayout.setBackgroundResource(R.drawable.layout_shadow);
                dieselLayout.setBackgroundResource(0);
                essenceLayout.setBackgroundResource(0);
                carburant = "HYBRIDE";
            }
        });

        lvFilterBrands = (GridView) getActivity().findViewById(R.id.lv_filter_brands);
        final ArrayList<Brand> brands = new ArrayList<>();
        loadingBrandsProgress.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_BRANDS, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                int count = 0;
                while (count < response.length()) {
                    try {
                        Brand brand = new Brand();
                        JSONObject jsonObject = response.getJSONObject(count);
                        brand.setBrandId(jsonObject.getString("id"));
                        brand.setBrand(jsonObject.getString("name"));
                        brand.setLogo(jsonObject.getString("logo"));
                        brand.setCover(jsonObject.getString("cover"));
                        brands.add(brand);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                BrandFilterListViewAdapter brandFilterListViewAdapter = new BrandFilterListViewAdapter(getContext(), brands);
                lvFilterBrands.setAdapter(brandFilterListViewAdapter);
                lvFilterBrands.setNumColumns(3);
                loadingBrandsProgress.setVisibility(View.INVISIBLE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(getContext()).addToRequestque(jsonArrayRequest);

        leftDrawer = (LinearLayout) getActivity().findViewById(R.id.left_drawer);
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(FilterFragment.class.toString());

                if (f != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.entre_from_right, R.anim.exit_to_right)
                            .remove(f).commit();
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.enableFabButton(actionButton);
                }
            }
        });

        filterFragment = (FrameLayout) getActivity().findViewById(R.id.filter_fragment);
        filterFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rvBodyType = (RecyclerView) getActivity().findViewById(R.id.rv_body_type);
        getBodyTypes();
    }

    @Override
    public void onResume() {
        super.onResume();
        BrandFilterListViewAdapter.selectedBrands.clear();
    }


    public void getBodyTypes() {
        final ArrayList<BodyType> bodyTypes = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.GET_BODY_CARS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                int count = 0;
                while (count < response.length()) {
                    try {
                        BodyType bodyType = new BodyType();
                        JSONObject jsonObject = response.getJSONObject(count);
                        bodyType.setId(jsonObject.getString("id"));
                        bodyType.setBody(jsonObject.getString("body"));
                        bodyType.setLogo(jsonObject.getString("logo"));
                        bodyTypes.add(bodyType);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rvBodyType.setLayoutManager(linearLayoutManager);
                rvBodyType.setAdapter(new BodyTypeFilterAdapter(getContext(), bodyTypes));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(getContext()).addToRequestque(jsonArrayRequest);
    }


}
