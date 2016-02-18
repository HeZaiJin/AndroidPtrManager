package com.haozhang.ptr.libary.base;

import android.view.View;

/**
 * 自定义FooterView有2中方式。一种类似PtrFooterView,一种
 *
 * @author HaoZhang
 * @date 2016/2/18.
 */
public interface IPtrBaseFooter {

     abstract View onGetContentView();

     abstract void onLoadMorePrepare();

     abstract void onLoadMoreBackground();

     abstract void onLoadMoreCompleted();

}
