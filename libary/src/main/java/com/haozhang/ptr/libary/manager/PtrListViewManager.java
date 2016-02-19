package com.haozhang.ptr.libary.manager;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.haozhang.ptr.libary.PtrFootView;
import com.haozhang.ptr.libary.base.IPtrBaseFooter;
import com.haozhang.ptr.libary.base.PtrBaseManager;
import com.haozhang.ptr.libary.base.PtrListeners;

/**
 * ListView的上拉加载manager，支持自定义上拉加载的footerview
 * @see IPtrBaseFooter
 *
 * @author HaoZhang
 * @date 2016/2/17.
 */
public class PtrListViewManager extends PtrBaseManager<PtrListViewManager> implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener {
    private static final String TAG = "PtrListView";

    private static final int STATUS_PREPARED = 0;
    private static final int STATUS_BACKGROUND = 1;
    private static final int STATUS_COMPLETED = -1;
    private int mStatus = STATUS_PREPARED;

    long lastScrollDate = 0;
    private Thread loadThread;
    private LoadMoreRunnable loadRunnalbe;

    private IPtrBaseFooter footer;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ListView listView;

    public PtrListViewManager(ListView listView) {
        this.listView = listView;
        this.listView.setOnScrollListener(this);
        this.listView.setOnItemClickListener(this);
        initFooterView();
    }

    private void initFooterView(){
        footer = new PtrFootView(listView.getContext());
        footer.onGetContentView().setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mStatus == STATUS_BACKGROUND && (position == listView.getCount() - 1) && null != onFootClickListener) {
            onFootClickListener.onFootClickListener(view);
        } else if (null != onItemClickListener) {
            onItemClickListener.onItemClick(parent, view, position, id);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (null != onScrollListener) {
            onScrollListener.onScrollStateChanged(view, scrollState);
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (null != onScrollListener) {
            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            if (onScrollListener.onDispatchScorllListener()) {
                return;
            }
        }
        if (null == onLoadMoreListener) return;

        if (mStatus == STATUS_PREPARED
                && listView.getChildAt(0) != null
                && listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1
                && listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
            long time = System.currentTimeMillis();
            if ((time - lastScrollDate) > 200) {
                lastScrollDate = time;
                onLoadMorePrepare();
                onLoadMoreBackground();
            }
        }
    }

    @Override
    public PtrListViewManager setHeaderView(View view) {
//        this.listView.addHeaderView(view);
        return this;
    }

    @Override
    public PtrListViewManager setFooterView(IPtrBaseFooter view) {
        footer = view;
        return this;
    }

    @Override
    public PtrFootView getMeterialFooterView() {
        if (footer instanceof PtrFootView){
            return (PtrFootView) footer;
        }else {
            throw new RuntimeException("footer view is not instanceof PtrFootView");
        }
    }

    @Override
    public PtrListViewManager onLoadMorePrepare() {
        mStatus = STATUS_PREPARED;

        if (null!=onLoadMoreListener) {
            onLoadMoreListener.onLoadMorePrepared();
        }

        footer.onLoadMorePrepare();

        listView.addFooterView(footer.onGetContentView());
        return this;
    }

    @Override
    public PtrListViewManager onLoadMoreBackground() {
        mStatus = STATUS_BACKGROUND;

        footer.onLoadMoreBackground();

        terminateThreads();
        loadRunnalbe = new LoadMoreRunnable();
        loadThread = new Thread(loadRunnalbe);
        loadThread.start();
        return this;
    }

    @Override
    public PtrListViewManager onLoadMoreCompleted() {
        mStatus = STATUS_COMPLETED;
        if (null!=onLoadMoreListener) {
            onLoadMoreListener.onLoadMoreCompleted();
        }
        footer.onLoadMoreCompleted();

        listView.removeFooterView(footer.onGetContentView());

        mStatus = STATUS_PREPARED;
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

    public PtrListViewManager setOnScrollListener(PtrListeners.OnScrollListener listener) {
        this.onScrollListener = listener;
        return this;
    }

    public PtrListViewManager setOnLoadMoreListener(PtrListeners.OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
        return this;
    }

    public PtrListViewManager setOnPtrItemClickListener(PtrListeners.OnItemClickListener listener) {
        this.onItemClickListener = listener;
        return this;
    }

    public PtrListViewManager setOnFootClickListener(PtrListeners.OnFootClickListener listener) {
        this.onFootClickListener = listener;
        return this;
    }
}
