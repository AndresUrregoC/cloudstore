package com.grability.cloudstore.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ndczz.infinityloading.InfinityLoading;
import com.grability.cloudstore.R;
import com.grability.cloudstore.adapters.AppsAdapter;
import com.grability.cloudstore.api.apiItunes;
import com.grability.cloudstore.api.models.Apps;
import com.grability.cloudstore.dao.AppDao;
import com.grability.cloudstore.dao.CategoryDao;
import com.grability.cloudstore.entities.App;
import com.grability.cloudstore.entities.Category;
import com.grability.cloudstore.services.ServiceGeneratorApi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by AndresDev on 21/02/16.
 */
public class AppsFragment extends Fragment {

    @Bind(R.id.rvBase) RecyclerView rvBase;
    @Bind(R.id.loading) InfinityLoading loading;
    private AppsAdapter appsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        ButterKnife.bind(this, view);
        loading.setVisibility(View.VISIBLE);
        setupRecyclerContent();
        return view;
    }

    private void setupRecyclerContent(){
        rvBase.setHasFixedSize(true);
        rvBase.setLayoutManager(new LinearLayoutManager(getActivity()));
        appsAdapter = new AppsAdapter();
        setDataRecycler();
    }

    private void setDataRecycler(){
        apiItunes client = ServiceGeneratorApi.createService(apiItunes.class);
        client.getAllApps(new Callback<Apps>() {
            @Override
            public void success(Apps apps, Response response) {
                Log.i("Datos", apps.getFeed().getEntry().get(1).getName().getLabel());
                try {
                    setDataOffline(apps);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                try{
                    Log.e("Error", error.getMessage());
                    if(new AppDao(getActivity()).getAllApps().size()>0){
                        appsAdapter.setListApps(new AppDao(getActivity()).getAllApps());
                        rvBase.setAdapter(appsAdapter);
                        loading.setVisibility(View.GONE);
                    }else{
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "no hay datos", Toast.LENGTH_SHORT).show();
                    }
                }catch (RetrofitError e){
                    setDataRecycler();
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
            category.setID(i+"");
            category.setNAME(categoriesAux.get(i)+"");
            new CategoryDao(getActivity()).addCategory(category);
        }
        appsAdapter.setListApps(new AppDao(getActivity()).getAllApps());
        rvBase.setAdapter(appsAdapter);
        loading.setVisibility(View.GONE);

    }


}
