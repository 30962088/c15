package com.cctv.music.cctv15;
import android.content.Context;
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

import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.SongRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.ui.SliderFragment;

import java.util.ArrayList;
import java.util.List;

public class AlbumSongActivity extends BaseActivity implements BaseListView.OnLoadListener,AdapterView.OnItemClickListener,View.OnClickListener,SliderFragment.OnSliderItemClickListener {

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
        adapter = new SongAlbumAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnLoadListener(this);
        listView.load(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.slider_container, SliderFragment.newInstance(this, SliderFragment.Model.getMocks())).commit();
    }

    private int getSliderHeight(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth()/718*392;
    }

    @Override
    public BaseClient onLoad(int offset, int limit) {

        SongRequest request = new SongRequest(this,new SongRequest.Params(1,offset,limit,200,200));

        return request;
    }

    @Override
    public boolean onLoadSuccess(Object object, int offset, int limit) {

        SongRequest.Result result = (SongRequest.Result)object;

        if(offset == 1){
            list.clear();
        }

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
    public void OnSliderItemClick(SliderFragment.Model model) {

    }
}
