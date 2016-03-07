package com.haozhang.ptrmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.haozhang.ptr.libary.PtrFootView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        PtrFootView iv = (PtrFootView) findViewById(R.id.material);
        iv.onLoadMorePrepare();

        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setDrawingCacheBackgroundColor(Color.BLACK);
    }
}
