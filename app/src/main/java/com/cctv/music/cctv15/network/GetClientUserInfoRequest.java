package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.model.ClientUser;
import com.cctv.music.cctv15.utils.Preferences;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class GetClientUserInfoRequest extends BaseClient{

    public static class Params{
        private String userid;

        public Params(String userid) {
            this.userid = userid;
        }
    }

    public static class Result{
        private ClientUser model;

        public ClientUser getModel() {
            return model;
        }
    }

    private Params params;

    public GetClientUserInfoRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","getClientUserInfo");
        params.add("userid",""+this.params.userid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/getClientUserInfo";
    }

    @Override
    protected Method getMethod() {
        return Method.GET;
    }

    @Override
    public Object onSuccess(String str) {
        Result result = new Gson().fromJson(str,Result.class);
        Preferences.getInstance().login(result.getModel().getUserid(),result.getModel().getPkey());
        return result;
    }

    @Override
    public void onError(int error, String msg) {

    }
}
