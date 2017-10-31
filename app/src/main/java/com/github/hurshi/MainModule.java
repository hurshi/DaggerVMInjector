package com.github.hurshi;

import com.github.hurshi.bean.Dog;
import com.github.hurshi.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

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
