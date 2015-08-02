package com.cctv.music.cctv15.network;


import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class PhoneCodeRequest extends BaseClient{

    public static class Params{
        private String phone;
        private int type=1;
        private int userid=0;

        public Params(String phone) {
            this.phone = phone;
        }


    }

    public static class Result{
        private String code;

        public String getCode() {
            return code;
        }
    }

    private Params params;

    public PhoneCodeRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","getVerifyCode");
        params.add("phone",this.params.phone);
        params.add("type",""+this.params.type);
        params.add("userid",""+this.params.userid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/getVerifyCode2";
    }

    @Override
    protected Method getMethod() {
        return Method.GET;
    }

    @Override
    public Object onSuccess(String str) {
        return new Gson().fromJson(str,Result.class);
    }

    @Override
    public void onError(int error, String msg) {

    }
}
