package com.orcchg.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MAXA on 07.03.2016.
 */
public class Post {
    @SerializedName("userId") private int mUserId;
    @SerializedName("id") private int mId;
    @SerializedName("title") private String mTitle;
    @SerializedName("body") private String mBody;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    @Override
    public String toString() {
        return "Post{" +
                "mUserId=" + mUserId +
                ", mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mBody='" + mBody + '\'' +
                '}';
    }
}
