package com.orcchg.myapplication.database.repository;

import android.support.annotation.NonNull;

import com.orcchg.myapplication.model.interfaces.IPost;

/**
 * Created by MAXA on 08.03.2016.
 */
public interface PostSpecification {
    boolean specified(@NonNull IPost post);
    String getSelectionArgs();
}
