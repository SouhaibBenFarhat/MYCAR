package com.esprit.nolife.carsstore.activities.album_photo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.entities.Picture;
import com.esprit.nolife.carsstore.utils.PicassoImageLoader;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import java.util.ArrayList;
import java.util.List;

public class NewsGalleryPhotoActivity extends AppCompatActivity {
    private ScrollGalleryView scrollGalleryView;

    ArrayList<Picture> pictures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_gallery_photo);
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pictures = (ArrayList<Picture>) extras.getSerializable("pictures");
        }


        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);

        List<MediaInfo> infos = new ArrayList<>(pictures.size());
        for (Picture url : pictures) {
            infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url.getUrl())));
        }
        scrollGalleryView
                .setThumbnailSize(100)
                .setZoom(true)
                .setFragmentManager(this.getSupportFragmentManager())
                .addMedia(infos);

    }
}
