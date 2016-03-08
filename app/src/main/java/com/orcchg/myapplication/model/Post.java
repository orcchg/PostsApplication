package com.orcchg.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MAXA on 07.03.2016.
 */
public class Post {
    @SerializedName("id") private int mId;
    @SerializedName("userId") private int mUserId;
    @SerializedName("title") private String mTitle;
    @SerializedName("body") private String mBody;

    public int getId() {
        return mId;
    }
    public int getUserId() { return mUserId; }
    public String getTitle() {
        return mTitle;
    }
    public String getDescription() {
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
