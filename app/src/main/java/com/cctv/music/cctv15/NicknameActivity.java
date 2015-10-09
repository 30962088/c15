package com.cctv.music.cctv15;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.IsHaveNickNameRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.Utils;

public class NicknameActivity extends BaseActivity implements OnClickListener {

	private static final long serialVersionUID = 1L;

	private EditText nicknameText;

	private View confirmBtn;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		String nickname = getIntent().getStringExtra("nickname");
		setContentView(R.layout.activity_nickname);
		nicknameText = (EditText) findViewById(R.id.nickname);
		nicknameText.setText(nickname);
		findViewById(R.id.back).setOnClickListener(this);
		confirmBtn = findViewById(R.id.confirm);
		confirmBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.confirm:
			onconfirm();
			break;

		default:
			break;
		}
		
	}

	private void onconfirm() {
		final String nickname = nicknameText.getText().toString();
		if(TextUtils.isEmpty(nickname)){
			Utils.tip(this, "请输入新的昵称");
			return;
		}
		IsHaveNickNameRequest haveSingerName = new IsHaveNickNameRequest(this, new IsHaveNickNameRequest.Params(nickname,1));
		LoadingPopup.show(this);
		haveSingerName.request(new BaseClient.RequestHandler() {
			
			@Override
			public void onSuccess(Object object) {
				Intent intent = new Intent();
				intent.putExtra("nickname", nickname);
				setResult(Activity.RESULT_OK, intent);
				finish();
				
			}
			
			@Override
			public void onError(int error, String msg) {
				Utils.tip(NicknameActivity.this, Utils.getError(error));
				
			}
			
			@Override
			public void onComplete() {
				LoadingPopup.hide(NicknameActivity.this);
				
			}
		});
		

	}

	
	

}