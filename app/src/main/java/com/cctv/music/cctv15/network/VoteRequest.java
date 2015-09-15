package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.db.OfflineDataField;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.model.Vote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class VoteRequest extends BaseClient{

    public static class Params{
        protected int pageno;
        protected int pagesize;

        public Params(int pageno, int pagesize) {
            this.pageno = pageno;
            this.pagesize = pagesize;
        }
    }

    public static class Result{
        private List<Vote> votelist;

        public List<Vote> getVotelist() {
            return votelist;
        }
    }

    private Params params;

    public VoteRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        //method=vote
        RequestParams params = new RequestParams();
        params.add("method","vote");
        params.add("pageno",""+this.params.pageno);
        params.add("pagesize",""+this.params.pagesize);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/vote";
    }

    @Override
    protected Method getMethod() {
        return Method.GET;
    }

    @Override
    public Object onSuccess(String str) {

        OfflineDataField dataField = OfflineDataField.getOffline(context, getURL());

        List<Vote> list;

        if(dataField == null || params.pageno == 1){
            list = new ArrayList<>();
        }else{
            list =new Gson().fromJson(dataField.getData(), new TypeToken<List<Vote>>() {}.getType());
        }

        Result result = new Gson().fromJson(str,Result.class);

        list.addAll(result.getVotelist());

        OfflineDataField.create(context,new OfflineDataField(getURL(), new Gson().toJson(list)));


        return result;
    }

    @Override
    public void onError(int error, String msg) {

    }
}
