package com.orcchg.myapplication.model;

import com.google.gson.annotations.SerializedName;
import com.orcchg.myapplication.model.interfaces.IPost;

/**
 * Created by MAXA on 07.03.2016.
 */
public class Post implements IPost {
    @SerializedName("id") private int mId;
    @SerializedName("userId") private int mUserId;
    @SerializedName("title") private String mTitle;
    @SerializedName("body") private String mBody;

    @Override public int getId() {
        return mId;
    }
    @Override public int getUserId() { return mUserId; }
    @Override public String getTitle() {
        return mTitle;
    }
    @Override public String getDescription() {
        return mBody;
    }

    public Post(int id, int userId, String title, String body) {
        mId = id;
        mUserId = userId;
        mTitle = title;
        mBody = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "mId=" + mId +
                ", mUserId=" + mUserId +
                ", mTitle='" + mTitle + '\'' +
                ", mBody='" + mBody + '\'' +
                '}';
    }
}
