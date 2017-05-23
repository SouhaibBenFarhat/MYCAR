package com.esprit.nolife.carsstore.activities.album_photo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.background_tasks.ModelsManager;
import com.esprit.nolife.carsstore.entities.Model;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;


public class ModelPhotoGalleryActivity extends AppCompatActivity {

    Model model;
    private ScrollGalleryView scrollGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_photo_gallery);
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model = (Model) extras.getSerializable("model");
        }

        ModelsManager modelsManager = new ModelsManager(this);
        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        modelsManager.getModelPhoto(Integer.parseInt(model.getId()),scrollGalleryView);
    }
}

