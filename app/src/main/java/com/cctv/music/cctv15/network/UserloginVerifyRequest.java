package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.model.UserResult;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

///cctv15/userloginVerify?method=userloginVerify&phone=18500238470&password=123123
public class UserloginVerifyRequest extends BaseClient{

    public static class Params{
        private String phone;
        private String password;

        public Params(String phone, String password) {
            this.phone = phone;
            this.password = password;
        }
    }

    private Params params;

    public static class Result extends UserResult{

    }

    public UserloginVerifyRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","userloginVerify");
        params.add("phone",this.params.phone);
        params.add("password",this.params.password);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/userloginVerify";
    }

    @Override
    protected Method getMethod() {
        return Method.POST;
    }

    @Override
    public Object onSuccess(String str) {
        return new Gson().fromJson(str,Result.class);
    }

    @Override
    public void onError(int error, String msg) {

    }
}
