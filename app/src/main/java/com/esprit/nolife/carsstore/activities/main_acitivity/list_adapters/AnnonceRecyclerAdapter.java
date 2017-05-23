package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.annonce_detail_activity.AnnonceDetailActivity;
import com.esprit.nolife.carsstore.entities.Annonce;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Firas on 18/12/2016.
 */

public class AnnonceRecyclerAdapter extends  RecyclerView.Adapter<AnnonceRecyclerAdapter.CustomViewHolder> {

   private Context context;
    private List<Annonce> annonces = Collections.emptyList();


    public AnnonceRecyclerAdapter(Context  context , List<Annonce> annonces){
        this.context = context;
        this.annonces = annonces;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_annoce, null);
       CustomViewHolder holder = new CustomViewHolder(view);
        return holder   ;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Annonce current = annonces.get(position);
        holder.tvBrand.setText(current.getBrand()+" "+current.getModel());
        holder.tvEtat.setText(current.getEtat());
        holder.tvTransmition.setText(current.getTransmission());
        holder.tvKilometrage.setText(current.getKilometrage());
        holder.tvCarburant.setText(current.getCarburant());
        holder.tvPrix.setText(current.getPrix()+" DNT");


        Picasso.with(context).load(Uri.parse(current.getPhoto()))
                .into(holder.ivAnnonce);

    }

    @Override
    public int getItemCount() {
        return (null != annonces ? annonces.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAnnonce;
        TextView tvBrand;
        TextView tvEtat;
        TextView tvTransmition;
        TextView tvKilometrage;
        TextView tvCarburant;
        TextView tvPrix;


        public CustomViewHolder(View itemView) {
            super(itemView);

            ivAnnonce = (ImageView) itemView.findViewById(R.id.iv_annonce) ;
            tvBrand = (TextView) itemView.findViewById(R.id.tv_brand_annoce);
            tvEtat = (TextView) itemView.findViewById(R.id.tv_etat_annonce);
            tvTransmition = (TextView) itemView.findViewById(R.id.tv_transmission_annonce);
            tvKilometrage = (TextView) itemView.findViewById(R.id.tv_kilometrage_annonce);
            tvCarburant = (TextView) itemView.findViewById(R.id.tv_carburant_annonce);
            tvPrix = (TextView) itemView.findViewById(R.id.tv_price_annoce);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ;
                    Annonce annonce= annonces.get(getLayoutPosition());
                    System.out.println(annonce.toString());
                    Intent intent = new Intent(context,AnnonceDetailActivity.class);
                    intent.putExtra("id",annonce.getId());
                    intent.putExtra("photo",annonce.getPhoto());
                    intent.putExtra("type",annonce.getType());
                    intent.putExtra("brand",annonce.getBrand());
                    intent.putExtra("model",annonce.getModel());
                    intent.putExtra("annee",annonce.getAnnee());
                    intent.putExtra("transmission",annonce.getTransmission());
                    intent.putExtra("puissance",annonce.getPuissance());
                    intent.putExtra("kilometrage",annonce.getKilometrage());
                    intent.putExtra("color",annonce.getColor());
                    intent.putExtra("portes",annonce.getNobrePortes());
                    intent.putExtra("carburant",annonce.getCarburant());
                    intent.putExtra("prix",annonce.getPrix());
                    intent.putExtra("num_tel",annonce.getNum_tel());
                    intent.putExtra("etat",annonce.getEtat());
                    intent.putExtra("user_id",annonce.getUser_id());
                    context.startActivity(intent);

                }
            });
         }


    }
}
