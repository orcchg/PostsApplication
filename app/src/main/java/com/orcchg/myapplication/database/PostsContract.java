package com.orcchg.myapplication.database;

/**
 * Created by MAXA on 08.03.2016.
 */
public final class PostsContract {

    public PostsContract() {
        // protect from accidental instantiation
    }

    public static class PostsTable {
        public static final String TABLE_NAME = "PostsTable";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_USER_ID = "userId";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_BODY = "body";
    }
}
