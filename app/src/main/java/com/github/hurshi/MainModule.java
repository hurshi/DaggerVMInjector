package com.github.hurshi;

import dagger.Module;
import dagger.Provides;
import github.hurshi.daggervminjector.annotation.ActivityScope;

/**
 * Created by gavin on 2017/10/24.
 */

@Module
public class MainModule {


    @ActivityScope
    @Provides
    public Dog provideDog() {
        return new Dog("旺财", 3);
    }


}
