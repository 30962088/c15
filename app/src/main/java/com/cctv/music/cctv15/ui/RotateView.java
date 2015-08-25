package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class RotateView extends View{


    public static enum Direction{
        Up,Down
    }

    public static interface OnRotateListener{
        public void start();
        public void onrotate(double rotate);
        public void end(Direction dir);
    }

    public RotateView(Context context) {
        super(context);
    }

    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnRotateListener onRotateListener;

    public void setOnRotateListener(OnRotateListener onRotateListener) {
        this.onRotateListener = onRotateListener;
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
        return true;
    }


    private double startA;


    private double current = 0;

    private void touchstart(MotionEvent event) {
        last = startA = calcRotationAngleInDegrees(new PointF(event.getX(),event.getY()));
        if(onRotateListener != null){
            onRotateListener.start();
        }
    }

    private double last;

    private Direction direction;

    private void touchmove(MotionEvent event) {
        PointF pointF = new PointF(event.getX(),event.getY());
        double angle = calcRotationAngleInDegrees(pointF);
        if(angle > last){
            direction = Direction.Down;
        }else{
            direction = Direction.Up;
        }
        last = angle;
        if(onRotateListener != null){
            onRotateListener.onrotate(current+angle-startA);
        }
    }

    private void touchend(MotionEvent event) {
        PointF pointF = new PointF(event.getX(),event.getY());
        double endA = calcRotationAngleInDegrees(pointF);

        double deltaA =endA-startA;
        current+=deltaA;
        if(onRotateListener != null && direction != null){
            onRotateListener.end(direction);
        }
    }


    public double calcRotationAngleInDegrees(PointF targetPt){
        PointF centerPt = new PointF(getWidth()/2,getHeight()/2);
//        Log.d("zzm","PointF:"+targetPt.x+","+targetPt.y);
//        Log.d("zzm","center:"+centerPt.x+","+centerPt.y);
        double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);
        theta += Math.PI/2.0;
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

}
