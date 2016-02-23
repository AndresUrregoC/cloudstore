package com.grability.cloudstore.adapters;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.grability.cloudstore.DetailActivity;
import com.grability.cloudstore.R;
import com.grability.cloudstore.api.models.Apps;
import com.grability.cloudstore.dao.AppDao;
import com.grability.cloudstore.dao.CategoryDao;
import com.grability.cloudstore.entities.App;
import com.grability.cloudstore.entities.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AndresDev on 22/02/16.
 */
public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    private static List<App> listApps;
    private List<String> listCategories = new ArrayList<>();
    private List<App> listAppsForCategories = new ArrayList<>();

    public void setListApps(List<App> listApps) {
        AppsAdapter.listApps = listApps;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.lItemCategory) LinearLayout lItemCategory;
        @Bind(R.id.lItemApp) LinearLayout lItemApp;
        @Bind(R.id.tvTop) TextView tvTop;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_apps, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(AppsAdapter.ViewHolder holder, int position) {

        try {
            for (int i = 0; i < new CategoryDao(holder.lItemApp.getContext()).getAllCategories().size(); i++) {
                holder.lItemCategory.addView(createButtonCategory(new CategoryDao(holder.lItemApp.getContext()).getAllCategories().get(i).getNAME(), holder.lItemCategory, holder.lItemApp, holder.tvTop));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (App app: listApps) {
            holder.lItemApp.addView(createCardViewApp(app, holder.lItemApp));
        }
    }

    private View createCardViewApp(final App app, ViewGroup parent) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        CircleImageView ivIcon = ButterKnife.findById(card, R.id.ivIcon);
        TextView tvName = ButterKnife.findById(card, R.id.tvName);
        TextView tvCompany = ButterKnife.findById(card, R.id.tvCompany);
        CardView cvBase = ButterKnife.findById(card, R.id.cvBase);

        Glide.with(parent.getContext()).load(app.getIMAGE()).into(ivIcon);
        tvName.setText(app.getNAME());
        tvCompany.setText(app.getCOMPANY());

        cvBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), DetailActivity.class)
                        .putExtra("APP_ID", app.getID()));
                //Toast.makeText(v.getContext(), app.getNAME(), Toast.LENGTH_SHORT).show();
            }
        });

        return card;
    }

    private View createButtonCategory(final String name, final ViewGroup parent, final ViewGroup layout, final TextView tvTop){

        View button = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        final Button btCategory = ButterKnife.findById(button, R.id.btCategory);
        btCategory.setText(name);
        btCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btCategory.setTextColor(Color.WHITE);
                tvTop.setText("Top Free " + name);
                layout.removeAllViews();

                try {
                    for (App app: new AppDao(parent.getContext()).getAppsByCategory(name)) {
                        layout.addView(createCardViewApp(app, layout));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        return button;
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
