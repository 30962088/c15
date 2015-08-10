package com.cctv.music.cctv15.network;

import android.content.Context;

import com.loopj.android.http.RequestParams;

///cctv15/isHaveNickName?method=isHaveNickName&username=%E9%98%B3%E5%85%89%E7%9A%84%E5%B0%8F%E9%83%A8%E7%9A%84&usertype=2
public class IsHaveNickNameRequest extends BaseClient{

    public static class Params{
        private String username;
        private int usertype;

        public Params(String username, int usertype) {
            this.username = username;
            this.usertype = usertype;
        }
    }

    private Params params;

    public IsHaveNickNameRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","isHaveNickName");
        params.add("username",""+this.params.username);
        params.add("usertype",""+this.params.usertype);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/isHaveNickName";
    }

    @Override
    protected Method getMethod() {
        return Method.POST;
    }

    @Override
    public Object onSuccess(String str) {
        return null;
    }

    @Override
    public void onError(int error, String msg) {

    }
}
