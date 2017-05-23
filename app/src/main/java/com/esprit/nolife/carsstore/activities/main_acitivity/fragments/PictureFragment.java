package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.entities.Picture;
import com.esprit.nolife.carsstore.utils.SwipeBackLayout;
import com.squareup.picasso.Picasso;

public class PictureFragment extends DialogFragment {

    LinearLayout header, footer;
    ImageView imageModel;
    ImageButton backButton;
    SwipeBackLayout swipeBackLayout;


    private static final String TAG = "AKDialogFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_picture, container, false);


        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        header = (LinearLayout) getActivity().findViewById(R.id.header_picture);
        footer = (LinearLayout) getActivity().findViewById(R.id.footer_picture);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = getActivity().getSupportFragmentManager().findFragmentById(R.id.home_container);
                if (f != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(f).commit();
                }
            }
        });
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = getActivity().getSupportFragmentManager().findFragmentById(R.id.home_container);
                if (f != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(f).commit();
                }
            }
        });


        backButton = (ImageButton) getActivity().findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = getActivity().getSupportFragmentManager().findFragmentById(R.id.home_container);
                if (f != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(f).commit();
                }
            }
        });

        Picture picture = (Picture) getArguments().getSerializable("picture");
        imageModel = (ImageView) getActivity().findViewById(R.id.image_model);
        Picasso.with(getContext()).load(Uri.parse(picture.getUrl())).into(imageModel);

        swipeBackLayout = (SwipeBackLayout) getActivity().findViewById(R.id.swipe_layout);
        swipeBackLayout.setEnableFlingBack(false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_save) {
//            // handle confirmation button click here
//            return true;
//        } else if (id == android.R.id.home) {
//            // handle close button click here
//            dismiss();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
