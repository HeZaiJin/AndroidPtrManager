package com.haozhang.ptr.libary.manager;

import android.view.View;

import com.haozhang.ptr.libary.base.IPtrBaseFooter;
import com.haozhang.ptr.libary.base.PtrBaseManager;
import com.haozhang.ptr.libary.base.PtrListeners;

/**
 * @author HaoZhang
 * @date 2016/2/17.
 */
public class PtrRecyclerViewManager extends PtrBaseManager {

    @Override
    public PtrBaseManager setHeaderView(View view) {
        return null;
    }

    @Override
    public PtrBaseManager setFooterView(IPtrBaseFooter view) {
        return null;
    }

    @Override
    public PtrBaseManager onLoadMorePrepare() {
        return null;
    }

    @Override
    public PtrBaseManager onLoadMoreBackground() {
        return null;
    }

    @Override
    public PtrBaseManager onLoadMoreCompleted() {
        return null;
    }

    @Override
    public PtrBaseManager setOnScrollListener(PtrListeners.OnScrollListener listener) {
        return null;
    }

    @Override
    public PtrBaseManager setOnLoadMoreListener(PtrListeners.OnLoadMoreListener listener) {
        return null;
    }

    @Override
    public PtrBaseManager setOnPtrItemClickListener(PtrListeners.OnItemClickListener listener) {
        return null;
    }

    @Override
    public PtrBaseManager setOnFootClickListener(PtrListeners.OnFootClickListener listener) {
        return null;
    }
}
