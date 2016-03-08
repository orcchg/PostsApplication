package com.orcchg.myapplication.core;

import android.content.Context;

import com.orcchg.myapplication.database.PostsDatabase;
import com.orcchg.myapplication.model.Post;
import com.orcchg.myapplication.network.RestAdapter;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MAXA on 08.03.2016.
 */
public class DataManager {

    private final RestAdapter mRestAdapter;
    private final PostsDatabase mDatabase;

    private boolean mInvalidateCache;

    public DataManager(Context context) {
        mRestAdapter = RestAdapter.Creator.create();
        mDatabase = new PostsDatabase(context);
    }

    public RestAdapter getRestAdapter() {
        return mRestAdapter;
    }

    public PostsDatabase getDatabase() {
        return mDatabase;
    }

    public void invalidateCache(boolean flag) {
        mInvalidateCache = flag;
    }

    public Observable<List<Post>> getPosts() {
        if (mInvalidateCache || mDatabase.isEmpty()) {
            return mRestAdapter.getPosts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return mDatabase.getAllPosts().flatMap(new Func1<List<Post>, Observable<List<Post>>>() {
                @Override
                public Observable<List<Post>> call(List<Post> posts) {
                    return Observable.just(posts);
                }
            }).subscribeOn(Schedulers.computation())
              .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
