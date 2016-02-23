package com.grability.cloudstore.api;

import com.grability.cloudstore.api.models.Apps;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by AndresDev on 21/02/16.
 */
public interface apiItunes {

    @GET("/topfreeapplications/limit=20/json")
    void getAllApps(Callback<Apps> listApps);
}
