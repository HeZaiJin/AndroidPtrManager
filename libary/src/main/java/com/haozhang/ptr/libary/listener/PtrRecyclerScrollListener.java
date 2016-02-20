package com.haozhang.ptr.libary.listener;

import android.support.v7.widget.RecyclerView;

/**
 * RecyclerView的ScrollListener，主要是判断滑动到底部，进行加载模式
 * @author HaoZhang
 * @date 2016/2/19.
 */
public class PtrRecyclerScrollListener extends RecyclerView.OnScrollListener {
    OnScrollCallback onScrollCallback;
    public PtrRecyclerScrollListener(OnScrollCallback onScrollCallback) {
        super();
        this.onScrollCallback = onScrollCallback;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        onScrollCallback.onScrollStateChanged(recyclerView,newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        onScrollCallback.onScrolled(recyclerView,dx,dy);
    }


    public interface OnScrollCallback{
        public abstract void onScrolled(RecyclerView recyclerView, int dx, int dy);
        public abstract void onScrollStateChanged(RecyclerView recyclerView, int newState);
    }
}
