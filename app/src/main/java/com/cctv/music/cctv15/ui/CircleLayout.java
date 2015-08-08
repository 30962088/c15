package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.cctv.music.cctv15.R;


public class CircleLayout extends View{

    private final static int TOTAL_DEGREE = 360;
    private int degree = -120;




    private int iconSize;
    private int mOuterRadius;
    private int[] d1 = new int[]{R.drawable.d1_n,R.drawable.d2_n,R.drawable.d3_n,R.drawable.d4_n,R.drawable.d5_n,R.drawable.d6_n};
    private int[] d2 = new int[]{R.drawable.d1_s,R.drawable.d2_s,R.drawable.d3_s,R.drawable.d4_s,R.drawable.d5_s,R.drawable.d6_s};
    private int mItemCount = d1.length;
    private Bitmap[] bm1 = new Bitmap[d1.length];
    private Bitmap[] bm2 = new Bitmap[d2.length];

    private int mSweepAngle=TOTAL_DEGREE / mItemCount;

    public CircleLayout(Context context) {
        super(context);
        init();
    }

    public CircleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        init();
    }

    public CircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleLayout1, 0, 0);
        try {
            iconSize = (int) a.getDimension(R.styleable.CircleLayout1_iconSize1,80f);
        }finally {
            a.recycle();
        }
        init();
    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    private int index = 0;

    private int lastDegree;

    private CircleAnimation animation;

    public void setDegree(boolean raise,int during){

        if(animation != null){
            animation.cancel();
            setDegree(lastDegree);
        }
        int degree;
        if(raise){
            index--;
            if(index < 0){
                index = mItemCount-1;
            }

            degree = mSweepAngle;
        }else{
            index++;
            if(index == mItemCount){
                index = 0;
            }
            degree = -mSweepAngle;
        }

        lastDegree = this.degree+degree;

        animation = new CircleAnimation(this,this.degree,lastDegree);

        animation.setDuration(during);

        animation.setFillAfter(true);

        animation.setFillEnabled(true);

        animation.setFillBefore(true);

        setAnimation(animation);

        animation.start();


    }

    private void init(){

        for(int i = 0;i<d1.length;i++){

            bm1[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), d1[i]), iconSize, iconSize, true);
//            bm1[i] = BitmapFactory.decodeResource(getResources(),d1[i]);
        }

        for(int i = 0;i<d2.length;i++){
            bm2[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),d2[i]),iconSize,iconSize,true);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mOuterRadius = getHeight()/2;

        int width = getWidth();
        int height = getHeight();



        for (int i = 0; i < mItemCount; i++) {

            Bitmap bitmap;

            if(i == index){
                bitmap = bm2[i];
            }else{
                bitmap = bm1[i];
            }

            int startAngle = degree + i * mSweepAngle;

            int centerX = (int) ((mOuterRadius + bitmap.getWidth()) / 2 * Math.cos(Math.toRadians(startAngle + mSweepAngle / 2)));
            int centerY = (int) ((mOuterRadius + bitmap.getHeight()) / 2 * Math.sin(Math.toRadians(startAngle + mSweepAngle / 2)));


            canvas.drawBitmap(bitmap, width / 2 + centerX - bitmap.getWidth() / 2, height / 2 + centerY - bitmap.getHeight() / 2, null);


        }

        super.onDraw(canvas);
    }

}
