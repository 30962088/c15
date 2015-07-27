package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class ClockView extends View{
    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchstart(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchmove(event);
                break;
            case MotionEvent.ACTION_UP:
                touchend(event);
                break;
        }
        Log.d("zzm", "" + event.getX());
        return super.onTouchEvent(event);
    }

    private float cx;

    private float cy;

    private void touchstart(MotionEvent event) {
        cx = event.getX();
        cy = event.getY();

    }

    private void touchmove(MotionEvent event) {

    }

    private void touchend(MotionEvent event) {

    }

}
