package com.orcchg.myapplication.database.repository;

import com.orcchg.myapplication.model.interfaces.IPost;

import java.util.List;

import rx.Observable;

/**
 * Created by MAXA on 08.03.2016.
 */
public interface PostsRepository {

    void addPosts(List<IPost> posts);
    void updatePosts(List<IPost> posts);
    void removePosts(List<IPost> posts);

    Observable<List<IPost>> getAllPosts();
    Observable<List<IPost>> queryPosts(PostSpecification specification);
}
