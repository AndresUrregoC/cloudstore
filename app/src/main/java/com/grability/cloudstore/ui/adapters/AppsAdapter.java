package com.grability.cloudstore.ui.adapters;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grability.cloudstore.ui.activities.DetailActivity;
import com.grability.cloudstore.R;
import com.grability.cloudstore.entities.App;
import com.grability.cloudstore.utils.AnimateUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AndresDev on 22/02/16.
 */
public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    private static List<App> listApps;

    public void setListApps(List<App> listApps) {
        AppsAdapter.listApps = listApps;
    }

    public void deleteListApps(){
        listApps.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        @Bind(R.id.cvBase) CardView cvBase;
        @Bind(R.id.ivIcon) CircleImageView ivIcon;
        @Bind(R.id.tvName) TextView tvName;
        @Bind(R.id.tvCompany) TextView tvCompany;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailActivity.class)
                    .putExtra("APP_ID", listApps.get(getAdapterPosition()).getID());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Pair<View, String>[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(ivIcon, "shared_image");
                pairs[1] = new Pair<View, String>(tvName, "shared_text");
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) v.getContext(), pairs);
                v.getContext().startActivity(intent, options.toBundle());
            }else{
                v.getContext().startActivity(intent);
            }
        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(AppsAdapter.ViewHolder holder, int position) {

        Glide.with(holder.ivIcon.getContext()).load(listApps.get(position).getIMAGE()).into(holder.ivIcon);
        holder.tvName.setText(listApps.get(position).getNAME());
        holder.tvCompany.setText(listApps.get(position).getCOMPANY());

        new AnimateUtil().setAnimation(holder.cvBase, position, android.R.anim.slide_in_left, 500);
    }


    @Override
    public int getItemCount() {
        return listApps.size();
    }
}
