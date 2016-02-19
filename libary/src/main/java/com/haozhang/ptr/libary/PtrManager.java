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
public class PtrManager {

    public static final PtrBaseManager bind(ViewGroup group){
        if (group instanceof ListView){

            return new PtrListViewManager((ListView)group);
        }else if (group instanceof RecyclerView){

            return new PtrRecyclerViewManager();
        }
        return null;
    }


}
