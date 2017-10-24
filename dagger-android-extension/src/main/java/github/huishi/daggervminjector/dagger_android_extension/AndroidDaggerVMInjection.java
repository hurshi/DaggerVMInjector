/*
 * Copyright (C) 2017 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.huishi.daggervminjector.dagger_android_extension;

import android.app.Application;
import android.util.Log;

import dagger.android.AndroidInjector;
import dagger.internal.Beta;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Injects core Android types from support libraries.
 */
@Beta
public final class AndroidDaggerVMInjection {
    private static final String TAG = "dagger.android.support";

    public static void inject(Application application, DaggerVM daggerVM) {
        checkNotNull(application, "application");
        checkNotNull(daggerVM, "daggerVM");
        HasDaggerVMInjector hasDaggerVMInjector = findHasDaggerVMInjector(application);
        Log.d(
                TAG,
                String.format(
                        "An injector for %s was found in %s",
                        daggerVM.getClass().getCanonicalName(),
                        hasDaggerVMInjector.getClass().getCanonicalName()));

        AndroidInjector<DaggerVM> daggerVMAndroidInjector =
                hasDaggerVMInjector.daggerVMInjector();
        checkNotNull(
                daggerVMAndroidInjector,
                "%s.daggerVMInjector() returned null",
                daggerVMAndroidInjector.getClass().getCanonicalName());

        daggerVMAndroidInjector.inject(daggerVM);
    }

    private static HasDaggerVMInjector findHasDaggerVMInjector(Application application) {
        if (application instanceof HasDaggerVMInjector) {
            return (HasDaggerVMInjector) application;
        }
        throw new IllegalArgumentException(
                String.format("No injector was found for %s", application.getClass().getCanonicalName()));
    }

    private AndroidDaggerVMInjection() {
    }
}
