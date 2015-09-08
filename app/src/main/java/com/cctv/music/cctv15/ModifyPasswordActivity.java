package com.cctv.music.cctv15;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.UpdateClientUserpassRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;


public class ModifyPasswordActivity extends BaseActivity implements
		OnClickListener {

	private static final long serialVersionUID = 1L;

	private EditText oldPwdText;

	private EditText newPwdText;

	private EditText rePwdText;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_modify_password);
		oldPwdText = (EditText) findViewById(R.id.oldpwd);
		newPwdText = (EditText) findViewById(R.id.newpwd);
		rePwdText = (EditText) findViewById(R.id.repwd);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.submit).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.submit:
			onsubmit();
			break;

		default:
			break;
		}

	}

	private void onsubmit() {
		String oldpwd = oldPwdText.getText().toString();
		final String newpwd = newPwdText.getText().toString();
		String repwd = rePwdText.getText().toString();
		if (TextUtils.isEmpty(oldpwd)) {
			Utils.tip(this, "旧密码不能为空");
			return;
		}
		if (TextUtils.isEmpty(newpwd)) {
			Utils.tip(this, "新密码不能为空");
			return;
		}
		if (TextUtils.isEmpty(repwd)) {
			Utils.tip(this, "确认密码不能为空");
			return;
		}
		if (TextUtils.equals(oldpwd, newpwd)) {
			Utils.tip(this, "新密码不能和旧密码一样");
			return;
		}
		if (!TextUtils.equals(newpwd, repwd)) {
			Utils.tip(this, "新密码和确认密码不一致");
			return;
		}
		UpdateClientUserpassRequest request = new UpdateClientUserpassRequest(
				this, new UpdateClientUserpassRequest.Params(Preferences.getInstance().getUid(), Preferences.getInstance().getPkey(), oldpwd, newpwd));

		LoadingPopup.show(this);
		request.request(new BaseClient.SimpleRequestHandler() {
			@Override
			public void onComplete() {
				LoadingPopup.hide(ModifyPasswordActivity.this);
			}

			@Override
			public void onSuccess(Object object) {
				Utils.tip(ModifyPasswordActivity.this, "修改成功");
				Intent intent = new Intent();
				intent.putExtra("password", newpwd);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}

			@Override
			public void onError(int error, String msg) {
				if (error == 1018) {
					Utils.tip(ModifyPasswordActivity.this, "旧密码错误");
				}
			}

		});

	}

}