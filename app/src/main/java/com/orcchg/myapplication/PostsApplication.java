package com.orcchg.myapplication;

import android.app.Application;

import com.orcchg.myapplication.core.DataManager;
import com.orcchg.myapplication.database.PostsDatabase;

import timber.log.Timber;

/**
 * Created by MAXA on 07.03.2016.
 */
public class PostsApplication extends Application {

    private DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        mDataManager = new DataManager(getApplicationContext());
    }

    public DataManager getDataManager() {
        return mDataManager;
    }
}
