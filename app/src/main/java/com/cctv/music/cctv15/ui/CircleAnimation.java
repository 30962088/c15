package com.cctv.music.cctv15.ui;

import android.support.v4.view.ViewCompat;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleAnimation extends Animation {

    private CircleLayout circleLayout;

    private int start;

    private int end;

    public CircleAnimation(CircleLayout circleLayout, int start, int end) {
        this.circleLayout = circleLayout;
        this.start = start;
        this.end = end;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float offset = (end - start) * interpolatedTime + start;

        circleLayout.setDegree((int) offset);
    }
}
