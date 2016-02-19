package com.haozhang.ptr.libary.manager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.haozhang.ptr.libary.PtrFootView;
import com.haozhang.ptr.libary.base.IPtrBaseFooter;
import com.haozhang.ptr.libary.base.PtrBaseManager;
import com.haozhang.ptr.libary.base.PtrListeners;

/**
 * @author HaoZhang
 * @date 2016/2/17.
 */
public class PtrRecyclerViewManager extends PtrBaseManager<PtrRecyclerViewManager> {

    private RecyclerView recyclerView;

    public PtrRecyclerViewManager(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    @Override
    public PtrRecyclerViewManager setHeaderView(View view) {
        return null;
    }

    @Override
    public PtrRecyclerViewManager setFooterView(IPtrBaseFooter view) {
        return null;
    }

    @Override
    protected PtrRecyclerViewManager onLoadMorePrepare() {
        return null;
    }

    @Override
    protected PtrRecyclerViewManager onLoadMoreBackground() {
        return null;
    }

    @Override
    protected PtrRecyclerViewManager onLoadMoreCompleted() {
        return null;
    }

    public PtrRecyclerViewManager setOnScrollListener(PtrListeners.OnScrollListener listener) {
        this.onScrollListener = listener;
        return this;
    }

    public PtrRecyclerViewManager setOnLoadMoreListener(PtrListeners.OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
        return this;
    }

    public PtrRecyclerViewManager setOnPtrItemClickListener(PtrListeners.OnItemClickListener listener) {
        this.onItemClickListener = listener;
        return this;
    }

    public PtrRecyclerViewManager setOnFootClickListener(PtrListeners.OnFootClickListener listener) {
        this.onFootClickListener = listener;
        return this;
    }

    @Override
    public PtrFootView getMeterialFooterView() {
        return null;
    }
}
