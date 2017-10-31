package com.github.hurshi.app_kotlin.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Gavin on 16/12/15.
 */


@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {
}
