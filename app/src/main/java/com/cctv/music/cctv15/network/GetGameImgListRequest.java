package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.model.GameImg;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.List;

public class GetGameImgListRequest extends BaseClient{

    public static class Result{
        private List<GameImg> gameimglist;

        public List<GameImg> getGameimglist() {
            return gameimglist;
        }
    }

    public GetGameImgListRequest(Context context) {
        super(context);
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/GetGameImgList";
    }

    @Override
    protected Method getMethod() {
        return Method.POST;
    }

    @Override
    public Object onSuccess(String str) {
        return new Gson().fromJson(str,Result.class);
    }

    @Override
    public void onError(int error, String msg) {

    }
}
