package com.haozhang.ptr.libary.base;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

/**
 * Listener 集合
 *
 * @author HaoZhang
 * @date 2016/2/16.
 */
public class PtrListeners {

    public interface OnScrollListener {
        /**
         * 返回true代表自己拓展ScrollListener,ptrlistview 不处理scrolllistener，也就不处理loadmore
         *
         * @return
         */
        public boolean onDispatchScorllListener();

        public void onScrollStateChanged(AbsListView view, int scrollState);

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);

    }

    public interface OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    public interface OnLoadMoreListener {

        public void onLoadMorePrepared();

        /**
         * 处理加载事件，在子线程
         */
        public void onLoadMoreBackground();

        /**
         * 加载完毕，在主线程。注意adapter刷新。
         */
        public void onLoadMoreCompleted();
    }

    public interface OnFootClickListener {
        public void onFootClickListener(View foot);
    }
}
