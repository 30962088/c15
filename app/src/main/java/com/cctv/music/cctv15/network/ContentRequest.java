package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.db.OfflineDataField;
import com.cctv.music.cctv15.model.Content;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class ContentRequest extends BaseClient{


    public static class Params{
        protected int categoryid;
        protected int pageno;
        protected int pagesize;
        public Params(int categoryid, int pageno, int pagesize) {
            this.categoryid = categoryid;
            this.pageno = pageno;
            this.pagesize = pagesize;
        }
    }

    public static class Result{
        private List<Content> list;

        public List<Content> getList() {
            return list;
        }
    }

    private Params params;


    public ContentRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method", "contents");
        params.add("categoryid",""+this.params.categoryid);
        params.add("pageno",""+this.params.pageno);
        params.add("pagesize",""+this.params.pagesize);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/contents";
    }

    @Override
    protected Method getMethod() {
        return Method.GET;
    }




    @Override
    public Object onSuccess(String str) {


        OfflineDataField dataField = OfflineDataField.getOffline(context, getURL());

        List<Content> list;

        if(dataField == null || params.pageno == 1){
            list = new ArrayList<>();
        }else{
            list =new Gson().fromJson(dataField.getData(), new TypeToken<List<Content>>() {}.getType());
        }

        Result result = new Gson().fromJson(str,Result.class);

        list.addAll(result.getList());

        OfflineDataField.create(context,new OfflineDataField(getURL(), new Gson().toJson(list)));

        return result;
    }



    @Override
    public void onError(int error, String msg) {

    }
}
