package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.FilterFragment;
import com.esprit.nolife.carsstore.entities.Brand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Souhaib on 17/11/2016.
 */

public class BrandFilterListViewAdapter extends ArrayAdapter<Brand> {

    ArrayList<Brand> brands;
    Context context;
    ScrollView filterScroll;

    public final static ArrayList<Brand> selectedBrands = new ArrayList<>();

    // View lookup cache


    public BrandFilterListViewAdapter(Context context, ArrayList<Brand> brands) {
        super(context, R.layout.brand_listview_item, brands);
        this.context = context;
        this.brands = brands;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {


        final Brand brand = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.brand_listview_item, parent, false);
        TextView brandName = (TextView) convertView.findViewById(R.id.brand_title);
        ImageView brandLogo = (ImageView) convertView.findViewById(R.id.img_brand_logo);
        final LinearLayout brandItemContainer = (LinearLayout) convertView.findViewById(R.id.brand_item_container);
        for (Brand b : selectedBrands) {
            if (brand.getBrand().equals(b.getBrand())) {
                brandItemContainer.setBackgroundResource(R.drawable.layout_shadow);
            }
        }
        brandItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedBrands.size() == 1) {
                    for (Brand b : selectedBrands) {
                        brandItemContainer.setBackgroundResource(0);
                    }
                    selectedBrands.clear();
                    brandItemContainer.setBackgroundResource(R.drawable.layout_shadow);
                    selectedBrands.add(brand);
                    FilterFragment.selectedBrand = brand;

                } else {
                    brandItemContainer.setBackgroundResource(R.drawable.layout_shadow);
                    selectedBrands.add(brand);
                    FilterFragment.selectedBrand = brand;
                }
                notifyDataSetChanged();
            }
        });

        brandName.setText(brand.getBrand());
        Glide.with(context).load(Uri.parse(brand.getLogo())).into(brandLogo);

        Activity activity = (Activity) context;
        final ScrollView scrollView = (ScrollView) activity.findViewById(R.id.filter_scroll);


        brandItemContainer.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("grid touch");
                // Disallow the touch request for parent scroll on touch of
                // child view
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
