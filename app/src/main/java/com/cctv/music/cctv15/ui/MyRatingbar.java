package com.cctv.music.cctv15.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cctv.music.cctv15.R;

public class MyRatingbar extends FrameLayout{

    public static interface OnRateListener{
        public void onrate(int rate);
    }

    public MyRatingbar(Context context) {
        super(context);
        init();
    }

    public MyRatingbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRatingbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private OnRateListener onRateListener;

    private ViewGroup container;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.com_ratingbar, this);
        container = ((ViewGroup) findViewById(R.id.container));
        for(int i = 0;i<container.getChildCount();i++){
            View view = container.getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRateListener != null){
                        onRateListener.onrate(finalI+1);
                    }
                }
            });
        }
    }

    public void setRate(int rate){
        for(int i = 0;i<container.getChildCount();i++){
            View view = container.getChildAt(i);
            if(i<=rate-1){
                view.setSelected(true);
            }else{
                view.setSelected(false);
            }
        }
    }

    public void setOnRateListener(OnRateListener onRateListener) {
        this.onRateListener = onRateListener;
    }
}
