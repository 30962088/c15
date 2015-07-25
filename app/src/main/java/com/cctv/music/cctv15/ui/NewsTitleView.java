package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.utils.DateUtils;

public class NewsTitleView extends FrameLayout {
    public NewsTitleView(Context context) {
        super(context);
        init();
    }


    public NewsTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewsTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private class ViewHolder{
        private TextView title;
        private TextView date;

        public ViewHolder() {
            title = (TextView) findViewById(R.id.title);
            date = (TextView) findViewById(R.id.date);
        }
    }

    private ViewHolder hoder;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.com_news_title, this);
        hoder = new ViewHolder();
    }

    public void setModel(Content content){
        hoder.date.setText(DateUtils.format(content.getContentsdate()));
        hoder.title.setText(""+content.getContentstitle());
    }

}
