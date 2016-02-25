package com.grability.cloudstore.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grability.cloudstore.R;
import com.grability.cloudstore.dao.AppDao;
import com.grability.cloudstore.entities.App;
import com.grability.cloudstore.utils.OrientationUtil;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        OrientationUtil.adjustScreenOrientation(this);
        setupToolbar();
        setDataContent();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setDataContent() {
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

    @OnClick(R.id.btInstall)
    void clickInstall(){
        createDialog("Not enough external storage", "\""+listAppSelected.get(0).getNAME() + "\" can't be downloaded. Remove apps " +
                "or content you no longer need, and try again", true);
    }

    @OnClick(R.id.tvReadmore)
    void clickReadmore(){
        createDialog(listAppSelected.get(0).getNAME(), listAppSelected.get(0).getSUMMARY(), false);
    }

    private void createDialog(String title, String message, boolean setButtons){
        AlertDialog.Builder dialogMessage = new AlertDialog.Builder(this);
        dialogMessage.setTitle(title);
        dialogMessage.setMessage(message);
        if(setButtons){
            dialogMessage.setPositiveButton("VIEW STORAGE", null);
            dialogMessage.setNegativeButton("CANCEL", null);
        }
        dialogMessage.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void finishActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }
}
