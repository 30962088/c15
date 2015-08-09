package com.cctv.music.cctv15;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.cctv.music.cctv15.adapter.SongAlbumAdapter;

import com.cctv.music.cctv15.model.Song;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.SongRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.ui.SliderFragment;

import java.util.ArrayList;
import java.util.List;

public class AlbumSongActivity extends BaseActivity implements BaseListView.OnLoadListener,View.OnClickListener,SliderFragment.OnSliderItemClickListener,SongAlbumAdapter.OnSongAlbumClick {

    public static void open(Context context) {

        Intent intent = new Intent(context, AlbumSongActivity.class);

        context.startActivity(intent);

    }

    private List<SongAlbumAdapter.Model> list = new ArrayList<>();

    private SongAlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_rank);
        findViewById(R.id.back).setOnClickListener(this);
        BaseListView listView = (BaseListView)findViewById(R.id.listview);
        View header = LayoutInflater.from(this).inflate(R.layout.slider_container, null);

        listView.getRefreshableView().addHeaderView(header);
        adapter = new SongAlbumAdapter(this,list,this);
        listView.setAdapter(adapter);
        listView.setOnLoadListener(this);
        listView.load(true);

    }

    private int getSliderHeight(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth()/718*392;
    }

    @Override
    public BaseClient onLoad(int offset, int limit) {

        if(offset == 1){
            requestSlider();
        }

        SongRequest request = new SongRequest(this,new SongRequest.Params(1,offset,limit,200,200));

        return request;
    }

    private List<Song> sliderSongList;

    private List<Song> songList = new ArrayList<>();

    private void requestSlider() {
        SongRequest request = new SongRequest(this,new SongRequest.Params(0,1,6,200,200));
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                SongRequest.Result result = (SongRequest.Result) object;
                sliderSongList = result.getSonglist();
                getSupportFragmentManager().beginTransaction().replace(R.id.slider_container, SliderFragment.newInstance(AlbumSongActivity.this, result.toSliderList())).commit();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
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
