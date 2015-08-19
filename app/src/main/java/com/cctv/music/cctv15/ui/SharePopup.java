package com.cctv.music.cctv15.ui;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.adapter.ShareAdapter;
import com.cctv.music.cctv15.utils.ShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
public class SharePopup implements OnClickListener{





	public static void shareWebsite(final Context context, final String title, final String url, final File bitmapFile){
		_share(context, new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ShareAdapter.Model model = (ShareAdapter.Model) parent.getAdapter().getItem(position);
				ShareUtils.shareWebsite(context,model.getShare_media(),title,url,bitmapFile);
			}
		});
	}

	private static void _share(Context context,OnItemClickListener onItemClickListener){
		new SharePopup(context, new ArrayList<ShareAdapter.Model>(){{
			add(new ShareAdapter.Model(R.drawable.icon_wechat, "微信好友",SHARE_MEDIA.WEIXIN));
			add(new ShareAdapter.Model(R.drawable.icon_timeline, "朋友圈",SHARE_MEDIA.WEIXIN_CIRCLE));
			add(new ShareAdapter.Model(R.drawable.icon_weibo, "新浪微博",SHARE_MEDIA.SINA));
			add(new ShareAdapter.Model(R.drawable.icon_qzone, "QQ空间",SHARE_MEDIA.QZONE));
		}}, onItemClickListener);
	}

	private PopupWindow mPopupWindow;
	
	
	public SharePopup(Context context,List<ShareAdapter.Model> list,final OnItemClickListener onItemClickListener) {
		View view = LayoutInflater.from(context).inflate(R.layout.popup_share,null);
		final GridView gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setAdapter(new ShareAdapter(context, list));
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onItemClickListener.onItemClick(parent, gridView, position, id);
				mPopupWindow.dismiss();
			}
		});
		view.setOnClickListener(this);
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4D000000")));
		mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
       
        Animation rotation = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_bottom);
        rotation.setDuration(500);
        View bottomBar = view.findViewById(R.id.popup);
        bottomBar.setOnClickListener(this);
        bottomBar.startAnimation(rotation);
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
       
        view.findViewById(R.id.close).setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.popupWindow:
			mPopupWindow.dismiss();
			break;
		case R.id.close:
			mPopupWindow.dismiss();
			break;
		default:
			break;
		}
		
	}
	
	
	
	

	
}
