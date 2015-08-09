package com.cctv.music.cctv15.network;


import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class GetSongScoreRequest extends BaseClient{

    public static class Result{
        private int Score;

        public int getScore() {
            return Score;
        }
    }

    public static class Params{
        //userid=174&songid=13
        private String userid;
        private int songid;

        public Params(String userid, int songid) {
            this.userid = userid;
            this.songid = songid;
        }
    }

    private Params params;

    public GetSongScoreRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("userid",""+this.params.userid);
        params.add("songid",""+this.params.songid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/GetSongScore";
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
