package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.model.NewsDescription;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class DescriptionRequest extends BaseClient{

    public static class Params{
        protected int contentsid;
        public Params(int contentsid) {
            this.contentsid = contentsid;
        }
    }

    public static class Result extends NewsDescription {

    }

    private Params params;

    public DescriptionRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","description");
        params.add("contentsid",""+this.params.contentsid);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/description";
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
