package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.db.InfoTable;
import com.cctv.music.cctv15.model.PushInfo;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class PushInfoRequest extends BaseClient{

    //pushinfo?method=pushinfo&pagesize=100000&pageno=1

    public static class Params{
        private int pageno;
        private int pagesize;

        public Params(int pageno, int pagesize) {
            this.pageno = pageno;
            this.pagesize = pagesize;
        }
    }

    public static class Result{
        private ArrayList<PushInfo> pushinfolist;

        public ArrayList<PushInfo> getPushinfolist() {
            return pushinfolist;
        }

        private Integer newCount;

        public Integer getNewCount(Context context) {
            if(newCount == null){
                newCount = 0;
                for(PushInfo info : pushinfolist){
                    if(!InfoTable.isRead(context,info.getPid())){
                        newCount++;
                    }
                }
            }
            return newCount;
        }
    }

    private Params params;

    public PushInfoRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","pushinfo");
        params.add("pageno",""+this.params.pageno);
        params.add("pagesize",""+this.params.pagesize);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/pushinfo";
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
