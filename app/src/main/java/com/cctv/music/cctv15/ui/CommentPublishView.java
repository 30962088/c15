package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.cctv.music.cctv15.R;

public class CommentPublishView extends FrameLayout implements View.OnClickListener{

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share:
                onshare();
                break;
            case R.id.send:
                onsend();
                break;
        }
    }

    private void onsend() {
        if(onPublishListener != null){
            onPublishListener.onsend(hoder.text.getText().toString());
        }
    }

    private void onshare() {
        if(onPublishListener != null){
            onPublishListener.onshare();
        }

    }

    public static interface OnPublishListener{
        public void onshare();
        public void onsend(String text);
    }

    private OnPublishListener onPublishListener;

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
        hoder.share.setOnClickListener(this);
        hoder.send.setOnClickListener(this);
    }

    public void clear(){
        hoder.text.setText("");
        hoder.text.requestFocus();
    }



    public void setModel(OnPublishListener onPublishListener){
        this.onPublishListener = onPublishListener;
    }

}
