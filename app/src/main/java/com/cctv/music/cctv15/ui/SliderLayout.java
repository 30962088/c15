package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SliderLayout extends FrameLayout {


	public SliderLayout(Context context) {
		super(context);
	}

	public SliderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public SliderLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

	
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int originalWidth = MeasureSpec.getSize(widthMeasureSpec);


		int calculatedHeight = originalWidth /718*392;

		

		super.onMeasure(
				MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY));
	}
}