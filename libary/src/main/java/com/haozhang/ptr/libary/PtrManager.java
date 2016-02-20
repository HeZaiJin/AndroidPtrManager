package com.haozhang.ptr.libary;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ListView;

import com.haozhang.ptr.libary.base.PtrBaseManager;
import com.haozhang.ptr.libary.manager.PtrListViewManager;
import com.haozhang.ptr.libary.manager.PtrRecyclerViewManager;

/**
 * @author HaoZhang
 * @date 2016/2/17.
 */
public class PtrManager<T extends PtrBaseManager>{

    public  T bind(ViewGroup group){
        if (group instanceof ListView){
            return (T)new PtrListViewManager((ListView)group);
        }else if (group instanceof RecyclerView){
            return (T)new PtrRecyclerViewManager((RecyclerView)group);
        }
        throw new RuntimeException("only support listview and recyclerview");
    }
}
