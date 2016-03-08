package com.orcchg.myapplication.database.repository;

import android.support.annotation.NonNull;

import com.orcchg.myapplication.model.Post;

/**
 * Created by MAXA on 08.03.2016.
 */
public interface PostSpecification {
    boolean specified(@NonNull Post post);
    String getSelectionArgs();
}
