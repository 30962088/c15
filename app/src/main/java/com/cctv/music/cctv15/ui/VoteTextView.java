package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class VoteTextView extends TextView{

    private int mStrokeColor = Color.parseColor("#4c4ff3");
    private int mStrokeWidth = 3;
    private TextPaint mStrokePaint;

    public VoteTextView(Context context) {
        super(context);
    }

    public VoteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VoteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // lazy load
        if (mStrokePaint == null) {
            mStrokePaint = new TextPaint();
        }

        // copy
        mStrokePaint.setTextSize(getTextSize());
        mStrokePaint.setTypeface(getTypeface());
        mStrokePaint.setFlags(getPaintFlags());

        // custom
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(mStrokeColor);
        mStrokePaint.setStrokeWidth(mStrokeWidth);

        String text = getText().toString();
        canvas.drawText(text, (getWidth() - mStrokePaint.measureText(text)) / 2, getBaseline(), mStrokePaint);
        super.onDraw(canvas);
    }
}
