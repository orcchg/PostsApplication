package com.orcchg.myapplication.database.repository;

import com.orcchg.myapplication.model.Post;

import java.util.List;

import rx.Observable;

/**
 * Created by MAXA on 08.03.2016.
 */
public interface PostsRepository {

    void addPosts(List<Post> posts);
    void updatePosts(List<Post> posts);
    void removePosts(List<Post> posts);

    Observable<List<Post>> getAllPosts();
    Observable<List<Post>> queryPosts(PostSpecification specification);
}
