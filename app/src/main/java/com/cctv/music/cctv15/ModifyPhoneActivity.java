package com.cctv.music.cctv15;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;


import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.PhoneCodeRequest;
import com.cctv.music.cctv15.network.UpdateClientUserInfoRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.RegexUtils;
import com.cctv.music.cctv15.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class ModifyPhoneActivity extends BaseActivity implements OnClickListener {

	private EditText phoneEditText;

	private TextView sendBtn;

	private EditText verifyEditText;

	private String verifyCode;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_modify_phone);
		findViewById(R.id.back).setOnClickListener(this);
		phoneEditText = (EditText) findViewById(R.id.phone);
		sendBtn = (TextView) findViewById(R.id.send);
		sendBtn.setOnClickListener(this);
		verifyEditText = (EditText)findViewById(R.id.verify);
		findViewById(R.id.submit).setOnClickListener(this);
	}
	
	private void sendVerify() {
		String phone = phoneEditText.getText().toString();

		
		if (TextUtils.isEmpty(phone)) {
			Utils.tip(this, "手机号不能为空");
			return;
		}
		if (!RegexUtils.checkPhone(phone)) {
			Utils.tip(this, "手机号格式错误");
			return;
		}
		
		

		PhoneCodeRequest.Params params = new PhoneCodeRequest.Params(phone, 2);
		PhoneCodeRequest request = new PhoneCodeRequest(this,params);
		request.request(new BaseClient.RequestHandler() {

			@Override
			public void onSuccess(Object object) {
				PhoneCodeRequest.Result result = (PhoneCodeRequest.Result) object;
				startTimer();
				verifyCode = result.getCode();
				
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(int error, String msg) {
				switch (error) {
				case 1015:
					Utils.tip(ModifyPhoneActivity.this, "手机已经注册过了");
					break;
				default:
					Utils.tip(ModifyPhoneActivity.this, "发送验证码失败");
					break;
				}
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.send:
			sendVerify();
			break;
		case R.id.submit:
			onsubmit();
			break;
		default:
			break;
		}
		
	}
	
	private void onsubmit() {
		String verify = verifyEditText.getText().toString();
		if(TextUtils.isEmpty(verify)){
			Utils.tip(this, "请输入验证码");
			return;
		}
		if(!TextUtils.equals(verify, verifyCode)){
			Utils.tip(this, "验证码输入有误");
			return;
		}
		
		final String phone = phoneEditText.getText().toString();
		if(TextUtils.isEmpty(phone)){
			Utils.tip(this, "请输入手机号");
			return;
		}
		if (!RegexUtils.checkPhone(phone)) {
			Utils.tip(this, "手机号格式错误");
			return;
		}

		UpdateClientUserInfoRequest.Params params =  new UpdateClientUserInfoRequest.Params(Preferences.getInstance().getUid(), Preferences.getInstance().getPkey());

		params.setUsername(phone);

		UpdateClientUserInfoRequest request = new UpdateClientUserInfoRequest(this,params);

        LoadingPopup.show(context);

		request.request(new BaseClient.SimpleRequestHandler(){

            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
			public void onError(int error, String msg) {
				Utils.tip(ModifyPhoneActivity.this, "修改失败");
			}
			
			@Override
			public void onSuccess(Object object) {
                Utils.tip(context,"修改成功");
				Intent intent = new Intent();
				intent.putExtra("phone", phone);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
		
		
	}

	private void startTimer() {
		Timer t = new Timer();
		t.schedule(new SendTimer(60), 0, 1000);
	}

	private class SendTimer extends TimerTask {

		private int second;

		public SendTimer(int second) {
			super();
			this.second = second;
			sendBtn.setEnabled(false);
		}

		@Override
		public void run() {
			Activity activity = ModifyPhoneActivity.this;
			if (activity != null) {
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						second--;
						sendBtn.setText("发送验证码(" + second + ")");
						if (second == 0) {
							sendBtn.setText("发送验证码");
							sendBtn.setEnabled(true);
							cancel();
						}

					}
				});
			}

		}

	}

	
	

}