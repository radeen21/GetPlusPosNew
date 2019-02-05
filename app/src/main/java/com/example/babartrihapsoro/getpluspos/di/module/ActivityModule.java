package com.example.babartrihapsoro.getpluspos.di.module;

import com.example.babartrihapsoro.getpluspos.base.BaseActivity;
import com.example.babartrihapsoro.getpluspos.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    @PerActivity
    BaseActivity provideActivity() {
        return baseActivity;
    }

}
