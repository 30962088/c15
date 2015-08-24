package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.VoteDetailActivity;
import com.cctv.music.cctv15.model.Vote;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class VoteItem2 extends FrameLayout {


    public VoteItem2(Context context) {
        super(context);
        init();
    }

    public VoteItem2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VoteItem2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private class ViewHolder{
        private ImageView img;
        private TextView title;
        private TextView date;

        public ViewHolder() {
            img = (ImageView) findViewById(R.id.img);
            title = (TextView) findViewById(R.id.title);
            date = (TextView) findViewById(R.id.date);
        }
    }

    private ViewHolder holder;

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_vote2, this);
        holder = new ViewHolder();
    }

    public void setVote(final Vote vote){
        holder.date.setText(vote.getDate2());
        holder.title.setText(vote.getVotetitle());
        ImageLoader.getInstance().displayImage(vote.getAttachment().getAttachmentimgurl(), holder.img, DisplayOptions.IMG.getOptions());
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteDetailActivity.open(getContext(),vote);
            }
        });
    }

}
