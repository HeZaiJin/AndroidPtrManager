package com.haozhang.ptrmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 晓勇 on 2015/8/26 0026.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<String> datas;
    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;
    private static final int IS_NORMAL = 1;
    public RecyclerViewAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerViewHolder holder;
        //对不同的flag创建不同的Holder
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_header, viewGroup, false);
            holder = new RecyclerViewHolder(view,IS_HEADER);
            return holder;
        } else if (viewType == IS_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_footer, viewGroup, false);
            holder = new RecyclerViewHolder(view,IS_FOOTER);
            return holder;
        }else if(viewType==IS_NORMAL){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
            holder = new RecyclerViewHolder(view,IS_NORMAL);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder recyclerViewHolder, int position) {
        //对不同的Item相应不同的操作
        if(position!=0&&position!=datas.size()+1&&recyclerViewHolder.viewType==IS_NORMAL){
            recyclerViewHolder.mTextView.setText(datas.get(position - 1));
        }
        if(position==0&&recyclerViewHolder.viewType==IS_HEADER){
            //header
            recyclerViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                int i=0;
                @Override
                public void onClick(View v) {
                    recyclerViewHolder.mButton.setText(++i+"");
                }
            });
        }
        if(position==datas.size()+1&&recyclerViewHolder.viewType==IS_FOOTER){
            //footer
        }

    }

    @Override
    public int getItemCount() {
        return datas.size() + 2;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else if(position==datas.size()+1){
           return IS_FOOTER;
        }else {
            return IS_NORMAL;
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public Button mButton;
        public int viewType;
        public RecyclerViewHolder(View itemView,int viewType) {
            super(itemView);
            this.viewType = viewType;
            if(viewType==IS_HEADER){
                mButton = (Button) itemView.findViewById(R.id.button);
            }
            if(viewType==IS_FOOTER){
                //do some sthing
            }
            if(viewType==IS_NORMAL){
                mTextView = (TextView) itemView.findViewById(R.id.tv_content);
            }
        }
    }
}
