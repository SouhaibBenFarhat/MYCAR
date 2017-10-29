package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.FavorisFragment;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.GammeDetailFragment;
import com.esprit.nolife.carsstore.background_tasks.FavorisManager;
import com.esprit.nolife.carsstore.background_tasks.RatingManager;
import com.esprit.nolife.carsstore.entities.Gamme;
import com.esprit.nolife.carsstore.entities.Motorisation;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Souhaib on 12/11/2016.
 */

public class GammeRecyclarViewAdapter extends RecyclerView.Adapter<GammeRecyclarViewAdapter.CustomViewHolder> {

    private final Motorisation motorisation = new Motorisation();
    public List<Gamme> gammes;
    private Context mContext;


    public GammeRecyclarViewAdapter(Context context, List<Gamme> gammes) {
        this.gammes = gammes;
        this.mContext = context;

    }

    @Override
    public GammeRecyclarViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gamme_item, null, false);
        GammeRecyclarViewAdapter.CustomViewHolder viewHolder = new GammeRecyclarViewAdapter.CustomViewHolder(view, mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GammeRecyclarViewAdapter.CustomViewHolder customViewHolder, final int i) {
        customViewHolder.gammeName.setText(gammes.get(i).getGamme());
        customViewHolder.gammePrice.setText(gammes.get(i).getPrix());
        Glide.with(mContext).load(Uri.parse(gammes.get(i).getPictureUrl()))
                .into(customViewHolder.imageGamme);
        customViewHolder.menuModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(customViewHolder.menuModel, gammes.get(i));
            }
        });

        RatingManager ratingManager = new RatingManager(mContext);
        ratingManager.getRatingValue(gammes.get(i).getModelId(), customViewHolder.modelRating, customViewHolder.ratingValue);


        Fragment f = ((FragmentActivity) mContext).getSupportFragmentManager().findFragmentById(R.id.home_container);
        if (f instanceof FavorisFragment) {
            customViewHolder.menuModel.setVisibility(View.INVISIBLE);

        }
    }

    private void showPopupMenu(View view, Gamme gamme) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_model, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(gamme, mContext));
        popup.show();
    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        Gamme gamme;
        Context mContext;

        public MyMenuItemClickListener(Gamme gamme, Context mContext) {
            this.gamme = gamme;
            this.mContext = mContext;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    FavorisManager favorisManager = new FavorisManager(mContext);
                    favorisManager.addUserFavoris(FirebaseAuth.getInstance().getCurrentUser().getUid(), gamme.getId());
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return (null != gammes ? gammes.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageGamme;
        protected TextView gammeName;
        protected TextView gammePrice;
        protected ImageView menuModel;
        protected RatingBar modelRating;
        protected TextView ratingValue;
        Context context;


        public CustomViewHolder(View view, final Context context) {
            super(view);
            this.context = context;
            this.imageGamme = (ImageView) view.findViewById(R.id.iv_gamme);
            this.gammeName = (TextView) view.findViewById(R.id.tv_gamme_name);
            this.gammePrice = (TextView) view.findViewById(R.id.tv_gamme_price);
            this.menuModel = (ImageView) view.findViewById(R.id.overflow);
            this.modelRating = (RatingBar) view.findViewById(R.id.MyRating);
            this.ratingValue = (TextView) view.findViewById(R.id.rating_value);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle arg = new Bundle();
                    GammeDetailFragment gammeFragment = new GammeDetailFragment();
                    arg.putSerializable("gamme", gammes.get(getLayoutPosition()));
                    gammeFragment.setArguments(arg);
                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                            .add(R.id.home_container, gammeFragment).addToBackStack(null)
                            .commit();

                }
            });

            Fragment f = ((FragmentActivity) mContext).getSupportFragmentManager().findFragmentById(R.id.home_container);
            if (f instanceof FavorisFragment) {
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        ;
                        alert.setTitle("Favoris");
                        alert.setMessage("Voulez-vous supprimer?");


                        alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FavorisManager favorisManager = new FavorisManager(context);
                                favorisManager.deleteUserFavoris(FirebaseAuth.getInstance().getCurrentUser().getUid(), gammes.get(getLayoutPosition()).getId());
                                gammes.remove(getLayoutPosition());

                                notifyDataSetChanged();
                            }
                        });

                        alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        });

                        alert.show();


                        return true;
                    }
                });

            }
        }
    }
}

