package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by zhangzimeng on 15/9/20.
 */
public class MySurfaceView extends SurfaceView {
    public MySurfaceView(Context context) {
        super(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if(mwidth > 0 && mheight>0){
            int originalWidth = MeasureSpec.getSize(widthMeasureSpec);


            int calculatedHeight = (int)(originalWidth /16*9);

            super.onMeasure(
                    MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY));
//        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
