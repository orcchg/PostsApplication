package com.orcchg.myapplication.presenter.posts;

import com.orcchg.myapplication.model.Post;
import com.orcchg.myapplication.network.RestAdapter;
import com.orcchg.myapplication.presenter.base.BasePresenter;
import com.orcchg.myapplication.view.base.MvpView;
import com.orcchg.myapplication.view.posts.PostsActivity;

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

    private final RestAdapter mRestAdapter;
    private Subscription mSubscription;

    public PostsPresenter(MvpView view) {
        mRestAdapter = RestAdapter.Creator.create();
    }

    @Override
    public void onDetachView(boolean retainInstance) {
        super.onDetachView(retainInstance);
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void loadPosts() {
        Timber.d("Load posts");
        mSubscription = mRestAdapter.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    public void loadPostsNoRx() {
        Timber.d("Load posts deprecated");
        mRestAdapter.getPostsNoRx().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                onPostsLoaded(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Timber.e("Network error !");
            }
        });
    }

    private void onPostsLoaded(List<Post> posts) {
        for (Post post : posts) {
            Timber.d(post.toString());
        }
        if (isViewAttached()) {
            getView().showPosts(posts);
        }
    }
}
