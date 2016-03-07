package com.orcchg.myapplication;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by MAXA on 07.03.2016.
 */
public class PostsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
