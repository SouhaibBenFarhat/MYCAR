package com.esprit.nolife.carsstore.activities.models_activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.background_tasks.ModelsManager;
import com.esprit.nolife.carsstore.entities.Brand;
import com.squareup.picasso.Picasso;

public class ModelsActivity extends AppCompatActivity {


    RecyclerView modelsRecyclerView;
    LinearLayoutManager modeLinearLayoutManager;
    ModelsManager modelsManager;
    ImageView brandCover;
    Brand brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_model);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        brandCover = (ImageView) findViewById(R.id.backdrop);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            brand = (Brand) b.getSerializable("brand");
            System.out.println(brand.getCover() + "cover");
            toolbar.setTitle(brand.getBrand());
            Glide.with(getApplicationContext()).load(Uri.parse(brand.getCover())).into(brandCover);
            modelsRecyclerView = (RecyclerView) findViewById(R.id.rv_models);
            modeLinearLayoutManager = new GridLayoutManager(this, 2);
            modelsRecyclerView.setLayoutManager(modeLinearLayoutManager);
            modelsManager = new ModelsManager(this, modelsRecyclerView);
            modelsManager.getModels(Integer.parseInt(brand.getBrandId()));

        }
//        Picasso.with(getContext()).load(Uri.parse(brand.getCover())).fit().into(brandCover);

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
                    collapsingToolbar.setTitle(brand.getBrand());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


}
