package com.example.babartrihapsoro.getpluspos.di.components;

import com.example.babartrihapsoro.getpluspos.di.module.ActivityModule;
import com.example.babartrihapsoro.getpluspos.di.module.GetPlusModule;
import com.example.babartrihapsoro.getpluspos.di.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class,
        GetPlusModule.class})
public interface GetPlusComponent {
}
