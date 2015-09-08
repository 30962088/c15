package com.cctv.music.cctv15.network;


import android.content.Context;

import com.loopj.android.http.RequestParams;

public class InsertcommentRequest extends BaseClient{

    public static class Params{
        private String contentsid;
        private String remark;
        private String userid;
        private String commentid;
        private String isuserid;
        private String pkey;

        public Params(String contentsid, String remark, String userid, String commentid, String isuserid, String pkey) {
            this.contentsid = contentsid;
            this.remark = remark;
            this.userid = userid;
            this.commentid = commentid;
            this.isuserid = isuserid;
            this.pkey = pkey;
        }



    }

    private Params params;

    public InsertcommentRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","insertcomment");
        params.add("contentsid",this.params.contentsid);
        params.add("remark",this.params.remark);
        params.add("userid",this.params.userid);
        params.add("commentid",this.params.commentid);
        params.add("isuserid",""+this.params.isuserid);
        params.add("pkey",""+this.params.pkey);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/insertcomment";
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
