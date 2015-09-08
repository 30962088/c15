package com.cctv.music.cctv15.ui;


import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Comment;
import com.cctv.music.cctv15.utils.DateUtils;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.RelativeDateFormat;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Comment2View extends FrameLayout implements View.OnClickListener{


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_comment:
                onCommentViewListener.onCommentClick(comment);
                break;
            case R.id.btn_jubao:
                onCommentViewListener.onJubaoClick(comment);
                break;
        }
    }

    public static interface OnCommentViewListener{
        public void onCommentClick(Comment comment);
        public void onJubaoClick(Comment comment);
    }

    private OnCommentViewListener onCommentViewListener;

    private class ViewHolder{
        private ImageView avatar;
        private TextView username;
        private TextView date;
        private TextView content;
        public ViewHolder() {
            avatar = (ImageView) findViewById(R.id.avatar);
            username = (TextView) findViewById(R.id.username);
            date = (TextView) findViewById(R.id.date);
            content = (TextView) findViewById(R.id.content);
        }
    }

    public Comment2View(Context context) {
        super(context);
        init();
    }

    public Comment2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Comment2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ViewHolder holder;

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_comment2, this);
        holder = new ViewHolder();
        findViewById(R.id.btn_jubao).setOnClickListener(this);
        findViewById(R.id.btn_comment).setOnClickListener(this);
    }

    private Comment comment;

    public void setModel(OnCommentViewListener onCommentViewListener, Comment comment){

        this.onCommentViewListener = onCommentViewListener;
        this.comment = comment;
        holder.username.setText(""+comment.getUsername());
        ImageLoader.getInstance().displayImage(comment.getUserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
        holder.content.setText("" + Html.fromHtml(comment.getContent()));
        holder.date.setText(""+ RelativeDateFormat.format(comment.getDatetime()));
    }


}
