package com.haozhang.ptr.libary;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haozhang.ptr.libary.base.IPtrBaseFooter;
import com.haozhang.ptr.libary.design.MaterialProgressDrawable;


/**
 * 底部加载更多的foot view
 * @author HaoZhang
 * @date 2016/2/1.
 */
public class PtrFootView extends LinearLayout implements IPtrBaseFooter {

    private static final int DEFAULT_PROGRESS_COLOR = 0Xff4caf50;
    private static final int DEFAULT_WIDTH = 100;

    private ImageView mRefreshView;

    private MaterialProgressDrawable mRefreshDrawable;

    private int mProgressColor = DEFAULT_PROGRESS_COLOR;
    private int mWidth;

    public PtrFootView(Context context) {
        this(context, null);
    }

    public PtrFootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mWidth = (int) (metrics.density*DEFAULT_WIDTH);
        onInitView(context);
    }

    public void onInitView(Context context){
        this.setGravity(Gravity.CENTER);
        mRefreshView = new ImageView(context);
        mRefreshView.setLayoutParams(new LayoutParams(mWidth ,mWidth));

        mRefreshDrawable = new MaterialProgressDrawable(context,this);
        mRefreshDrawable.setAlpha(255);
        mRefreshDrawable.setColorSchemeColors(mProgressColor);
        mRefreshView.setImageDrawable(mRefreshDrawable);
        this.addView(mRefreshView);
    }

    public void setProgressDrawableColor(int color){
        this.mProgressColor = color;
        mRefreshDrawable.setColorSchemeColors(color);
        postInvalidate();
    }

    public void setRefreshViewWidth(int width){
        mWidth = width;
        mRefreshView.setLayoutParams(new LayoutParams(mWidth, mWidth));
        postInvalidate();
    }

    public void setStrokeWidth(int width){
        // TODO set paint stroke width
    }


    public void stopRefresh(Animation animation,MaterialProgressDrawable.AnimationListener listener){
        mRefreshDrawable.stop(animation,listener);
    }

    public void stopRefresh(){
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
        mRefreshDrawable.start();
    }

    @Override
    public void onLoadMoreCompleted() {
        mRefreshDrawable.stop();
    }
}
