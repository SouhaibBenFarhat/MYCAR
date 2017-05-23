package com.esprit.nolife.carsstore.utils;

import android.content.Context;
import android.widget.ImageView;

import com.esprit.nolife.carsstore.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

/**
 * Created by Souhaib on 16/01/2017.
 */

public class PicassoImageLoader implements MediaLoader {

    private String url;

    public PicassoImageLoader(String url) {
        this.url = url;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final MediaLoader.SuccessCallback callback) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView, new ImageCallback(callback));
    }

    @Override
    public void loadThumbnail(Context context, ImageView thumbnailView, MediaLoader.SuccessCallback callback) {
        Picasso.with(context)
                .load(url)
                .resize(100, 100)
                .placeholder(R.drawable.placeholder_image)
                .centerInside()
                .into(thumbnailView, new ImageCallback(callback));
    }

    private static class ImageCallback implements Callback {

        private final MediaLoader.SuccessCallback callback;

        public ImageCallback(SuccessCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess() {
            callback.onSuccess();
        }

        @Override
        public void onError() {

        }
    }
}
