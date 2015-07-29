package com.cctv.music.cctv15.network;

import android.content.Context;

import com.cctv.music.cctv15.adapter.SongAlbumAdapter;
import com.cctv.music.cctv15.model.Song;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class SongRequest extends BaseClient{

    //isrecommend=1&pagesize=6&pageno=1&width=100&height=100
    public static class Params{
        protected int isrecommend;
        protected int pageno;
        protected int pagesize;
        protected int width;
        protected int height;

        public Params(int isrecommend, int pageno, int pagesize, int width, int height) {
            this.isrecommend = isrecommend;
            this.pageno = pageno;
            this.pagesize = pagesize;
            this.width = width;
            this.height = height;
        }
    }

    public static class Result{
        private List<Song> songlist;

        public List<Song> getSonglist() {
            return songlist;
        }

        public List<SongAlbumAdapter.Model> getModels(){
            List<SongAlbumAdapter.Model> list = new ArrayList<>();
            SongAlbumAdapter.Model model = null;
            for(int i = 0;i<songlist.size();i++){
                if(i%2 == 0){
                    model = new SongAlbumAdapter.Model();
                    model.setCol1(songlist.get(i));
                    list.add(model);
                }else{
                    model.setCol2(songlist.get(i));
                }

            }
            return list;
        }

    }

    private Params params;

    public SongRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("isrecommend",""+this.params.isrecommend);
        params.add("pageno",""+this.params.pageno);
        params.add("pagesize",""+this.params.pagesize);
        params.add("width",""+this.params.width);
        params.add("height",""+this.params.height);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/GetSongInfoList";
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
