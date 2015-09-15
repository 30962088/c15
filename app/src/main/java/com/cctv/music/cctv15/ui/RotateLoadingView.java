package com.cctv.music.cctv15.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class RotateLoadingView extends View{


    public RotateLoadingView(Context context) {
        super(context);
        init();
    }



    public RotateLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotateLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private PausableRotateAnimation lineAnimation;

    private void init() {
        lineAnimation = new PausableRotateAnimation(0f, 358f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        lineAnimation.setInterpolator(new LinearInterpolator());
        lineAnimation.setDuration(2000);
        lineAnimation.setFillAfter(true);
        lineAnimation.setRepeatCount(Animation.INFINITE);
        setAnimation(lineAnimation);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

    }
}
