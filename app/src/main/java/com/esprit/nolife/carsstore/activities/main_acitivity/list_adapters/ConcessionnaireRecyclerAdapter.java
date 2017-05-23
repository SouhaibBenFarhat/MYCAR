package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.concessionnaure_detail_activity.ConcessionnaireDetailActivity;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.AllConcessionnairesFragment;
import com.esprit.nolife.carsstore.entities.Concessionnaire;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Souhaib on 18/11/2016.
 */

public class ConcessionnaireRecyclerAdapter extends RecyclerView.Adapter<ConcessionnaireRecyclerAdapter.CustomViewHolder> {
    private List<Concessionnaire> concessionnaires;
    private Context mContext;

    public ConcessionnaireRecyclerAdapter(Context context, List<Concessionnaire> concessionnaires) {
        this.concessionnaires = concessionnaires;
        this.mContext = context;


    }

    @Override
    public ConcessionnaireRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_concessionnaire, null);
        ConcessionnaireRecyclerAdapter.CustomViewHolder viewHolder = new ConcessionnaireRecyclerAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ConcessionnaireRecyclerAdapter.CustomViewHolder customViewHolder, int i) {
        final Concessionnaire concessionnaire = concessionnaires.get(i);
        customViewHolder.concessionnaireName.setText(concessionnaire.getName());

        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.home_container);
        if (f instanceof AllConcessionnairesFragment) {
            customViewHolder.concessionnaireDescription.setVisibility(View.VISIBLE);
            customViewHolder.concessionnaireDescription.setText(concessionnaire.getDescription());
            customViewHolder.fieldToHide.setVisibility(View.VISIBLE);

        } else {
            customViewHolder.concessionnaireDescription.setVisibility(View.INVISIBLE);
            customViewHolder.concessionnaireDescription.setText("");
            customViewHolder.fieldToHide.setVisibility(View.INVISIBLE);
        }
        Picasso.with(mContext).load(Uri.parse(concessionnaire.getLogo())).resize(800, 600).into(customViewHolder.concessionnaireLogo);
    }

    @Override
    public int getItemCount() {
        return (null != concessionnaires ? concessionnaires.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView concessionnaireLogo;
        protected TextView concessionnaireName;
        protected TextView fieldToHide;
        protected TextView concessionnaireDescription;


        public CustomViewHolder(View view) {
            super(view);
            this.concessionnaireLogo = (ImageView) view.findViewById(R.id.iv_concessionnaire_logo);
            this.concessionnaireName = (TextView) view.findViewById(R.id.tv_concessionnaire_name);
            this.concessionnaireDescription = (TextView) view.findViewById(R.id.tv_concessionnaire_description);
            this.fieldToHide = (TextView) view.findViewById(R.id.feild_to_hide);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Concessionnaire concessionnaire = concessionnaires.get(getLayoutPosition());

                    Bundle arg = new Bundle();
                    arg.putSerializable("concessionnaire", concessionnaire);
                    Intent intent = new Intent(((Activity) mContext), ConcessionnaireDetailActivity.class);
                    intent.putExtras(arg);
                    ((Activity) mContext).startActivity(intent);
//                    ConcessionnaireDetailFragment concessionnaireDetailFragment = new ConcessionnaireDetailFragment();
//                    concessionnaireDetailFragment.setArguments(arg);
//                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
//                            .add(R.id.home_container, concessionnaireDetailFragment, ConcessionnaireDetailFragment.class.toString())
//                            .addToBackStack(ConcessionnaireDetailFragment.class.toString()).commit();
                }
            });
        }
    }
}

