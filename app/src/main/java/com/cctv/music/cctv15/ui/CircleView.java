package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SizeF;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

public class CircleView extends AbsoluteLayout{


    public CircleView(Context context) {
        super(context);
        init();
    }



    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        final int width = display.getWidth();
        post(new Runnable() {
            @Override
            public void run() {
                int height = getHeight();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.leftMargin = -(height - width) / 2;
                setViews();
            }
        });
    }

    private void setViews() {
        for(int i = 0;i<getChildCount();i++){
            setView(getChildAt(i),i);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }

    private void setView(View child,int index){

        child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

        int w = child.getMeasuredWidth(),h = child.getMeasuredHeight();

        Log.d("zzm","width:"+w+",height:"+h);

        int perA = 360/getChildCount();

        int angle = perA*index;

        PointF point = getPoint(angle-perA);

        LayoutParams params = (LayoutParams) child.getLayoutParams();
        params.x = (int) (point.x-((w/2)*Math.cos(angle * Math.PI/180)));
        params.y = (int) (point.y+((h/2)*Math.sin(angle * Math.PI/180)));

       ViewCompat.setRotation(child, angle);

    }

    private PointF getPoint(double angle){

        int r = getHeight()/2;

        float x = (float)(r+r*Math.cos(angle * Math.PI/180));

        float y = (float)(r+r*Math.sin(angle * Math.PI / 180));

        return new PointF(x,y);



    }


}
