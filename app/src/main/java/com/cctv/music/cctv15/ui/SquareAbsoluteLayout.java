package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

public class SquareAbsoluteLayout extends AbsoluteLayout{

	public SquareAbsoluteLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SquareAbsoluteLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SquareAbsoluteLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(heightMeasureSpec, heightMeasureSpec);
	}
	
}
