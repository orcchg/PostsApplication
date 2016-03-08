package com.orcchg.myapplication.view.posts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.orcchg.myapplication.R;
import com.orcchg.myapplication.model.Post;
import com.orcchg.myapplication.presenter.posts.PostsPresenter;
import com.orcchg.myapplication.view.base.BaseLceActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostsActivity extends BaseLceActivity<PostsPresenter> {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.rv_posts) RecyclerView mPostsListView;

    private PostsAdapter mPostsAdapter;

    @Override
    protected PostsPresenter createPresenter() {
        return new PostsPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mPostsListView.setHasFixedSize(true);
        mPostsListView.setLayoutManager(new LinearLayoutManager(this));
        mPostsAdapter = new PostsAdapter();
        mPostsListView.setAdapter(mPostsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @OnClick(R.id.fab)
    public void loadPosts() {
        mPresenter.loadPosts();
    }

    public void setPosts(List<Post> posts) {
        mPostsAdapter.setPosts(posts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_invalidate:
                mPresenter.onInvalidateClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
