package com.grability.cloudstore.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ndczz.infinityloading.InfinityLoading;
import com.grability.cloudstore.R;
import com.grability.cloudstore.ui.adapters.AppsAdapter;
import com.grability.cloudstore.api.apiItunes;
import com.grability.cloudstore.models.Apps;
import com.grability.cloudstore.dao.AppDao;
import com.grability.cloudstore.dao.CategoryDao;
import com.grability.cloudstore.entities.App;
import com.grability.cloudstore.entities.Category;
import com.grability.cloudstore.services.ServiceGeneratorApi;
import com.grability.cloudstore.utils.AnimateUtil;
import com.grability.cloudstore.utils.OrientationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by AndresDev on 21/02/16.
 */
public class AppsFragment extends Fragment {

    @Bind(R.id.lItemCategory) LinearLayout lItemCategory;
    @Bind(R.id.tvTop) TextView tvTop;
    @Bind(R.id.rvBase) RecyclerView rvBase;
    @Bind(R.id.loading) InfinityLoading loading;
    @Bind(R.id.ivCloudSad) ImageView ivCloudSad;
    @Bind(R.id.btRetry) Button btRetry;
    private AppsAdapter appsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        ButterKnife.bind(this, view);
        loading.setVisibility(View.VISIBLE);
        getDataContent();
        return view;
    }

    @OnClick(R.id.btRetry)
    void clickRetry(){
        ivCloudSad.setVisibility(View.GONE);
        btRetry.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        getDataContent();
    }

    private void setupRecycler(){
        rvBase.setHasFixedSize(true);
        if (!OrientationUtil.isLarge(getActivity())) {
            rvBase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }else{
            rvBase.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        appsAdapter = new AppsAdapter();
    }

    private void getDataContent(){

        setupRecycler();

        apiItunes client = ServiceGeneratorApi.createService(apiItunes.class);
        client.getAllApps(new Callback<Apps>() {
            @Override
            public void success(Apps apps, Response response) {
                try {
                    setDataOffline(apps);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                try{
                    if(new AppDao(getActivity()).getAllApps().size()>0){
                        tvTop.setVisibility(View.VISIBLE);
                        setDataCategories();
                        appsAdapter.setListApps(new AppDao(getActivity()).getAllApps());
                        rvBase.setAdapter(appsAdapter);
                        loading.setVisibility(View.GONE);
                    }else{
                        loading.setVisibility(View.GONE);
                        ivCloudSad.setVisibility(View.VISIBLE);
                        btRetry.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "There is no internet connection", Toast.LENGTH_SHORT).show();
                    }
                }catch (RetrofitError e){
                    getDataContent();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDataOffline(Apps app) throws SQLException {

        List<Apps.Feed.Entry> listAppsOnline = app.getFeed().getEntry();
        List<String> categoriesAux = new ArrayList<>();

        if(new CategoryDao(getActivity()).getAllCategories().size()>0){
            new CategoryDao(getActivity()).deleteCategories();
            new AppDao(getActivity()).deleteApps();
        }

        for (int i = 0; i < listAppsOnline.size(); i++){
            App appObject = new App();
            if(categoriesAux.size()>0){
                if (!categoriesAux.contains(listAppsOnline.get(i).getCategory().getAttributes().getLabel())){
                    categoriesAux.add(listAppsOnline.get(i).getCategory().getAttributes().getLabel());
                }
            }else{
                categoriesAux.add(listAppsOnline.get(i).getCategory().getAttributes().getLabel());
            }

            appObject.setID(Integer.parseInt(listAppsOnline.get(i).getId().getAttributes().getId()));
            appObject.setNAME(listAppsOnline.get(i).getName().getLabel());
            appObject.setIMAGE(listAppsOnline.get(i).getImage().get(2).getLabel());
            appObject.setCATEGORY(listAppsOnline.get(i).getCategory().getAttributes().getLabel());
            appObject.setCOMPANY(listAppsOnline.get(i).getArtist().getLabel());
            appObject.setSUMMARY(listAppsOnline.get(i).getSummary().getLabel());
            appObject.setRIGHTS(listAppsOnline.get(i).getRights().getLabel());

            new AppDao(getActivity()).addApp(appObject);
        }

        for (int i = 0; i < categoriesAux.size(); i++){
            Category category = new Category();
            category.setNAME(categoriesAux.get(i)+"");
            new CategoryDao(getActivity()).addCategory(category);
        }
        tvTop.setVisibility(View.VISIBLE);
        setDataCategories();
        appsAdapter.setListApps(new AppDao(getActivity()).getAllApps());
        rvBase.setAdapter(appsAdapter);
        loading.setVisibility(View.GONE);

    }

    private void setDataCategories() {

        try {
            for (int i = 0; i < new CategoryDao(getActivity()).getAllCategories().size(); i++) {
                lItemCategory.addView(createButtonCategory(new CategoryDao(getActivity()).getAllCategories().get(i).getNAME(), lItemCategory, tvTop));
                new AnimateUtil().setAnimation(lItemCategory, i, android.R.anim.slide_in_left, 500);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private View createButtonCategory(final String name, final ViewGroup parent, final TextView tvTop){

        View button = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        final Button btCategory = ButterKnife.findById(button, R.id.btCategory);
        btCategory.setText(name);
        btCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvTop.setText("Top Free " + name);
                appsAdapter.deleteListApps();

                try {
                    appsAdapter.setListApps(new AppDao(parent.getContext()).getAppsByCategory(name));
                    rvBase.setAdapter(appsAdapter);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        return button;
    }

}
