package com.orcchg.myapplication.presenter.base;

import android.support.annotation.Nullable;

import com.orcchg.myapplication.view.base.MvpView;

import java.lang.ref.WeakReference;

/**
 * Created by MAXA on 07.03.2016.
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> mView;

    @Override
    public void onAttachView(V view) {
        mView = new WeakReference<V>(view);
    }

    @Override
    public void onDetachView(boolean retainInstance) {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

    public boolean isViewAttached() {
        return mView != null && mView.get() != null;
    }

    @Nullable
    public V getView() {
        return mView != null ? mView.get() : null;
    }
}
