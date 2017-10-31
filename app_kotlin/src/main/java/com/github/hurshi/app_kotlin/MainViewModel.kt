package com.github.hurshi.app_kotlin

import android.app.Application
import android.util.Log
import com.github.hurshi.app_kotlin.bean.Dog
import com.github.hurshi.app_kotlin.scopes.ActivityScope
import github.hurshi.daggervminjector.annotation.BindVMModule
import github.hurshi.daggervminjector.extension.AndroidDaggerVMInjection
import github.hurshi.daggervminjector.extension.DaggerVM
import javax.inject.Inject

/**
 * Created by gavin on 2017/10/24.
 */

@BindVMModule(module = MainModule::class, scope = ActivityScope::class)
class MainViewModel(application: Application) : DaggerVM {

    @Inject
    lateinit var dog: Dog

    init {
        AndroidDaggerVMInjection.inject(application, this)
    }


    fun dogString() {
        Log.d(">>> dog toString = ", dog!!.toString())
    }
}
