package com.haozhang.ptr.libary.base;

import android.view.View;

import com.haozhang.ptr.libary.PtrFootView;

/**
 * @author HaoZhang
 * @date 2016/2/17.
 */
public abstract class PtrBaseManager<T extends PtrBaseManager> {

    /**
     * 拓展scroll listener
     */
    protected PtrListeners.OnScrollListener onScrollListener;

    /**
     * 上拉加载的监听回调
     */
    protected PtrListeners.OnLoadMoreListener onLoadMoreListener;

    protected PtrListeners.OnItemClickListener onItemClickListener;

    protected PtrListeners.OnFootClickListener onFootClickListener;

    public abstract T setHeaderView(View view);

    public abstract T setFooterView(IPtrBaseFooter view);

    protected abstract T onLoadMorePrepare();

    protected abstract T onLoadMoreBackground();

    protected abstract T onLoadMoreCompleted();

    public abstract T setOnScrollListener(PtrListeners.OnScrollListener listener);

    public abstract T setOnLoadMoreListener(PtrListeners.OnLoadMoreListener listener);

    public abstract T setOnPtrItemClickListener(PtrListeners.OnItemClickListener listener);

    public abstract T setOnFootClickListener(PtrListeners.OnFootClickListener listener);

    public abstract PtrFootView getMeterialFooterView();

}
