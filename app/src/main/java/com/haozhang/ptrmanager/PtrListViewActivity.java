package com.haozhang.ptrmanager;

import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.haozhang.ptr.libary.PtrManager;
import com.haozhang.ptr.libary.base.PtrListeners;
import com.haozhang.ptr.libary.manager.PtrListViewManager;

import java.util.ArrayList;
import java.util.List;

public class PtrListViewActivity extends AppCompatActivity {
    ListView listView;
    private static final String TAG = "PtrListView";

    private List<Integer> list = new ArrayList<Integer>();
    private MyAdapter adapter;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_list_view);
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

        final TextView tv = new TextView(PtrListViewActivity.this);
        //这里的footer的layout可以是new，也可以是xml
        tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(30);
        tv.setPadding(0, 20, 0, 20);
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.ic_heard);
        listView = (ListView) findViewById(R.id.ptrlistview);

        // 如果想多footerview的话，item click需要自己处理position
        listView.addFooterView(iv);

        new PtrManager<PtrListViewManager>()
                .bind(listView).
                setOnLoadMoreListener(new PtrListeners.OnLoadMoreListener() {
                    @Override
                    public void onLoadMorePrepared() {
                        Log.d(TAG, "onLoadMorePrepared() called with: " + " is in mainThread ? =" + (Looper.myLooper() == Looper.getMainLooper()));
                    }

                    @Override
                    public void onLoadMoreBackground() {
                        Log.d(TAG, "onLoadMoreBackground() called with: " + " is in mainThread ? =" + (Looper.myLooper() == Looper.getMainLooper()));
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
                        Log.d(TAG, "onLoadMoreCompleted() called with: " + " is in mainThread ? =" + (Looper.myLooper() == Looper.getMainLooper()));
                        adapter.notifyDataSetChanged();
                    }
                })/*.setFooterView(new IPtrBaseFooter() {
            @Override
            public View onGetContentView() {
                return tv;
            }
            @Override
            public void onLoadMorePrepare() {
                tv.setText("onLoadMorePrepare");
                Log.d(TAG, "tv.setText( onLoadMorePrepare)" );
            }

            @Override
            public void onLoadMoreBackground() {
                tv.setText("onLoadMoreBackground");
                Log.d(TAG, "tv.setText( onLoadMoreBackground)");

            }

            @Override
            public void onLoadMoreCompleted() {
                tv.setText("onLoadMoreCompleted");
                Log.d(TAG, "tv.setText( onLoadMoreCompleted)");
            }
        })*/.setOnFootClickListener(new PtrListeners.OnFootClickListener() {
            @Override
            public void onFootClickListener(View foot) {
                Log.d(TAG, "onFootClickListener");

            }
        }).setOnPtrItemClickListener(new PtrListeners.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick position =" + position);
            }
        }).getMeterialFooterView();


        for (; i < 10; i++) {
            list.add(i);
        }

        adapter = new MyAdapter();

        listView.setAdapter(adapter);


    }

    private class MyAdapter extends BaseAdapter {

        public MyAdapter() {
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = View.inflate(PtrListViewActivity.this, R.layout.item_ptrlistview_demo, null);
            }
            TextView tv = ViewHolder.get(convertView, R.id.test);
            if (null != tv) {
                tv.setText(String.valueOf(list.get(position)));
            }
            return convertView;
        }
    }

}
