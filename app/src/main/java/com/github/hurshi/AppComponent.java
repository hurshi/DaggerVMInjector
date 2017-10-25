package com.github.hurshi;

import javax.inject.Singleton;

import dagger.Component;
import github.hurshi.daggervminjector.extension.AndroidDaggerVMInjectionModule;

/**
 * Created by gavin on 2017/10/24.
 */

@Singleton
@Component(modules = {AndroidDaggerVMInjectionModule.class
        , _GPDaggerVMInjectModule.class
})
public interface AppComponent {
    void inject(App app);
}
