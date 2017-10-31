package com.github.hurshi.app_kotlin

import dagger.Component
import github.hurshi.daggervminjector.extension.AndroidDaggerVMInjectionModule
import javax.inject.Singleton

/**
 * Created by gavin on 2017/10/24.
 */

@Singleton
@Component(modules = arrayOf(AndroidDaggerVMInjectionModule::class
        , _GPDaggerVMInjectModule::class
))
interface AppComponent {
    fun inject(app: App)
}
