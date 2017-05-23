package com.esprit.nolife.carsstore.activities.mes_annonces_acitivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.AnnonceRecyclerAdapter;
import com.esprit.nolife.carsstore.background_tasks.AnnonceManager;

public class MesAnnoncesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AnnonceRecyclerAdapter annonceRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces);
        recyclerView = (RecyclerView) findViewById(R.id.rc_Myannoce);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        AnnonceManager annonceManager = new AnnonceManager(this, recyclerView);
        annonceManager.getMesAnnonce();

        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return false;
    }


}
