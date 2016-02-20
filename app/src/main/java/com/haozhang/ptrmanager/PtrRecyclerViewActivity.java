package com.haozhang.ptrmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haozhang.ptr.libary.PtrManager;
import com.haozhang.ptr.libary.base.PtrListeners;
import com.haozhang.ptr.libary.manager.PtrRecyclerViewManager;

import java.util.ArrayList;
import java.util.List;

public class PtrRecyclerViewActivity extends AppCompatActivity {
        private static final String TAG = "PtrRecyclerViewActivity";
    RecyclerView recyclerView;
    private List<Integer> list = new ArrayList<Integer>();
    int i = 0;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_recycler_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        for (; i < 40; i++) {
            list.add(i);
        }


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new PtrManager<PtrRecyclerViewManager>().bind(recyclerView).setOnLoadMoreListener(new PtrListeners.OnLoadMoreListener() {
            @Override
            public void onLoadMorePrepared() {
                Log.d(TAG, "onLoadMorePrepared() called with: ");

            }

            @Override
            public void onLoadMoreBackground() {
                Log.d(TAG, "onLoadMoreBackground() called with: ");
                try {
                    Thread.sleep(5000);
                    int size = i + 10;
                    for (; i < size; i++) {
                        list.add(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadMoreCompleted() {
                Log.d(TAG, "onLoadMoreCompleted() called with: ");
                adapter.notifyDataSetChanged();
            }
        });
       /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "onScrollStateChanged() called with: " + "recyclerView = [" + recyclerView + "], newState = [" + newState + "]");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "onScrolled() called with: " + "recyclerView = [" + recyclerView + "], dx = [" + dx + "], dy = [" + dy + "]");

                Log.d(TAG, "last position = " + getLastVisiblePosition(recyclerView));
            }
        });*/



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

    public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(PtrRecyclerViewActivity.this, R.layout.item_ptrlistview_demo, null);

            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(String.valueOf(list.get(position)));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.test);

        }

    }
}
