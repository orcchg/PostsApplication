package com.orcchg.myapplication.database.repository;

import android.support.annotation.NonNull;

import com.orcchg.myapplication.database.PostsContract;
import com.orcchg.myapplication.model.interfaces.IPost;

/**
 * Created by MAXA on 08.03.2016.
 */
public class ByIdPostSpecification implements PostSpecification {

    private final int mId;

    public ByIdPostSpecification(int id) {
        mId = id;
    }

    @Override
    public boolean specified(@NonNull IPost post) {
        return post.getId() == mId;
    }

    @Override
    public String getSelectionArgs() {
        return PostsContract.PostsTable.COLUMN_NAME_ID + " = " + Integer.toString(mId);
    }
}
