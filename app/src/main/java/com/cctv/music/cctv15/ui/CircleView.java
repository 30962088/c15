package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;

public class CircleView extends AbsoluteLayout{


    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        setView(child,index);
    }

    private void setView(View child ,int index){

        /*AbsoluteLayout.LayoutParams params = (LayoutParams) child.getLayoutParams();
        params.x = -200;
        params.y = 500;*/

    }

}
