package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.model.MyTicket;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;


public class ActivistListRequest extends BaseClient{

    public static class Params{
        private String userid;

        public Params(String userid) {
            if(userid == null){
                userid = "0";
            }
            this.userid = userid;
        }
    }

    public static class Result extends MyTicket{

    }

    private Params params;

    public ActivistListRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("userid",""+this.params.userid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/GetActivistList";
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
