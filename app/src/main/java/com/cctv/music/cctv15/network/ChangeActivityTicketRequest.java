package com.cctv.music.cctv15.network;

import android.content.Context;

import com.loopj.android.http.RequestParams;


public class ChangeActivityTicketRequest extends BaseClient{

    public static class Params{
        private String activityid;
        private String changed_code;
        private String changename;
        private String userid;

        public Params(String activityid, String changed_code, String changename, String userid) {
            this.activityid = activityid;
            this.changed_code = changed_code;
            this.changename = changename;
            this.userid = userid;
        }
    }

    private Params params;

    public ChangeActivityTicketRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("activityid",this.params.activityid);
        params.add("changed_code",this.params.changed_code);
        params.add("changename",this.params.changename);
        params.add("userid",this.params.userid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/ChangeActivityTicket";
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
