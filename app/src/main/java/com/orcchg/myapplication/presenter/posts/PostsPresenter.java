package com.orcchg.myapplication.presenter.posts;

import android.app.Activity;

import com.orcchg.myapplication.PostsApplication;
import com.orcchg.myapplication.core.DataManager;
import com.orcchg.myapplication.model.Post;
import com.orcchg.myapplication.presenter.base.BasePresenter;
import com.orcchg.myapplication.view.base.MvpView;
import com.orcchg.myapplication.view.posts.PostsActivity;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by MAXA on 07.03.2016.
 */
public class PostsPresenter extends BasePresenter<PostsActivity> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    public PostsPresenter(MvpView view) {
        mDataManager = ((PostsApplication) ((Activity) view).getApplication()).getDataManager();
    }

    @Override
    public void onDetachView(boolean retainInstance) {
        super.onDetachView(retainInstance);
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void onResume() {
        showContent();
    }

    public void onInvalidateClicked() {
        mDataManager.invalidateCache(true);
    }

    public void loadPosts() {
        Timber.d("Load posts");
        showLoading();
        mSubscription = mDataManager.getPosts()
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Network error: " + e);
                        showError();
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        onPostsLoaded(posts);
                    }
                });
    }

    private void showLoading() {
        if (isViewAttached()) {
            getView().showLoading();
        }
    }

    private void showContent() {
        if (isViewAttached()) {
            getView().showContent();
        }
    }

    private void showError() {
        if (isViewAttached()) {
            getView().showError();
        }
    }

    private void onPostsLoaded(List<Post> posts) {
        mDataManager.getDatabase().addPosts(posts);
        mDataManager.invalidateCache(false);
        if (isViewAttached()) {
            getView().showContent();
            getView().setPosts(posts);
        }
    }
}
