package com.cctv.music.cctv15;



import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.GetWeiboUserRequest;
import com.cctv.music.cctv15.utils.OauthUtils;
import com.cctv.music.cctv15.utils.Preferences;

public class BindingWeiboActivity extends BaseActivity implements OnClickListener,OauthUtils.OauthCallback {
	
	private View bindingBtn;
	
	private TextView nicknameView;
	
	private View unbindingView;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_binding_weibo);
		bindingBtn = findViewById(R.id.binding_btn);
		unbindingView = findViewById(R.id.unbinding_view);
		findViewById(R.id.back).setOnClickListener(this);
		bindingBtn.setOnClickListener(this);
		nicknameView = (TextView) findViewById(R.id.nickname);
		findViewById(R.id.unbinding_btn).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.binding_btn:
			onbinding();
			break;
		case R.id.unbinding_btn:
			onunbinding();
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
		
	}
	private void onunbinding() {
		Preferences.getInstance().clearWeibo();
		onResume();
		
	}
	private void onbinding() {
		OauthUtils oauthUtils = new OauthUtils(this);
		oauthUtils.setOauthCallback(this);
		oauthUtils.sinaOauth();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		String accessToken = Preferences.getInstance().getWeiboAccessToken();
		String uid = Preferences.getInstance().getWeiboUid();
		if(accessToken != null){
			unbindingView.setVisibility(View.VISIBLE);
			bindingBtn.setVisibility(View.GONE);
			GetWeiboUserRequest request = new GetWeiboUserRequest(this,
					new GetWeiboUserRequest.Params(accessToken, uid));
			request.request(new BaseClient.RequestHandler() {
				
				@Override
				public void onSuccess(Object object) {
					GetWeiboUserRequest.Result result = (GetWeiboUserRequest.Result)object;
					nicknameView.setText(result.getScreen_name());
				}
				
				
				@Override
				public void onComplete() {
					// TODO Auto-generated method stub
					
				}


				@Override
				public void onError(int error, String msg) {
					// TODO Auto-generated method stub
					
				}
			});
		}else{
			unbindingView.setVisibility(View.GONE);
			bindingBtn.setVisibility(View.VISIBLE);
		}
		
	}
	
	@Override
	public void onSuccess(OauthUtils.Result params) {
		onResume();
	}
	
	
	
}
