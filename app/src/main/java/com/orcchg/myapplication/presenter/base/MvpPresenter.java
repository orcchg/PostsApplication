package com.orcchg.myapplication.presenter.base;

import com.orcchg.myapplication.view.base.MvpView;

/**
 * Created by MAXA on 07.03.2016.
 */
public interface MvpPresenter<V extends MvpView> {
    void onAttachView(V view);
    void onDetachView(boolean retainInstance);
}
