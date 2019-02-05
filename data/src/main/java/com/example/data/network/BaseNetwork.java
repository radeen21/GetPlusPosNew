package com.example.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseNetwork<T>  {

    private T networkService;

    protected abstract Class<T> getNetworkClass();

    protected abstract String getBaseUrl();

    public T getNetworkService() {
        if (networkService == null) {
            initNetworkInterface();
        }
        return networkService;
    }

    private void initNetworkInterface() {
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        networkService = retrofit.create(getNetworkClass());
    }

}
