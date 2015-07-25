package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.model.Comment;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.List;

public class CommentRequest extends BaseClient{

    public static class Params{
        protected int contentsid;
        protected int pageno;
        protected int pagesize;

        public Params(int contentsid, int pageno, int pagesize) {
            this.contentsid = contentsid;
            this.pageno = pageno;
            this.pagesize = pagesize;
        }
    }

    public static class Result{
        private List<Comment> commentlist;

        public List<Comment> getCommentlist() {
            return commentlist;
        }
    }

    private Params params;

    public CommentRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","comment");
        params.add("contentsid",""+this.params.contentsid);
        params.add("pageno",""+this.params.pageno);
        params.add("pagesize",""+this.params.pagesize);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/comment";
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
