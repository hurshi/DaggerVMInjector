package com.github.hurshi.app_kotlin

import com.github.hurshi.app_kotlin.bean.Dog
import com.github.hurshi.app_kotlin.scopes.ActivityScope

import dagger.Module
import dagger.Provides

/**
 * Created by gavin on 2017/10/24.
 */

@Module
class MainModule {


    @ActivityScope
    @Provides
    fun provideDog(): Dog {
        return Dog("旺财", 3)
    }


}
