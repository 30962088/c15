package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.model.SongComment;
import com.cctv.music.cctv15.ui.Comment2View;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class GetSongOfCommentInfoRequest extends BaseClient {

    //songid=10&pagesize=20&pageno=1

    public static class Params{

        private String songid;

        private int pageno;

        private int pagesize;

        public Params(String songid, int pageno, int pagesize) {
            this.songid = songid;
            this.pageno = pageno;
            this.pagesize = pagesize;
        }

        public String getSongid() {
            return songid;
        }

        public int getPageno() {
            return pageno;
        }

        public int getPagesize() {
            return pagesize;
        }
    }

    public static class Result{
        private List<SongComment> modellist;

        public List<SongComment> getModellist() {
            return modellist;
        }

        public List<Comment2View.CommentItem> getCommentlist() {

            List<Comment2View.CommentItem> list = new ArrayList<>();

            for(SongComment comment : modellist){
                list.add(comment.toCommentItem());
            }

            return list;
        }



    }


    private Params params;

    public GetSongOfCommentInfoRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        return buildParams(params);
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/GetSongOfCommentInfo";
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
