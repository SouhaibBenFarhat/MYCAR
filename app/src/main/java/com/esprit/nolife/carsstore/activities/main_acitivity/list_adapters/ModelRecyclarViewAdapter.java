package com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.URL.URL;
import com.esprit.nolife.carsstore.activities.main_acitivity.fragments.ModelDetailFragment;
import com.esprit.nolife.carsstore.background_tasks.RatingManager;
import com.esprit.nolife.carsstore.connection_instance.ConnectionSingleton;
import com.esprit.nolife.carsstore.entities.Gamme;
import com.esprit.nolife.carsstore.entities.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Souhaib on 11/11/2016.
 */

public class ModelRecyclarViewAdapter extends RecyclerView.Adapter<ModelRecyclarViewAdapter.CustomViewHolder> {


    private List<Model> models;
    private Context mContext;
    LinearLayout.LayoutParams llpSmall;
    LinearLayout.LayoutParams llpBig;


    public ModelRecyclarViewAdapter(Context context, List<Model> models) {
        this.models = models;
        this.mContext = context;
        llpSmall = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llpSmall.setMargins(20, 0, 0, 0); // llpSmall.setMargins(left, top, right, bottom);

        llpBig = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llpBig.setMargins(50, 0, 0, 0); // llpSmall.setMargins(left, top, right, bottom);

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_item, null, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {


        customViewHolder.modelName.setText(models.get(i).getName());
        customViewHolder.tvModelDescription.setText(models.get(i).getDescription()+"...");
        customViewHolder.modelPrice.setText(models.get(i).getPriceFrom());
        customViewHolder.tvModelDescription.setText(models.get(i).getDescription());
        Picasso.with(mContext).load(Uri.parse(models.get(i).getPicture().getUrl())).resize(600, 350)
                .into(customViewHolder.imageModel);
        customViewHolder.userRatingValue.setText("--");
        customViewHolder.modelName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        final RatingManager ratingManager = new RatingManager(mContext);
        ratingManager.getRatingValue(models.get(i).getId(), customViewHolder.ratingSum, customViewHolder.ratingValue);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DID_USER_RATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals("0")) {
                            ratingManager.getUserRating(models.get(i).getId(), FirebaseAuth.getInstance().getCurrentUser().getUid(), customViewHolder.userRating, customViewHolder.userRatingValue);
                            customViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(mContext, "Vous avez déja donné un avis", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            customViewHolder.userRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()

                                                                                     {
                                                                                         @Override
                                                                                         public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {


                                                                                             new AlertDialog.Builder(mContext)
                                                                                                     .setTitle("INFO ")
                                                                                                     .setMessage("Vous n'aurez plus la possiblité de changer votre avis..")
                                                                                                     .setPositiveButton("J'ACCEPTE", new DialogInterface.OnClickListener() {
                                                                                                         public void onClick(DialogInterface dialog, int which) {
                                                                                                             RatingManager ratingManager = new RatingManager(mContext);
                                                                                                             ratingManager.insertModelRating(models.get(i).getId(), FirebaseAuth.getInstance().getCurrentUser().getUid(), String.valueOf(customViewHolder.userRating.getRating()));
                                                                                                             customViewHolder.userRating.setIsIndicator(true);
                                                                                                             customViewHolder.userRatingValue.setText(String.valueOf(customViewHolder.userRating.getRating()));
                                                                                                             customViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                                                                                                                 @Override
                                                                                                                 public void onClick(View v) {
                                                                                                                     Toast.makeText(mContext, "Vous avez déja donné un avis", Toast.LENGTH_SHORT).show();
                                                                                                                 }
                                                                                                             });
                                                                                                         }
                                                                                                     })
                                                                                                     .setNegativeButton("RETOUR", new DialogInterface.OnClickListener() {
                                                                                                         public void onClick(DialogInterface dialog, int which) {
                                                                                                             // do nothing
                                                                                                         }
                                                                                                     })
                                                                                                     .setIcon(android.R.drawable.ic_dialog_alert)
                                                                                                     .show();


                                                                                         }
                                                                                     }

                            );
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Probléme de connexion", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("model_id", models.get(i).getId());
                params.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return params;
            }

        };


        ConnectionSingleton.getInstance(mContext).addToRequestque(stringRequest);


        final ArrayList<Gamme> gammeNames = new ArrayList<>();
        String request = URL.GET_GAMME_NAME_BY_MODEL + "?model_id=" + String.valueOf(models.get(i).getId());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                while (count < response.length()) {
                    try {
                        Gamme gamme = new Gamme();
                        JSONObject jsonObject = response.getJSONObject(count);
                        gamme.setId(jsonObject.getString("id"));
                        gamme.setGamme(jsonObject.getString("gamme"));
                        gamme.setPrix(jsonObject.getString("prix"));
                        gamme.setModelId(jsonObject.getString("model_id"));
                        gammeNames.add(gamme);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                customViewHolder.expandableLayoutContainer.removeAllViews();
                for (int j = 0; j < gammeNames.size(); j++) {


                    LinearLayout parent = new LinearLayout(mContext);
                    parent.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                    parent.setOrientation(LinearLayout.VERTICAL);


                    TextView modelName = new TextView(mContext);
                    modelName.setTextAppearance(mContext, android.R.style.TextAppearance_Material_Headline);
                    modelName.setTextSize(12);
                    modelName.setTypeface(Typeface.DEFAULT_BOLD);
                    modelName.setText(gammeNames.get(j).getGamme());
                    modelName.setLayoutParams(llpSmall);

//                    TextView modelPrix = new TextView(mContext);
//                    modelPrix.setTextAppearance(mContext, android.R.style.TextAppearance_Material_Headline);
//                    modelPrix.setTextSize(12);
//                    modelPrix.setTypeface(null, Typeface.NORMAL);
//                    modelPrix.setText(gammeNames.get(j).getPrix());
//                    modelPrix.setLayoutParams(llpBig);


//                    TextView dt = new TextView(mContext);
//                    dt.setTextAppearance(mContext, android.R.style.TextAppearance_Material_Headline);
//                    dt.setTextSize(12);
//                    dt.setTypeface(Typeface.DEFAULT_BOLD);
//                    dt.setText("DT");
//                    dt.setTextColor(Color.parseColor("#f04031"));
//                    dt.setLayoutParams(llpSmall);


                    parent.addView(modelName);
//                    parent.addView(modelPrix);
//                    parent.addView(dt);


                    customViewHolder.expandableLayoutContainer.addView(parent);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Probléme de connexion", Toast.LENGTH_SHORT).show();
            }
        }

        );
        ConnectionSingleton.getInstance(mContext).

                addToRequestque(jsonArrayRequest);


    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_model, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return (null != models ? models.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageModel;
        protected TextView modelName;
        protected TextView modelPrice;
        protected RelativeLayout expandableRelativeLayout;
        protected TextView tvModelDescription;
        protected LinearLayout expandableLayoutContainer;
        protected RatingBar userRating;
        protected RatingBar ratingSum;
        protected TextView ratingValue;
        protected TextView userRatingValue;
        protected LinearLayout linearLayout;

        public CustomViewHolder(View view) {
            super(view);
            this.imageModel = (ImageView) view.findViewById(R.id.iv_model);
            this.modelName = (TextView) view.findViewById(R.id.tv_model_name);
            this.modelPrice = (TextView) view.findViewById(R.id.tv_model_price);
            this.expandableRelativeLayout = (RelativeLayout) view.findViewById(R.id.description_ex_layout);
            this.tvModelDescription = (TextView) view.findViewById(R.id.tv_model_description);
            this.expandableLayoutContainer = (LinearLayout) view.findViewById(R.id.expandable_layout_container);
            this.tvModelDescription = (TextView) view.findViewById(R.id.tv_model_description);
            this.userRating = (RatingBar) view.findViewById(R.id.rating);
            this.ratingSum = (RatingBar) view.findViewById(R.id.MyRating);
            this.ratingValue = (TextView) view.findViewById(R.id.rating_value);
            this.userRatingValue = (TextView) view.findViewById(R.id.user_rating_value);
            this.linearLayout = (LinearLayout) view.findViewById(R.id.layout);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle arg = new Bundle();
                    ModelDetailFragment modelDetailFragment = new ModelDetailFragment();
                    arg.putSerializable("model", models.get(getLayoutPosition()));
                    modelDetailFragment.setArguments(arg);
                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                            .add(R.id.home_container, modelDetailFragment).addToBackStack(null)
                            .commit();
//                    ActionBar mActionBar = ((AppCompatActivity) mContext).getSupportActionBar();
//                    mActionBar.hide();


                }
            });
        }
    }
}
