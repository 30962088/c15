package com.cctv.music.cctv15.ui;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.cctv.music.cctv15.utils.Utils;

public class Bg1RelativeLayout extends View{

    public Bg1RelativeLayout(Context context) {
        super(context);
        init();
    }


    public Bg1RelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bg1RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private int type;

    private Paint paint1;

    private Paint paint2;

    private void init() {

        paint1 = new Paint();
        paint1.setColor(Color.parseColor("#7ab4ea"));
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);
        paint2 = new Paint();
        paint2.setColor(Color.parseColor("#509fe3"));
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);
    }

    public void setType(int type) {
        this.type = type;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(type == 1){
            canvas.drawPath(getPath1(), paint1);
        }else{
            canvas.drawPath(getPath2(), paint2);
        }

    }

    private Path path1;

    private Path getPath1(){
        if(path1 == null){
            path1 = new Path();
            path1.setFillType(Path.FillType.EVEN_ODD);
            path1.moveTo(Utils.dpToPx(getContext(),5), 0);
            path1.lineTo(getWidth(), 0);
            path1.lineTo(getWidth(), getHeight());
            path1.lineTo(0, getHeight());
            path1.close();
        }
        return path1;

    }

    private Path path2;

    private Path getPath2(){
        if(path2 == null){
            path2 = new Path();
            path2.setFillType(Path.FillType.EVEN_ODD);
            path2.moveTo(0, 0);
            path2.lineTo(getWidth(), 0);
            path2.lineTo(getWidth(), getHeight());
            path2.lineTo(Utils.dpToPx(getContext(),5), getHeight());
            path2.close();
        }
        return path2;

    }

}
