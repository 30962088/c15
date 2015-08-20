package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.model.ActivistTicket;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.List;

public class GetMyActivistTicketListRequest extends BaseClient{

    public static class Params{
        private String userid;

        public Params(String userid) {
            this.userid = userid;
        }
    }

    public static class Result{
        private List<ActivistTicket> myticketlist;

        public List<ActivistTicket> getMyticketlist() {
            return myticketlist;
        }
    }

    private Params params;

    public GetMyActivistTicketListRequest(Context context, Params params) {
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
        return HOST+"/cctv15/GetMyActivistTicketList";
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
