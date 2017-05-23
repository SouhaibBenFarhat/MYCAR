package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.PictureFragment;
import com.esprit.nolife.carsstore.entities.Picture;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Souhaib on 14/11/2016.
 */

public class AlbumGridAdapter extends ArrayAdapter<Picture> {

    Context context;
    int resource;
    List<Picture> pictures;


    public AlbumGridAdapter(Context context, int resource, List<Picture> pictures) {
        super(context, resource, pictures);
        this.context = context;
        this.resource = resource;
        this.pictures = pictures;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, parent, false);


        ImageView imgBrandPicture = (ImageView) view.findViewById(R.id.img_model);
        Picasso.with(view.getContext()).load(Uri.parse(pictures.get(position).getUrl()))
                .into(imgBrandPicture);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Toast.makeText(context, "TOAST", Toast.LENGTH_SHORT).show();
//
////                Bundle arg = new Bundle();
////                arg.putSerializable("brand", pictures.get(position));
////                ModelsFragment modelsFragment = new ModelsFragment();
////                modelsFragment.setArguments(arg);
////                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
////                        .replace(R.id.home_container, modelsFragment).addToBackStack(null)
////                        .commit();
//
//
//            }
//        });


//        Activity activity = (Activity) context;
//        final ScrollView scrollView = (ScrollView) activity.findViewById(R.id.search_new_cars_scrollview);
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                System.out.println("scroll touch");
//                parent.findViewById(R.id.gv_brands).getParent()
//                        .requestDisallowInterceptTouchEvent(false);
//                return false;
//            }
//        });
//
//
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                enableDisableViewGroup(parent, false);
                Bundle arg = new Bundle();
                arg.putSerializable("picture", pictures.get(position));
                PictureFragment pictureFragment = new PictureFragment();
                pictureFragment.setArguments(arg);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .add(R.id.home_container, pictureFragment).addToBackStack(null)
                        .commit();

            }
        });


        return view;
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setKeepScreenOn(enabled);
            if (view instanceof ViewGroup) {
                System.out.println("view group");
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }

    }
}
