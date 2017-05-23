package com.esprit.nolife.carsstore.activities.album_photo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.background_tasks.GammeManager;
import com.esprit.nolife.carsstore.entities.Gamme;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

public class PhotoGalleryActivity extends AppCompatActivity {

    Gamme gamme;


    private ScrollGalleryView scrollGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gamme = (Gamme) extras.getSerializable("gamme");
        }

        GammeManager gammeManager = new GammeManager(this);
        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        gammeManager.getGammePhoto(Integer.parseInt(gamme.getId()),scrollGalleryView);
    }


}
