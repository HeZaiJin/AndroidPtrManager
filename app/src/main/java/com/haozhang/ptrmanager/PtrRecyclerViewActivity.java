package com.haozhang.ptrmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haozhang.ptr.libary.PtrFootView;
import com.haozhang.ptr.libary.PtrManager;
import com.haozhang.ptr.libary.base.IPtrBaseFooter;
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
    private RecyclerView.LayoutManager layoutManager;
    PtrManager<PtrRecyclerViewManager> manager;

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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        manager = new PtrManager<PtrRecyclerViewManager>();
        adapter = new MyAdapter();
        reset(false);

    }

    public void reset(boolean refresh) {
        list.clear();
        i = 0;
        for (; i < 40; i++) {
            list.add(i);
        }
//        ProgressWheel wheel = (ProgressWheel) getLayoutInflater().inflate(R.layout.activity_test,null).findViewById(R.id.progress);
        PtrFootView footView = new PtrFootView(this);
        ProgressBar bar = new ProgressBar(this);
        FooterProgressBar footer = (FooterProgressBar) getLayoutInflater().inflate(R.layout.progress_bar_footer,null).findViewById(R.id.footerProgress);
//        recyclerView.setLayoutManager(layoutManager);
        manager.bind(recyclerView)
                .setFooterView(footer)
                .setLayoutManager(layoutManager)
                .setAdapter(adapter)
                .setOnLoadMoreListener(new PtrListeners.OnLoadMoreListener() {
                    @Override
                    public void onLoadMorePrepared() {
                        Log.d(TAG, "onLoadMorePrepared() called with: ");

                    }

                    @Override
                    public void onLoadMoreBackground() {
                        Log.d(TAG, "onLoadMoreBackground() called with: ");
                        try {
                            Thread.sleep(5000);
                            int size = i + 5;
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

        if (refresh) {
            adapter.notifyDataSetChanged();
        }

    }

    public class FooterProgressBar extends ProgressBar implements IPtrBaseFooter{

        public FooterProgressBar(Context context) {
            this(context,null);
        }

        public FooterProgressBar(Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }

        public FooterProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public View onGetContentView() {
            return this;
        }

        @Override
        public void onLoadMorePrepare() {

        }

        @Override
        public void onLoadMoreBackground() {

        }

        @Override
        public void onLoadMoreCompleted() {

        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recycler_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        RecyclerView.LayoutManager manager = null;
        switch (id) {
            case R.id.action_linearlayout:
                manager = new LinearLayoutManager(this);
                break;
            case R.id.action_gridlayout:
                manager = new GridLayoutManager(this, 2);
                break;
            case R.id.action_staggered:
                manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        this.layoutManager = manager;
        reset(true);
        return super.onOptionsItemSelected(item);
    }
}
