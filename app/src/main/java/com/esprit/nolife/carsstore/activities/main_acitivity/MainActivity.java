package com.esprit.nolife.carsstore.activities.main_acitivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.login_activity.LoginActivity;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.AProposFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.AccueilFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.FavorisFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.FilterFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.GammeDetailFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.PictureFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.SearchNewCarsFragment;
import com.esprit.nolife.carsstore.activities.mes_annonces_acitivity.MesAnnoncesActivity;
import com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments.PublierAnnonceFragment;
import com.esprit.nolife.carsstore.utils.ManageUserPreferences;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener, com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener {

    String userProfileUrl;
    ContextMenuDialogFragment mMenuDialogFragment;
    Intent intent;
    FragmentManager fragmentManager;
    boolean backPresse;
    public static EditText etSearchFeaturedCars;
    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL.setIpServer();
        fragmentManager = getSupportFragmentManager();
        backPresse = false;
        intent = new Intent(MainActivity.this, LoginActivity.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                userProfileUrl = user.getPhotoUrl().toString();
            }

        }
        etSearchFeaturedCars = (EditText) findViewById(R.id.et_search_featured_cars);
        etSearchFeaturedCars.setVisibility(View.INVISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//Recupération de la nav_header

        View headerView = navigationView.getHeaderView(0);
        CircleImageView circleImageView = (CircleImageView) headerView.findViewById(R.id.user_profile_image);
        TextView tvUsername = (TextView) headerView.findViewById(R.id.tv_username_header);
        TextView tvEmail = (TextView) headerView.findViewById(R.id.tv_user_email_header);
        tvUsername.setText(user.getDisplayName());
        tvEmail.setText(user.getEmail());
        Picasso.with(this).load(user.getPhotoUrl())
                .into(circleImageView);
        getSupportFragmentManager().beginTransaction().add(R.id.home_container, new AccueilFragment())
                .commit();


        //    initToolbar();
        initMenuFragment();

    }


    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));

        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }


    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject close = new MenuObject();
        close.setResource(R.drawable.ic_close);
        MenuObject send = new MenuObject("FILTER");
        send.setResource(R.drawable.filter);
        menuObjects.add(close);
        menuObjects.add(send);

        return menuObjects;
    }

    public void initToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }


        if (backPresse) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        if (((f instanceof AccueilFragment))) {

            this.backPresse = true;
            Toast.makeText(this, "Cliquez une autre fois pour quitter...", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPresse = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
//            alertDialog.setTitle("IP ADDRESS");
//
//
//            final EditText input = new EditText(MainActivity.this);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT);
//            input.setText("172.16.131.154");
//            input.setLayoutParams(lp);
//            alertDialog.setView(input);
//
//
//            alertDialog.setPositiveButton("YES",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            URL.setIpServer(input.getText().toString());
//                            Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);
//                            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.home_container, f).commit();
//                        }
//                    });
//
//            alertDialog.setNegativeButton("NO",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//            alertDialog.show();
//        }
//        return true;

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search_new_cars) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);
            if ((f != null && f instanceof SearchNewCarsFragment)) {
                Toast.makeText(this, "SEARCH NEW CARS", Toast.LENGTH_SHORT).show();
            } else {

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("SearchNewCarsFragment");
                if (fragment != null) {

                    getSupportFragmentManager().beginTransaction()
//
                            .remove(fragment).add(R.id.home_container, new SearchNewCarsFragment(), "SearchNewCarsFragment")
                            .addToBackStack("SearchNewCarsFragment")
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()

                            .add(R.id.home_container, new SearchNewCarsFragment(), "SearchNewCarsFragment")
                            .addToBackStack("SearchNewCarsFragment")
                            .commit();
//                    .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
                }


            }
        } else if (id == R.id.nav_mes_annonces) {
            Intent intent = new Intent(MainActivity.this, MesAnnoncesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sell_car) {


            Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);
            if ((f != null && f instanceof PublierAnnonceFragment)) {
                Toast.makeText(this, "Sell your Car", Toast.LENGTH_SHORT).show();
            } else {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.home_container, new PublierAnnonceFragment(), PublierAnnonceFragment.class.toString())
                        .addToBackStack(PublierAnnonceFragment.class.toString())
                        .commit();

            }


        } else if (id == R.id.nav_favoris) {


            Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);
            if ((f != null && f instanceof FavorisFragment) || (f != null && f instanceof GammeDetailFragment) || f != null && f instanceof PictureFragment) {
                Toast.makeText(this, "Favoris", Toast.LENGTH_SHORT).show();
            } else {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.home_container, new FavorisFragment(), FavorisFragment.class.toString())
                        .addToBackStack(FavorisFragment.class.toString())
                        .commit();

            }


        } else if (id == R.id.nav_home) {

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);
            if ((f != null && f instanceof AccueilFragment)) {
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
            } else {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.home_container, new AccueilFragment())
                        .commit();

            }


        } else if (id == R.id.about) {

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);
            if ((f != null && f instanceof AProposFragment)) {
                Toast.makeText(this, "A propos", Toast.LENGTH_SHORT).show();
            } else {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.home_container, new AProposFragment(), AProposFragment.class.toString())
                        .addToBackStack(AProposFragment.class.toString())
                        .commit();

            }


        } else if (id == R.id.sign_out) {

            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("Se deconnecter");
            builder.setIcon(R.drawable.logout);

            builder.setMessage("Voulez vous quittez...?")
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    ManageUserPreferences manageUserPreferences = new ManageUserPreferences();
                    manageUserPreferences.setUserState(activity, "disconnected");
                }
            });

            builder.create();
            builder.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {

        if (position == 1) {

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_container);

            if (f != null && !(f instanceof FilterFragment)) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.entre_from_right, R.anim.exit_to_right)
                        .add(R.id.home_container, new FilterFragment(), FilterFragment.class.toString())
                        .addToBackStack(FilterFragment.class.toString())
                        .commit();
            } else {
                return;
            }

        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }

    public void enableFabButton(final FloatingActionButton actionButton) {
        actionButton.setVisibility(View.INVISIBLE);
    }

}
