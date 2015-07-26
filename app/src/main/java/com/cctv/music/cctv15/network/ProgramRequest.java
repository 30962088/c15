package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.model.CCTV15;
import com.cctv.music.cctv15.model.Program;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgramRequest extends BaseClient{

    public ProgramRequest(Context context) {
        super(context);
    }

    public static class Result{
        private CCTV15 cctv15;

        public CCTV15 getCctv15() {
            return cctv15;
        }

        public Integer getCurrent(){
            Integer index = null;
            for(int i = 0;i<cctv15.getProgram().size();i++){
                Program program = cctv15.getProgram().get(i);
                if(program.getSt() == cctv15.getLiveSt()){
                    index = i;
                    break;
                }
            }
            return index;
        }

    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("serviceId","cbox");
        params.add("c","cctv15");
        params.add("d",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        return params;
    }

    @Override
    protected String getURL() {
        return "http://api.cntv.cn/epg/epginfo";
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
