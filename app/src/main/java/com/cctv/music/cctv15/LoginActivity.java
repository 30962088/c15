package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cctv.music.cctv15.fragment.FillUserFragment;
import com.cctv.music.cctv15.model.UserType;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.IsHaveUserNameRequest;
import com.cctv.music.cctv15.utils.OauthUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener,OauthUtils.OauthCallback{

    public static void open(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);

        context.startActivity(intent);

    }

    private OauthUtils oauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oauth = new OauthUtils(this);
        oauth.setOauthCallback(this);
        setContentView(R.layout.activity_login);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.btn_weibo).setOnClickListener(this);
        findViewById(R.id.btn_renren).setOnClickListener(this);
        findViewById(R.id.btn_qq).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
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
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                IsHaveUserNameRequest.Result r1 = (IsHaveUserNameRequest.Result)object;
                if(!r1.exsits()){
                    FillUserFragment.Model model = new FillUserFragment.Model(finalUserType,sid,result.getAvatar(),null,result.getSex(),result.getNickname());
                    FillUserActivity.open(LoginActivity.this,model);
                }
            }

            @Override
            public void onError(int error, String msg) {

            }
        });

    }
}
