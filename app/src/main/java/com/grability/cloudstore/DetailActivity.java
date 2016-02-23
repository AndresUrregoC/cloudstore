package com.grability.cloudstore;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grability.cloudstore.dao.AppDao;
import com.grability.cloudstore.entities.App;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_layout) CollapsingToolbarLayout ctlLayout;
    @Bind(R.id.toolbarDetail) Toolbar toolbar;
    @Bind(R.id.ivIconApp) CircleImageView ivIconApp;
    @Bind(R.id.tvNameApp) TextView tvNameApp;
    @Bind(R.id.tvCompanyApp) TextView tvCompanyApp;
    @Bind(R.id.tvSummary) TextView tvSummary;
    @Bind(R.id.tvRights) TextView tvRights;

    private List<App> listAppSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        try {
            listAppSelected = new AppDao(this).getAppById(getIntent().getIntExtra("APP_ID", 0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ctlLayout.setTitle(listAppSelected.get(0).getNAME());
        Glide.with(this).load(listAppSelected.get(0).getIMAGE()).into(ivIconApp);
        tvNameApp.setText(listAppSelected.get(0).getNAME());
        tvCompanyApp.setText(listAppSelected.get(0).getCOMPANY());
        tvSummary.setText(listAppSelected.get(0).getSUMMARY());
        tvRights.setText(listAppSelected.get(0).getRIGHTS());

    }

}
