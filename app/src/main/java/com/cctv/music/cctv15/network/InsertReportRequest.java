package com.cctv.music.cctv15.network;


import android.content.Context;

import com.loopj.android.http.RequestParams;

public class InsertReportRequest extends BaseClient{

    //commentid=116&contents=%E6%B5%8B%E8%AF%95&userid=189
    //commentid=90&contents=%E6%9C%89%E7%9B%8A%E4%BA%8E&nickname=uuuu&phone=18500112552&userid=0


    public static class Params{
        private String commentid;
        private String contents;
        private String userid;
        private String nickname;
        private String phone;

        public Params(String commentid, String contents, String userid) {
            this.commentid = commentid;
            this.contents = contents;
            this.userid = userid;
        }

        public Params(String commentid, String nickname, String phone, String contents) {
            this.commentid = commentid;
            this.nickname = nickname;
            this.phone = phone;
            this.contents = contents;
        }

        public String getCommentid() {
            return commentid;
        }

        public String getContents() {
            return contents;
        }

        public String getUserid() {
            return userid;
        }

        public String getNickname() {
            return nickname;
        }

        public String getPhone() {
            return phone;
        }
    }

    private Params params;

    public InsertReportRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        return buildParams(params);
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/insertReport";
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
