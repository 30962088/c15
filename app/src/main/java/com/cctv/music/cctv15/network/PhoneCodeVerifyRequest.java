package com.cctv.music.cctv15.network;

import android.content.Context;

import com.loopj.android.http.RequestParams;

public class PhoneCodeVerifyRequest extends BaseClient{

    public static class Params{
        private String code;

        public Params(String code) {
            this.code = code;
        }
    }

    private Params params;

    public PhoneCodeVerifyRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("code",this.params.code);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/PhoneCodeVerify";
    }

    @Override
    protected Method getMethod() {
        return Method.GET;
    }

    @Override
    public Object onSuccess(String str) {
        return null;
    }

    @Override
    public void onError(int error, String msg) {

    }
}
