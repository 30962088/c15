package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.model.UserResult;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

///cctv15/isHaveUserName?method=isHaveUserName&username=poolk001&usertype=2
public class IsHaveUserNameRequest extends BaseClient{



    public static class Params{
        private String username;
        private int usertype;

        public Params(String username, int usertype) {
            this.username = username;
            this.usertype = usertype;
        }
    }

    public static class Result extends UserResult{



    }

    private Params params;

    public IsHaveUserNameRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("username",""+this.params.username);
        params.add("usertype",""+this.params.usertype);
        //method=isHaveUserName
        params.add("method","isHaveUserName");
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/isHaveUserName";
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
