package com.github.hurshi;

import android.app.Application;
import android.util.Log;

import javax.inject.Inject;

import github.hurshi.daggervminjector.annotation.TargetActivityModule;
import me.hurshi.dagger_android_extension.AndroidDaggerVMInjection;
import me.hurshi.dagger_android_extension.DaggerVM;

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
