package com.orcchg.myapplication.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.orcchg.myapplication.presenter.base.MvpPresenter;

/**
 * Created by MAXA on 07.03.2016.
 */
public abstract class BaseView<P extends MvpPresenter> extends AppCompatActivity implements MvpView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.onAttachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetachView(false);
        mPresenter = null;
    }
}
