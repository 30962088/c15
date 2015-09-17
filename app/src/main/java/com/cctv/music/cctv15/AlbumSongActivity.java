package com.cctv.music.cctv15;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.cctv.music.cctv15.adapter.SongAlbumAdapter;

import com.cctv.music.cctv15.db.OfflineDataField;
import com.cctv.music.cctv15.model.Song;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.SongRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.ui.SliderFragment;
import com.cctv.music.cctv15.utils.AppConfig;
import com.cctv.music.cctv15.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AlbumSongActivity extends BaseActivity implements BaseListView.OnLoadListener,SliderFragment.OnSliderItemClickListener,SongAlbumAdapter.OnSongAlbumClick {

    public static void open(Context context) {

        Intent intent = new Intent(context, AlbumSongActivity.class);

        context.startActivity(intent);

    }

    private List<SongAlbumAdapter.Model> list = new ArrayList<>();

    private SongAlbumAdapter adapter;

    private BaseListView listView;

    private View sliderContainer;

    private int itemSize;

    private int windowWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_rank);

        windowWidth = Utils.getScreenSize(this).x;
        itemSize = windowWidth/2-Utils.dpToPx(context, 26);
        listView = (BaseListView)findViewById(R.id.listview);
        sliderContainer = LayoutInflater.from(this).inflate(R.layout.slider_container, null);

        listView.getRefreshableView().addHeaderView(sliderContainer);
        sliderContainer.setVisibility(View.GONE);
        OfflineDataField offlineDataField =  OfflineDataField.getOffline(context, AppConfig.getInstance().getHost() + "/cctv15/GetSongInfoList0");
        if(offlineDataField != null){
            List<Song> list1 = new Gson().fromJson(offlineDataField.getData(),new TypeToken<List<Song>>(){}.getType());
            list.addAll(SongRequest.Result.getModels1(list1));
            songList.addAll(list1);
        }
        OfflineDataField offlineDataField2 =  OfflineDataField.getOffline(context, AppConfig.getInstance().getHost() + "/cctv15/GetSongInfoList1");
        if(offlineDataField2 != null){
            List<Song> list1 = new Gson().fromJson(offlineDataField2.getData(),new TypeToken<List<Song>>(){}.getType());
            sliderSongList = list1;
            if(sliderSongList == null || sliderSongList.size() == 0){
                sliderContainer.setVisibility(View.GONE);
            }else{
                sliderContainer.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.slider_container, SliderFragment.newInstance(AlbumSongActivity.this, SongRequest.Result.toSliderList1(list1))).commit();
            }

        }


        adapter = new SongAlbumAdapter(this,list,this);
        listView.setAdapter(adapter);
        listView.setOnLoadListener(this);
        listView.load(true);
    }

    @Override
    public BaseClient onLoad(int offset, int limit) {

        if(offset == 1){
            requestSlider();
        }

        SongRequest request = new SongRequest(this,new SongRequest.Params(0,offset,limit,itemSize,itemSize));

        return request;
    }

    private List<Song> sliderSongList;

    private List<Song> songList = new ArrayList<>();

    private void requestSlider() {
        SongRequest request = new SongRequest(this,new SongRequest.Params(1,1,6,windowWidth,Utils.dpToPx(context,198)));
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                SongRequest.Result result = (SongRequest.Result) object;
                sliderSongList = result.getSonglist();
                if(sliderSongList == null || sliderSongList.size() == 0){
                    sliderContainer.setVisibility(View.GONE);
                }else{
                    sliderContainer.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.slider_container, SliderFragment.newInstance(AlbumSongActivity.this, result.toSliderList())).commit();
                }

            }

            @Override
            public void onError(int error, String msg) {

            }
        });
    }

    @Override
    public boolean onLoadSuccess(Object object, int offset, int limit) {

        SongRequest.Result result = (SongRequest.Result)object;

        if(offset == 1){
            songList.clear();
            list.clear();
        }

        songList.addAll(result.getSonglist());

        list.addAll(result.getModels());

        adapter.notifyDataSetChanged();


        return result.getSonglist().size()>=limit?true:false;
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public BaseListView.Type getRequestType() {
        return BaseListView.Type.PAGE;
    }


    @Override
    public void OnSliderItemClick(int index,SliderFragment.Model model) {
        PlayActivity.open(this,new PlayActivity.Model(index,sliderSongList));
    }

    @Override
    public void onsongalbumclick(int index) {
        PlayActivity.open(this,new PlayActivity.Model(index,songList));
    }
}
