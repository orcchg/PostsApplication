package com.orcchg.myapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.orcchg.myapplication.database.repository.PostSpecification;
import com.orcchg.myapplication.database.repository.PostsRepository;
import com.orcchg.myapplication.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by MAXA on 08.03.2016.
 */
public class PostsDatabase extends SQLiteOpenHelper implements PostsRepository {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PostsDatabase.db";

    public PostsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_POSTS_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS " + PostsContract.PostsTable.TABLE_NAME + " (" +
            PostsContract.PostsTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY DEFAULT 0, " +
            PostsContract.PostsTable.COLUMN_NAME_USER_ID + " INTEGER DEFAULT 0, " +
            PostsContract.PostsTable.COLUMN_NAME_TITLE + " TEXT DEFAULT \"\", " +
            PostsContract.PostsTable.COLUMN_NAME_BODY + " TEXT DEFAULT \"\");";

    private static final String READ_ALL_POSTS_STATEMENT =
            "SELECT * FROM " + PostsContract.PostsTable.TABLE_NAME;

    private static final String READ_POSTS_STATEMENT = READ_ALL_POSTS_STATEMENT + " WHERE %s ";

    private static final String INSERT_POSTS_STATEMENT =
            "INSERT OR REPLACE INTO " + PostsContract.PostsTable.TABLE_NAME + " (" +
            PostsContract.PostsTable.COLUMN_NAME_ID + ", " +
            PostsContract.PostsTable.COLUMN_NAME_USER_ID + ", " +
            PostsContract.PostsTable.COLUMN_NAME_TITLE + ", " +
            PostsContract.PostsTable.COLUMN_NAME_BODY + ") " +
            "VALUES(?, ?, ?, ?);";

    private static final String DELETE_POSTS_STATEMENT =
            "DELETE FROM " + PostsContract.PostsTable.TABLE_NAME +
            " WHERE " + PostsContract.PostsTable.COLUMN_NAME_ID + " IN (%s);";

    private static final String CLEAR_POSTS_TABLE_STATEMENT =
            "DROP TABLE IF EXISTS " + PostsContract.PostsTable.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POSTS_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CLEAR_POSTS_TABLE_STATEMENT);
        onCreate(db);
    }

    public boolean isEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(READ_ALL_POSTS_STATEMENT, null);
        boolean result = true;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0) == 0;
        }
        cursor.close();
        return result;
    }

    /* Repository */
    // --------------------------------------------------------------------------------------------
    @Override
    public void addPosts(List<Post> posts) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        SQLiteStatement insert = db.compileStatement(INSERT_POSTS_STATEMENT);

        for (Post post : posts) {
            insert.bindLong(1, post.getId());
            insert.bindLong(2, post.getUserId());
            insert.bindString(3, post.getTitle());
            insert.bindString(4, post.getDescription());
            insert.execute();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void updatePosts(List<Post> posts) {
        addPosts(posts);  // using REPLACE clause
    }

    @Override
    public void removePosts(List<Post> posts) {
        final List<Integer> ids = new ArrayList<>();

        Observable.just(posts).flatMap(new Func1<List<Post>, Observable<Post>>() {
            @Override
            public Observable<Post> call(List<Post> posts) {
                return Observable.from(posts);
            }
        }).map(new Func1<Post, Integer>() {
            @Override
            public Integer call(Post post) {
                return post.getId();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Integer integer) {
                ids.add(integer);
            }
        });

        String args = TextUtils.join(", ", ids);
        String statement = String.format(DELETE_POSTS_STATEMENT, args);

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        SQLiteStatement delete = db.compileStatement(statement);
        delete.execute();
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public Observable<List<Post>> getAllPosts() {
        return queryPosts(null);
    }

    @Override
    public Observable<List<Post>> queryPosts(@Nullable PostSpecification specification) {
        final String statement = specification == null ? READ_ALL_POSTS_STATEMENT : String.format(READ_POSTS_STATEMENT, specification.getSelectionArgs());

        final SQLiteDatabase db = getReadableDatabase();

        final Callable<List<Post>> function = new Callable<List<Post>>() {
            @Override
            public List<Post> call() throws Exception {
                Cursor cursor = db.rawQuery(statement, null);
                List<Post> posts = new ArrayList<>();
                while (cursor.moveToNext()) {
                    posts.add(PostFactory.create(cursor));
                }
                cursor.close();
                return posts;
            }
        };

        return Observable.create(new Observable.OnSubscribe<List<Post>>() {
            @Override
            public void call(Subscriber<? super List<Post>> subscriber) {
                try {
                    subscriber.onNext(function.call());
                } catch (Exception e) {
                    Timber.e("Error reading from the database", e);
                }
            }
        });
    }

    /* Factory */
    // --------------------------------------------------------------------------------------------
    private static class PostFactory {
        static Post create(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndex(PostsContract.PostsTable.COLUMN_NAME_ID));
            int userId = cursor.getInt(cursor.getColumnIndex(PostsContract.PostsTable.COLUMN_NAME_USER_ID));
            String title = cursor.getString(cursor.getColumnIndex(PostsContract.PostsTable.COLUMN_NAME_TITLE));
            String body = cursor.getString(cursor.getColumnIndex(PostsContract.PostsTable.COLUMN_NAME_BODY));
            return new Post(id, userId, title, body);
        }
    }
}
