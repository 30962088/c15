package com.cctv.music.cctv15.ui;



import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.cctv.music.cctv15.R;

public class LoadingPopup {

	

	private static PopupWindow popup;

	public static void show(final Context context) {
		new Handler(context.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				popup = new PopupWindow(LayoutInflater.from(context).inflate(R.layout.loading_popup,
						null),LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
				popup.showAtLocation(popup.getContentView(), Gravity.BOTTOM, 0, 0);
			}

		});
	}

	public static void hide(Context context) {
		if(context != null){
			new Handler(context.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (popup != null) {
						popup.dismiss();
						popup = null;
					}
				}

			});
		}
		
		
	}

}
