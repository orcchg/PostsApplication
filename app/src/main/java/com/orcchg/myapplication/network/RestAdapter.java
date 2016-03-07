package com.orcchg.myapplication.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orcchg.myapplication.model.Post;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by MAXA on 07.03.2016.
 */
public interface RestAdapter {

    String ENDPOINT = "http://jsonplaceholder.typicode.com";

    public static class Creator {
        public static RestAdapter create() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RestAdapter.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(RestAdapter.class);
        }
    }

    @GET("/posts/")
    Observable<List<Post>> getPosts();

    @GET("/posts/")
    Call<List<Post>> getPostsNoRx();
}
