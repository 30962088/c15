package com.cctv.music.cctv15;

import java.io.File;

import com.cctv.music.cctv15.ui.SharePopup;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebViewActivity extends BaseActivity implements OnClickListener{

	public static final String PARAM_URL = "url";
	
	public static final String PARAM_TITLE = "title";
	
	public static final String PARAM_IMG = "img";
	
	private WebView webView;
	
	private TextView titleView;
	
	private String title;
	
	private String img;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		title = getIntent().getStringExtra(PARAM_TITLE);
		img = getIntent().getStringExtra(PARAM_IMG);
		
		setContentView(R.layout.webview_layout);
		/*View share = findViewById(R.id.share);
		share.setOnClickListener(this);
		if(img != null){
			share.setVisibility(View.VISIBLE);
			
		}else{
			share.setVisibility(View.GONE);
		}*/
		titleView = (TextView) findViewById(R.id.title);
		if(title != null){
			titleView.setText(title);
		}
		webView = (WebView) findViewById(R.id.webview);
		final View loading = findViewById(R.id.loading);
		webView.getSettings().setJavaScriptEnabled(true);
		if(img != null){
			webView.getSettings().setLoadWithOverviewMode(true);
			webView.getSettings().setUseWideViewPort(true);
		}
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.loadUrl(getIntent().getStringExtra(PARAM_URL));
		webView.setWebViewClient(new WebViewClient() {
	        @Override
	        public void onPageFinished(WebView view, String url) {
	        	loading.setVisibility(View.GONE);
	        	if(title == null){
	        		titleView.setText(view.getTitle());
	        	}
	        	
	        }
	        @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	        	loading.setVisibility(View.VISIBLE);
	        	super.onPageStarted(view, url, favicon);
	        }
	    });
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	
	
	
	public static void open(Context context,String title,String url,String img){
		Intent intent = new Intent(context, WebViewActivity.class);
		intent.putExtra(PARAM_TITLE, title);
		intent.putExtra(WebViewActivity.PARAM_URL,url);
		intent.putExtra(WebViewActivity.PARAM_IMG,img);
		context.startActivity(intent);
	}
	
	public static void open(Context context,String url){
		open(context, null, url,null);
	}
	
	public static void open(Context context,String title,String url){
		open(context, title, url,null);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		webView.loadUrl("about:blank");
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(webView.canGoBack()){
				webView.goBack();
			}else{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share:
			onshare();
			break;

		default:
			break;
		}
		
	}



	private void onshare() {
		String url = webView.getUrl();
		File bitmapFile = ImageLoader.getInstance().getDiskCache().get(img);
		SharePopup.shareWebsite(context,title,url,bitmapFile);
		
	}

	

	
}
