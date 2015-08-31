package com.cctv.music.cctv15.network;


import android.content.Context;

import com.loopj.android.http.RequestParams;

public class InsertSongCommentRequest extends BaseClient{

    public static class Params{
        private int becommentid = 0;

        private int beuserid = 0;

        private String songid;

        private String commentcontent;

        private String userid;

        private String pkey;

        public Params(String songid, String commentcontent, String userid, String pkey) {
            this.songid = songid;
            this.commentcontent = commentcontent;
            this.userid = userid;
            this.pkey = pkey;
        }

        public int getBecommentid() {
            return becommentid;
        }

        public int getBeuserid() {
            return beuserid;
        }

        public String getSongid() {
            return songid;
        }

        public String getCommentcontent() {
            return commentcontent;
        }

        public String getUserid() {
            return userid;
        }

        public String getPkey() {
            return pkey;
        }
    }


    private Params params;

    public InsertSongCommentRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {

        return buildParams(params);
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/InsertSongComment";
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
