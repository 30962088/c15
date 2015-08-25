package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.cctv.music.cctv15.R;

public class WHMeasureFrameLayout extends FrameLayout {


	public WHMeasureFrameLayout(Context context) {
		super(context);
	}

	public WHMeasureFrameLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	private float mwidth;

	private float mheight;

	public WHMeasureFrameLayout(Context context, AttributeSet attrs,
							  int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MeasureWH, 0, 0);
		try {
			mwidth =  a.getFloat(R.styleable.MeasureWH_measurew, -1);
			mheight = a.getFloat(R.styleable.MeasureWH_measureh,-1);
		}finally {
			a.recycle();
		}
	
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(mwidth > 0 && mheight>0){
			int originalWidth = MeasureSpec.getSize(widthMeasureSpec);


			int calculatedHeight = (int)(originalWidth /mwidth*mheight);

			super.onMeasure(
					MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY));
		}
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);

	}
}