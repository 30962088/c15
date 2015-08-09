package com.cctv.music.cctv15.network;

import android.content.Context;

import com.loopj.android.http.RequestParams;


public class UpdateSongScoreRequest extends BaseClient {

    public static class Params{
        private int score;
        private int songid;
        private String userid;

        public Params(int score, int songid, String userid) {
            this.score = score;
            this.songid = songid;
            this.userid = userid;
        }
    }

    private Params params;

    public UpdateSongScoreRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("score",""+this.params.score);
        params.add("songid",""+this.params.songid);
        params.add("userid",""+this.params.userid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/UpdateSongScore";
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
