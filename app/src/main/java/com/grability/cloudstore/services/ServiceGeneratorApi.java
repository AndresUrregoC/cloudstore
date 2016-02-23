package com.grability.cloudstore.services;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by AndresDev on 21/02/16.
 */
public class ServiceGeneratorApi {

    private static final String API_BASE_URL = "https://itunes.apple.com/us/rss";

    public static <S> S createService(Class<S> serviceClass){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_BASE_URL)
                .build();
        return restAdapter.create(serviceClass);
    }
}
