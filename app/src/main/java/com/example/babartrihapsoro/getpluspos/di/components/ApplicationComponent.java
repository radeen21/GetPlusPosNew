package com.example.babartrihapsoro.getpluspos.di.components;


import com.example.babartrihapsoro.getpluspos.base.BaseActivity;
import com.example.babartrihapsoro.getpluspos.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

}
