package com.orcchg.myapplication.presenter.posts;

import android.app.Activity;
import android.view.View;

import com.orcchg.myapplication.PostsApplication;
import com.orcchg.myapplication.core.DataManager;
import com.orcchg.myapplication.model.Post;
import com.orcchg.myapplication.model.interfaces.IPost;
import com.orcchg.myapplication.network.RestAdapter;
import com.orcchg.myapplication.presenter.base.BasePresenter;
import com.orcchg.myapplication.view.base.MvpView;
import com.orcchg.myapplication.view.posts.PostsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
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
        showProgress();
        mSubscription = mDataManager.getPosts()
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Network error !");
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        onPostsLoaded(posts);
                    }
                });
    }

    private void showContent() {
        if (isViewAttached()) {
            getView().showContent();
        }
    }

    private void showProgress() {
        if (isViewAttached()) {
            getView().showProgress();
        }
    }

    private void onPostsLoaded(List<Post> posts) {
        List<IPost> iPosts = new ArrayList<>();
        for (Post post : posts) {
            iPosts.add(post);
        }
        mDataManager.getDatabase().addPosts(iPosts);
        mDataManager.invalidateCache(false);
        if (isViewAttached()) {
            getView().showPosts(iPosts);
        }
    }
}
