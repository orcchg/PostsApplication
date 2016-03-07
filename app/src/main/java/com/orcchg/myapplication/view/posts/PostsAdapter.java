package com.orcchg.myapplication.view.posts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orcchg.myapplication.R;
import com.orcchg.myapplication.model.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MAXA on 07.03.2016.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    private final List<Post> mPosts;

    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_post_title) TextView mTitleView;
        @Bind(R.id.tv_post_description) TextView mDescriptionView;

        public PostsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public PostsAdapter() {
        mPosts = new ArrayList<>();
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_post, parent, false);
        PostsViewHolder holder = new PostsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.mTitleView.setText(post.getTitle());
        holder.mDescriptionView.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void setPosts(List<Post> posts) {
        if (posts != null && !posts.isEmpty()) {
            mPosts.clear();
            mPosts.addAll(posts);
            notifyDataSetChanged();
        }
    }
}
