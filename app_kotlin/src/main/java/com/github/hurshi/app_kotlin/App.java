package com.github.hurshi.app_kotlin;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import github.hurshi.daggervminjector.extension.DaggerVM;
import github.hurshi.daggervminjector.extension.HasDaggerVMInjector;

/**
 * Created by gavin on 2017/10/24.
 */

public class App extends Application implements HasActivityInjector, HasDaggerVMInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Inject
    DispatchingAndroidInjector<DaggerVM> daggerVmInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<DaggerVM> daggerVMInjector() {
        return daggerVmInjector;
    }
}
