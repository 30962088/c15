package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.cctv.music.cctv15.utils.Utils;


public class PercentView extends View {

    private int percent = 0;

    public PercentView(Context context) {
        super(context);
        init();
    }

    public PercentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PercentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private Paint bgPaint,fillPaint;
    private int stokeWidth,gap;

    private void init() {

        stokeWidth = Utils.dpToPx(getContext(),5);
        gap = stokeWidth/2;
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(Color.parseColor("#1a000000"));
        bgPaint.setStrokeWidth(stokeWidth);

        fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.STROKE);
//        Shader shader = new LinearGradient(0, 0, stokeWidth, stokeWidth, Color.parseColor("#cc1937eb"), Color.parseColor("#cc3a35f3"), Shader.TileMode.CLAMP);
//        fillPaint.setShader(shader);
        fillPaint.setColor(Color.parseColor("#08bdf2"));
        fillPaint.setStrokeWidth(stokeWidth);

    }

    public void setPercent(int percent) {
        this.percent = percent;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();

        RectF rect = new RectF(0f+gap,0f+gap,(float)w-gap,(float)h-gap);

        canvas.drawCircle(w / 2, h / 2, w/2-stokeWidth/2, bgPaint);

        canvas.drawArc(rect,-90,(float)(3.6*percent),false,fillPaint);

    }
}
