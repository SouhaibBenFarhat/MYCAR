package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.album_photo.ModelPhotoGalleryActivity;
import com.esprit.nolife.carsstore.background_tasks.BrandManager;
import com.esprit.nolife.carsstore.background_tasks.GammeManager;
import com.esprit.nolife.carsstore.background_tasks.ModelsManager;
import com.esprit.nolife.carsstore.entities.Model;
import com.squareup.picasso.Picasso;


public class ModelDetailFragment extends Fragment {


    ImageView imgModel, imgGammeBrandLogo;
    GridView modelAlbum;
    RecyclerView rvGammes;
    LinearLayoutManager linearLayoutManager;
    TextView modelName, modelDescription, tvPhoto;
    RelativeLayout photoExpandableRelativeLayout;
    AppCompatButton galleryPhotoButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_model_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.gamme_fragment_container, new ModelImageFragment()).commit();
        final Model model = (Model) getArguments().getSerializable("model");


        photoExpandableRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.photo_ex_layout);
        tvPhoto = (TextView) getActivity().findViewById(R.id.tv_photo);
        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.allah_ghaleb);

        modelAlbum = (GridView) getActivity().findViewById(R.id.gv_model_album);
        ModelsManager modelsManager = new ModelsManager(getContext());
        modelsManager.getModelPhoto(Integer.parseInt(model.getId()), modelAlbum, photoExpandableRelativeLayout, linearLayout);

        modelName = (TextView) getActivity().findViewById(R.id.model_name);
        modelName.setText(model.getName());

        modelDescription = (TextView) getActivity().findViewById(R.id.tv_description);
        modelDescription.setText(model.getDescription());


        imgModel = (ImageView) getActivity().findViewById(R.id.model_img);
        imgGammeBrandLogo = (ImageView) getActivity().findViewById(R.id.img_gamme_brand_logo);


        BrandManager brandManager = new BrandManager(getContext());
        brandManager.getBrandLogo(Integer.parseInt(model.getBrandId()), imgGammeBrandLogo);


        Glide.with(getContext()).load(Uri.parse(model.getPicture().getUrl())).into(imgModel);
        rvGammes = (RecyclerView) getActivity().findViewById(R.id.rv_gammes);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvGammes.setLayoutManager(linearLayoutManager);
        GammeManager gammeManager = new GammeManager(getContext(), rvGammes);
        gammeManager.getGammes(Integer.parseInt(model.getId()));

        galleryPhotoButton = (AppCompatButton) getActivity().findViewById(R.id.album_photo_button);
        galleryPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle arg = new Bundle();
                arg.putSerializable("model",model);
                Intent intent
                        = new Intent(getActivity(), ModelPhotoGalleryActivity.class);
                intent.putExtras(arg);
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
