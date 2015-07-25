package com.cctv.music.cctv15.ui;


import android.content.Context;
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
import com.nostra13.universalimageloader.core.ImageLoader;

public class CommentView extends FrameLayout{

    private class ViewHolder{
        private ImageView avatar;
        private TextView username;
        private TextView date;
        private TextView content;
        private View jubao;
        private View sep;

        public ViewHolder() {
            avatar = (ImageView) findViewById(R.id.avatar);
            username = (TextView) findViewById(R.id.username);
            date = (TextView) findViewById(R.id.date);
            content = (TextView) findViewById(R.id.content);
            jubao = findViewById(R.id.jubao);
            sep = findViewById(R.id.sep);


        }
    }

    public CommentView(Context context) {
        super(context);
        init();
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ViewHolder holder;

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_comment, this);
        holder = new ViewHolder();
    }

    public void setModel(Comment comment){
        holder.username.setText(""+comment.getUsername());
        ImageLoader.getInstance().displayImage(comment.getUserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
        holder.content.setText("" + comment.getRemark());
        holder.date.setText(""+ DateUtils.format(comment.getDatetime()));
    }


}
