package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.model.TicketDetail;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class TicketDetailRequest extends BaseClient{

    public static class Params{
        private int activityid;

        public Params(int activityid) {
            this.activityid = activityid;
        }
    }

    private Params params;

    public static class Result extends TicketDetail{

    }

    public TicketDetailRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("activityid",""+this.params.activityid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/GetActivistDetails";
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
