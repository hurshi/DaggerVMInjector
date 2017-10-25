package com.github.hurshi;

import android.app.Application;
import android.util.Log;

import javax.inject.Inject;

import github.hurshi.daggervminjector.annotation.TargetActivityModule;
import github.hurshi.daggervminjector.extension.AndroidDaggerVMInjection;
import github.hurshi.daggervminjector.extension.DaggerVM;

/**
 * Created by gavin on 2017/10/24.
 */

@TargetActivityModule(MainModule.class)
public class MainViewModel implements DaggerVM {

    @Inject
    Dog dog;

    public MainViewModel(Application application) {
        AndroidDaggerVMInjection.inject(application, this);
    }


    public void dogString() {
        Log.d(">>> dog toString = ", dog.toString());
    }
}
