package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Content;

public class CommentPublishView extends FrameLayout {

    public CommentPublishView(Context context) {
        super(context);
        init();
    }


    public CommentPublishView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentPublishView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private class ViewHolder{
        private EditText text;
        private View send;
        private View share;

        public ViewHolder() {
            text = (EditText) findViewById(R.id.text);
            send = findViewById(R.id.send);
            share = findViewById(R.id.share);
        }
    }

    private ViewHolder hoder;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.com_comment_publish, this);
        hoder = new ViewHolder();
    }

    public void setModel(Content content){

    }

}
