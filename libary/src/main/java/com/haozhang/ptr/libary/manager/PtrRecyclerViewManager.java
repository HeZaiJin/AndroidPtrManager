package com.haozhang.ptr.libary.manager;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AbsListView;

import com.haozhang.ptr.libary.PtrFootView;
import com.haozhang.ptr.libary.base.IPtrBaseFooter;
import com.haozhang.ptr.libary.base.PtrBaseManager;
import com.haozhang.ptr.libary.base.PtrListeners;
import com.haozhang.ptr.libary.listener.PtrRecyclerScrollListener;

/**
 * RecyclerView 加载更多的 manager .
 * 你应该先bind，然后setadapter，然后setLoadMoreListener
 *
 * @author HaoZhang
 * @date 2016/2/17.
 */
public class PtrRecyclerViewManager extends PtrBaseManager<PtrRecyclerViewManager> implements PtrRecyclerScrollListener.OnScrollCallback{

    private RecyclerView recyclerView;

    private IPtrBaseFooter footer;
    private PtrRecyclerViewAdapterManager adapterManager;

    private PtrRecyclerScrollListener ptrRecyclerScrollListener;

    private PtrListeners.OnLoadMoreListener onLoadMoreListener;

    private Thread loadThread;
    private LoadMoreRunnable loadRunnalbe;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public PtrRecyclerViewManager(RecyclerView recyclerView){

        this.recyclerView = recyclerView;
        ptrRecyclerScrollListener = new PtrRecyclerScrollListener(this);
        initFooterView();
        this.recyclerView.addOnScrollListener(ptrRecyclerScrollListener);
    }

    private void initFooterView(){
        footer = new PtrFootView(recyclerView.getContext());
        footer.onGetContentView().setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
    }

    private void initAdapterManager(RecyclerView.Adapter adapter){
        RecyclerView.LayoutManager layoutManager = this.recyclerView.getLayoutManager();
        if (null == adapter){
            throw new RuntimeException("The recyclerView's adapter is not set");
        }
        if (null == layoutManager){
            throw new RuntimeException("The recyclerView's layoutManager is not set");
        }
        adapterManager = new PtrRecyclerViewAdapterManager(adapter,layoutManager);
        this.recyclerView.setAdapter(adapterManager.getAdapter());
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
                onLoadMorePrepare();
                onLoadMoreBackground();
            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    public PtrRecyclerViewManager setAdapter(RecyclerView.Adapter adapter){
        initAdapterManager(adapter);
        return this;
    }

    @Override
    public PtrRecyclerViewManager setHeaderView(View view) {
        return this;
    }

    @Override
    public PtrRecyclerViewManager setFooterView(IPtrBaseFooter view) {
        this.footer = view ;
        return this;
    }

    public PtrRecyclerViewManager setOnLoadMoreListener(PtrListeners.OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
        return this;
    }


    @Override
    protected PtrRecyclerViewManager onLoadMorePrepare() {
        mStatus = STATUS_PREPARED;

        if (null!=onLoadMoreListener) {
            onLoadMoreListener.onLoadMorePrepared();
        }

        footer.onLoadMorePrepare();

        adapterManager.addFooterView(footer.onGetContentView());

        return this;
    }

    @Override
    protected PtrRecyclerViewManager onLoadMoreBackground() {
        mStatus = STATUS_BACKGROUND;

        footer.onLoadMoreBackground();

        terminateThreads();
        loadRunnalbe = new LoadMoreRunnable();
        loadThread = new Thread(loadRunnalbe);
        loadThread.start();
        return this;
    }

    private class LoadMoreRunnable implements Runnable {
        boolean runs = true;

        void terminate() {
            runs = false;
        }

        @Override
        public void run() {
            if (null!=onLoadMoreListener){
                onLoadMoreListener.onLoadMoreBackground();
            }

            if (runs) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onLoadMoreCompleted();
                    }
                });
            }
        }
    }
    private void terminateThreads() {

        if (loadThread != null) {
            loadRunnalbe.terminate();
            // No need to join, since we don't really terminate the thread. We just demand
            // it to post its result runnable into the gui main loop.
        }
    }

    @Override
    protected PtrRecyclerViewManager onLoadMoreCompleted() {
        mStatus = STATUS_COMPLETED;
        if (null!=onLoadMoreListener) {
            onLoadMoreListener.onLoadMoreCompleted();
        }
        footer.onLoadMoreCompleted();

        adapterManager.removeFooterView(footer.onGetContentView());

        mStatus = STATUS_PREPARED;

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
