package com.esprit.nolife.carsstore.activities.news_detail_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.album_photo.NewsGalleryPhotoActivity;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.AlbumGridAdapter;
import com.esprit.nolife.carsstore.entities.CarMagazine;
import com.esprit.nolife.carsstore.entities.Picture;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {

    TextView tvContentNews, tvAuthor, tvNewsTitle;
    ImageView imgNewdThumbnail;
    GridView imagesNews;
    AppCompatButton btnShareFacebook, btnMoreDetail, newsAlbumButton;


    private CallbackManager callbackManager;
    private LoginManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        final NewsDetailActivity newsDetailActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_model);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        btnMoreDetail = (AppCompatButton) findViewById(R.id.btn_more_detail);
        btnShareFacebook = (AppCompatButton) findViewById(R.id.btn_facebook_share);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Bundle arg = getIntent().getExtras();

        final CarMagazine carMagazine = (CarMagazine) arg.getSerializable("carMagazine");


        AppCompatButton moreDetail = (AppCompatButton) findViewById(R.id.btn_more_detail);
        moreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = carMagazine.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        btnShareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog shareDialog;
                FacebookSdk.sdkInitialize(NewsDetailActivity.this);
                shareDialog = new ShareDialog(newsDetailActivity);
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(carMagazine.getTitle())
                        .setContentDescription(
                                carMagazine.getDescription())

                        .setContentUrl(Uri.parse(carMagazine.getLink())).build();
                shareDialog.show(linkContent);
            }
        });


        Document doc1 = null;
        try {
            doc1 = Jsoup.connect(carMagazine.getLink()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tvContentNews = (TextView) findViewById(R.id.news_content);


        try {
            Element contentDiv = doc1.select("div[id=detail_news]").first();
            String text = contentDiv.getElementsByTag("div").text();
            tvContentNews.setText(text);

            Display display = getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);


            tvNewsTitle = (TextView) findViewById(R.id.news_detail_title);
            tvNewsTitle.setText(carMagazine.getTitle());
            tvAuthor = (TextView) findViewById(R.id.news_detail_author);
            tvAuthor.setText(carMagazine.getAuthor());

            imgNewdThumbnail = (ImageView) findViewById(R.id.backdrop);
            Picasso.with(getApplication()).load(Uri.parse(carMagazine.getThumbnailUrl().replace("min", "max")))
                    .into(imgNewdThumbnail);


            imagesNews = (GridView) findViewById(R.id.gv_img_news);
            final ArrayList<Picture> pictures = new ArrayList<>();

            Elements elements = doc1.getAllElements();
            for (Element e : elements) {
                if (e.hasClass("grid-gallery")) {
                    Elements elts = e.getAllElements();
                    for (Element e2 : elts) {
                        if (e2.hasClass("grid-item ") || e.hasClass("grid-item st-2x")) {
                            Picture picture = new Picture();
                            picture.setUrl(e2.attr("style").replace("background-image:url(", "").replace(")", ""));
                            pictures.add(picture);
                        }
                    }

                }
            }
            LinearLayout layoutPhoto = (LinearLayout) findViewById(R.id.news_photo_layout);
            if (pictures.size() == 0) {
                layoutPhoto.getLayoutParams().height = 0;
            }
            imagesNews.setAdapter(new AlbumGridAdapter(this, R.layout.album_item, pictures));
            imagesNews.setNumColumns(4);

            newsAlbumButton = (AppCompatButton) findViewById(R.id.news_album_photo_button);
            newsAlbumButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle arg = new Bundle();
                    arg.putSerializable("pictures", pictures);
                    Intent intent = new Intent(NewsDetailActivity.this, NewsGalleryPhotoActivity.class);
                    intent.putExtras(arg);
                    startActivity(intent);

                }
            });
        } catch (NullPointerException e) {
            finish();
            Toast.makeText(this, "Probléme de chargement", Toast.LENGTH_SHORT).show();
        }
    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}

