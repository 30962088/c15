package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;

import com.cctv.music.cctv15.adapter.ProgramAdapter;
import com.cctv.music.cctv15.model.Program;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.ProgramRequest;
import com.cctv.music.cctv15.ui.VideoView;
import com.cctv.music.cctv15.utils.Utils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LiveActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<ListView>,BaseClient.RequestHandler,View.OnClickListener{

    public static void open(Context context) {

        Intent intent = new Intent(context, LiveActivity.class);

        context.startActivity(intent);

    }

    private List<Program> list = new ArrayList<>();

    private PullToRefreshListView listView;

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        videoView = (VideoView) findViewById(R.id.video);
        videoView.setVidepPath("http://m3u8.1du1du.com:1019/cctv15.m3u8");
//        videoView.setVidepPath("http://live.1du1du.com/service/cctvmusic/ipad_CCTVMusic-TEST.m3u8");
//        videoView.setVidepPath("http://download.wavetlan.com/SVV/Media/HTTP/MP4/ConvertedFiles/MediaCoder/MediaCoder_test2_1m10s_XVID_VBR_131kbps_480x320_25fps_AACLCv4_VBR_32kbps_Stereo_24000Hz.mp4");


        findViewById(R.id.btn_program).setOnClickListener(this);
        listView = (PullToRefreshListView)findViewById(R.id.listview);
        listView.setOnRefreshListener(this);
        listView.setRefreshing(true);
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(this, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        ProgramRequest request = new ProgramRequest(this,new ProgramRequest.Params(new Date()));
        request.request(this);
    }

    @Override
    public void onComplete() {
        listView.onRefreshComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.release();
    }

    @Override
    public void onSuccess(Object object) {
        final ProgramRequest.Result result = (ProgramRequest.Result)object;
        if(result != null){
            Integer current = result.getCurrent();
            listView.setAdapter(new ProgramAdapter(this, result.getCctv15().getProgram(),current));
            if(current != null){
                listView.getRefreshableView().post(new Runnable() {
                    @Override
                    public void run() {
                        listView.getRefreshableView().smoothScrollToPosition(result.getCurrent() + 1);
                    }
                });

            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    public void onError(int error, String msg) {
        Utils.tip(this,"列表加载失败");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_program:
                ProgramsActivity.open(this);
                break;
        }
    }
}
