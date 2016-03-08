package com.orcchg.myapplication.view.base;

import android.view.View;

import com.orcchg.myapplication.R;
import com.orcchg.myapplication.presenter.base.MvpPresenter;

import butterknife.Bind;

/**
 * Created by MAXA on 08.03.2016.
 */
public abstract class BaseLceActivity<P extends MvpPresenter> extends BaseActivity<P> implements MvpLceView {

    @Bind(R.id.loading_view) View mLoadingView;
    @Bind(R.id.content_view) View mContentView;
    @Bind(R.id.error_view) View mErrorView;

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.INVISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showContent() {
        mLoadingView.setVisibility(View.INVISIBLE);
        mContentView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError() {
        mLoadingView.setVisibility(View.INVISIBLE);
        mContentView.setVisibility(View.INVISIBLE);
        mErrorView.setVisibility(View.VISIBLE);
    }
}
