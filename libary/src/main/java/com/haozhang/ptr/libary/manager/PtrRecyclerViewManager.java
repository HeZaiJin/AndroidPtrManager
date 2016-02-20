package com.haozhang.ptr.libary.manager;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AbsListView;

import com.haozhang.ptr.libary.PtrFootView;
import com.haozhang.ptr.libary.base.IPtrBaseFooter;
import com.haozhang.ptr.libary.base.PtrBaseManager;
import com.haozhang.ptr.libary.listener.PtrRecyclerScrollListener;

/**
 * @author HaoZhang
 * @date 2016/2/17.
 */
public class PtrRecyclerViewManager extends PtrBaseManager<PtrRecyclerViewManager> implements PtrRecyclerScrollListener.OnScrollCallback{

    private RecyclerView recyclerView;

    private IPtrBaseFooter footer;

    private PtrRecyclerScrollListener ptrRecyclerScrollListener;

    public PtrRecyclerViewManager(RecyclerView recyclerView){
        initFooterView();

        this.recyclerView = recyclerView;
        ptrRecyclerScrollListener = new PtrRecyclerScrollListener(this);
        this.recyclerView.addOnScrollListener(ptrRecyclerScrollListener);
    }

    private void initFooterView(){
        footer = new PtrFootView(recyclerView.getContext());
        footer.onGetContentView().setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }


    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition(RecyclerView view) {
        int position;
        if (view.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) view.getLayoutManager()).findLastVisibleItemPosition();
        } else if (view.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) view.getLayoutManager()).findLastVisibleItemPosition();
        } else if (view.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) view.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = view.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (mStatus == STATUS_PREPARED){
            int count = recyclerView.getAdapter().getItemCount();
            int lastVisiblePosition = getLastVisiblePosition(recyclerView);
            if (count == (lastVisiblePosition + 1)){

            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    public PtrRecyclerViewManager setAdapter(){
        return this;
    }


    @Override
    public PtrRecyclerViewManager setHeaderView(View view) {
        return this;
    }

    @Override
    public PtrRecyclerViewManager setFooterView(IPtrBaseFooter view) {
        return this;
    }

    @Override
    protected PtrRecyclerViewManager onLoadMorePrepare() {
        return this;
    }

    @Override
    protected PtrRecyclerViewManager onLoadMoreBackground() {
        return this;
    }

    @Override
    protected PtrRecyclerViewManager onLoadMoreCompleted() {
        return this;
    }

    @Override
    public PtrFootView getMeterialFooterView() {
        if (footer instanceof PtrFootView){
            return (PtrFootView) footer;
        }else {
            throw new RuntimeException("You have been setting your own footer");
        }
    }
}
