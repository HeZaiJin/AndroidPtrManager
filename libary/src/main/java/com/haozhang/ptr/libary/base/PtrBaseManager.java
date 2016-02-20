package com.haozhang.ptr.libary.base;

import android.view.View;

import com.haozhang.ptr.libary.PtrFootView;

/**
 * @author HaoZhang
 * @date 2016/2/17.
 */
public abstract class PtrBaseManager<T extends PtrBaseManager> {


    public static final int STATUS_PREPARED = 0;
    public static final int STATUS_BACKGROUND = 1;
    public static final int STATUS_COMPLETED = -1;
    public int mStatus = STATUS_PREPARED;

    public abstract T setHeaderView(View view);

    public abstract T setFooterView(IPtrBaseFooter view);

    protected abstract T onLoadMorePrepare();

    protected abstract T onLoadMoreBackground();

    protected abstract T onLoadMoreCompleted();

    public abstract PtrFootView getMeterialFooterView();

}
