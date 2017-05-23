package com.esprit.nolife.carsstore.activities.filter_search_result;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.FilterFragment;
import com.esprit.nolife.carsstore.background_tasks.GammeManager;
import com.squareup.picasso.Picasso;

public class FilterResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView filterBackDrop;
    private TextView desole, aucun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.filter_toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("");


        desole = (TextView) findViewById(R.id.desole);
        aucun = (TextView) findViewById(R.id.aucun);
        desole.setVisibility(View.INVISIBLE);
        aucun.setVisibility(View.INVISIBLE);

        initCollapsingToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        filterBackDrop = (ImageView) findViewById(R.id.filter_backdrop);
        Picasso.with(this).load(Uri.parse(FilterFragment.selectedBrand.getCover())).into(filterBackDrop);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GammeManager gammeManager = new GammeManager(this);
        gammeManager.fetchFilter(recyclerView, desole, aucun);

    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
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
