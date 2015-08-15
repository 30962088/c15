package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cctv.music.cctv15.fragment.FillUserFragment;
import com.cctv.music.cctv15.model.UserResult;
import com.cctv.music.cctv15.model.UserType;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.IsHaveUserNameRequest;
import com.cctv.music.cctv15.network.UserloginVerifyRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.OauthUtils;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.RegexUtils;
import com.cctv.music.cctv15.utils.Utils;

public class LoginActivity extends BaseActivity implements View.OnClickListener,OauthUtils.OauthCallback{

    public static void open(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);

        context.startActivity(intent);

    }

    private OauthUtils oauth;

    private class ViewHolder{
        private TextView phone;
        private TextView password;

        public ViewHolder() {
            phone = (TextView) findViewById(R.id.phone);
            password = (TextView) findViewById(R.id.password);

        }
    }

    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oauth = new OauthUtils(this);
        oauth.setOauthCallback(this);
        setContentView(R.layout.activity_login);
        holder = new ViewHolder();
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.btn_weibo).setOnClickListener(this);
        findViewById(R.id.btn_renren).setOnClickListener(this);
        findViewById(R.id.btn_qq).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                onlogin();
                break;
            case R.id.register:
                RegisterActivity.open(this);
                break;
            case R.id.btn_weibo:
                oauth.sinaOauth();
                break;
            case R.id.btn_qq:
                oauth.tencentOauth();
                break;
            case R.id.btn_renren:
                oauth.renrenOauth();
                break;

        }
    }

    private void onlogin() {
        String phone = holder.phone.getText().toString();
        String password = holder.password.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Utils.tip(this, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Utils.tip(this, "请输入密码");
            return;
        }
        if (!RegexUtils.checkPhone(phone)) {
            Utils.tip(this, "手机号码格式错误");
            return;
        }
        final UserloginVerifyRequest request = new UserloginVerifyRequest(this,new UserloginVerifyRequest.Params(phone,password));

        LoadingPopup.show(this);

        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(LoginActivity.this);
            }

            @Override
            public void onSuccess(Object object) {
                UserResult result = (UserloginVerifyRequest.Result)object;
                Preferences.getInstance().login(""+result.getUserid(),result.getPkey());
                AccountActivity.open(LoginActivity.this);
                finish();
            }

            @Override
            public void onError(int error, String msg) {
                Utils.tip(LoginActivity.this, "用户名或密码错误");
            }
        });

    }

    @Override
    public void onSuccess(final OauthUtils.Result result) {
        int userType = 0;
        switch (result.getMedia()){
            case SINA:
                userType = UserType.USERTYPE_SINA;
                break;
            case TENCENT:
                userType = UserType.USERTYPE_QQ;
                break;
            case RENREN:
                userType = UserType.USERTYPE_RENREN;
                break;
        }
        final String sid = result.getSid();

        final IsHaveUserNameRequest request = new IsHaveUserNameRequest(this,new IsHaveUserNameRequest.Params(sid,userType));

        final int finalUserType = userType;
        LoadingPopup.show(this);
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(LoginActivity.this);
            }

            @Override
            public void onSuccess(Object object) {

                IsHaveUserNameRequest.Result r1 = (IsHaveUserNameRequest.Result)object;
                if(!r1.exsits()){
                    FillUserFragment.Model model = new FillUserFragment.Model(finalUserType,sid,result.getAvatar(),null,result.getSex(),result.getNickname(),null);
                    FillUserActivity.open(LoginActivity.this,model);
                }else{
                    Preferences.getInstance().login(""+r1.getUserid(),r1.getPkey());
                    AccountActivity.open(LoginActivity.this);
                    finish();
                }
            }

            @Override
            public void onError(int error, String msg) {

            }
        });

    }
}
