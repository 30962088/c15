package com.cctv.music.cctv15.network;

import android.content.Context;

import com.loopj.android.http.RequestParams;


public class ChangeUserScoreRequest extends BaseClient {

    public static class Params{
        private int score;
        private String userid;

        public Params(int score, String userid) {
            this.score = score;
            this.userid = userid;
        }
    }

    private Params params;

    public ChangeUserScoreRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("score",""+this.params.score);
        params.add("userid",""+this.params.userid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/ChangeUser_Score";
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
