package com.cctv.music.cctv15.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Content;

public class HdCommentView extends FrameLayout{

    private class ViewHolder{
        private NewsTitleView titleview;

        public ViewHolder() {
            titleview = (NewsTitleView) findViewById(R.id.titleview);

        }
    }

    public HdCommentView(Context context) {
        super(context);
        init();
    }

    public HdCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HdCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ViewHolder holder;

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.hd_comment, this);
        holder = new ViewHolder();
    }

    public void setModel(Content content){
        holder.titleview.setModel(content);
    }


}
