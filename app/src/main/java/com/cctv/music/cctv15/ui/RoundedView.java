package com.cctv.music.cctv15.ui;

import android.content.Context;

import android.util.AttributeSet;

import com.makeramen.roundedimageview.RoundedImageView;


public class RoundedView extends RoundedImageView {

	
	
	public RoundedView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RoundedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RoundedView(Context context) {
		super(context);
		init();
	}
	
	

	private void init() {
		setScaleType(ScaleType.FIT_XY);
		setOval(true);
	}
	
	
}
