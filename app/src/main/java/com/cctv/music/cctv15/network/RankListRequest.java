package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.model.MyRank;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class RankListRequest extends BaseClient{

    public static class Params{
        private String userid;

        public Params(String userid) {
            if(userid == null){
                userid = "0";
            }
            this.userid = userid;
        }
    }

    private Params params;

    public static class Result extends MyRank{

    }

    public RankListRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("userid",this.params.userid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/GetRankList";
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
