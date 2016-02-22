package com.haozhang.ptr.libary.manager;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.haozhang.ptr.libary.modle.HeaderSapnSizeLookUp;
import com.haozhang.ptr.libary.modle.RefreshRecyclerViewAdapter;

/**
 * @author HaoZhang
 * @date 2016/2/19.
 */
public class PtrRecyclerViewAdapterManager {
    
    private RefreshRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RefreshRecyclerViewAdapter.OnItemClickListener mOnItemClickListener;
    private RefreshRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener;
    private RecyclerView.ItemDecoration mDecor;
    private RecyclerView.ItemAnimator mItemAnimator;

    public PtrRecyclerViewAdapterManager(
            RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        this.mAdapter = new RefreshRecyclerViewAdapter(adapter);

        if (null == layoutManager){
            throw new NullPointerException("Couldn't resolve a null object reference of LayoutManager");
        }
        this.mLayoutManager = layoutManager;
        if (layoutManager instanceof GridLayoutManager){
            //如果是header或footer，设置其充满整列
            ((GridLayoutManager)layoutManager).setSpanSizeLookup(
                    new HeaderSapnSizeLookUp(mAdapter, ((GridLayoutManager) layoutManager).getSpanCount()));
        }
        this.mLayoutManager = layoutManager;

        mAdapter.putLayoutManager(mLayoutManager);
    }



    private PtrRecyclerViewAdapterManager getInstance(){
        return PtrRecyclerViewAdapterManager.this;
    }

    public PtrRecyclerViewAdapterManager addHeaderView(View v){
        mAdapter.addHeaderView(v);
        return getInstance();
    }

    public PtrRecyclerViewAdapterManager addHeaderView(View v, int position){
        mAdapter.addHeaderView(v, position);
        return getInstance();
    }

    public PtrRecyclerViewAdapterManager addFooterView(View v){
        mAdapter.addFooterView(v);
        return getInstance();
    }

    public PtrRecyclerViewAdapterManager removeHeaderView(View v){
        mAdapter.removeHeader(v);
        return getInstance();
    }

    public RefreshRecyclerViewAdapter getAdapter(){
        return mAdapter;
    }


    public PtrRecyclerViewAdapterManager removeFooterView(View v){
        mAdapter.removeFooter(v);
        return getInstance();
    }




    public PtrRecyclerViewAdapterManager setOnItemClickListener
            (RefreshRecyclerViewAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
        return getInstance();
    }

    public PtrRecyclerViewAdapterManager setOnItemLongClickListener(
            RefreshRecyclerViewAdapter.OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
        return getInstance();
    }

    public void onRefreshCompleted(){


    }


    public PtrRecyclerViewAdapterManager setItemAnimator(RecyclerView.ItemAnimator itemAnimator){
        this.mItemAnimator = itemAnimator;
        return getInstance();
    }

    public PtrRecyclerViewAdapterManager addItemDecoration(RecyclerView.ItemDecoration decor){
        this.mDecor = decor;
        return getInstance();
    }



}