package com.github.hurshi;

import android.app.Application;
import android.util.Log;

import com.github.hurshi.bean.Dog;
import com.github.hurshi.scopes.ActivityScope;

import javax.inject.Inject;

import github.hurshi.daggervminjector.annotation.BindVMModule;
import github.hurshi.daggervminjector.extension.AndroidDaggerVMInjection;
import github.hurshi.daggervminjector.extension.DaggerVM;

/**
 * Created by gavin on 2017/10/24.
 */

@BindVMModule(module = MainModule.class, scope = ActivityScope.class)
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
