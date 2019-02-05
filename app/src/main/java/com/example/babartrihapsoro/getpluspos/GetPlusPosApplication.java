package com.example.babartrihapsoro.getpluspos;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.babartrihapsoro.getpluspos.di.components.ApplicationComponent;
import com.example.babartrihapsoro.getpluspos.di.components.DaggerApplicationComponent;
import com.example.babartrihapsoro.getpluspos.di.module.ApplicationModule;

public class GetPlusPosApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    private void initInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    // Install multi dex compatible
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
