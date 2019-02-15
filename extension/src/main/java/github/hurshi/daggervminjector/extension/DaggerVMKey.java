package github.hurshi.daggervminjector.extension;

import java.lang.annotation.Target;

import dagger.MapKey;
import dagger.internal.Beta;

import static java.lang.annotation.ElementType.METHOD;

/**
 * Created by gavin on 2017/9/28.
 */


@Beta
@MapKey
@Target(METHOD)
public @interface DaggerVMKey {
    Class<?> value();
}
