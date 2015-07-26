package com.cctv.music.cctv15;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;

import com.cctv.music.cctv15.adapter.ProgramAdapter;
import com.cctv.music.cctv15.model.Program;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.ProgramRequest;
import com.cctv.music.cctv15.utils.Utils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class LiveActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener<ListView>,BaseClient.RequestHandler {

    private List<Program> list = new ArrayList<>();

    private PullToRefreshListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        findViewById(R.id.back).setOnClickListener(this);
        listView = (PullToRefreshListView)findViewById(R.id.listview);
        listView.setOnRefreshListener(this);
        listView.setRefreshing(true);
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
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(this, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        ProgramRequest request = new ProgramRequest(this);
        request.request(this);
    }

    @Override
    public void onComplete() {
        listView.onRefreshComplete();
    }

    @Override
    public void onSuccess(Object object) {
        final ProgramRequest.Result result = (ProgramRequest.Result)object;
        listView.setAdapter(new ProgramAdapter(this, result.getCctv15()));
        Integer current = result.getCurrent();
        if(current != null){
            listView.getRefreshableView().post(new Runnable() {
                @Override
                public void run() {
                    listView.getRefreshableView().smoothScrollToPosition(result.getCurrent() + 1);
                }
            });

        }

    }

    @Override
    public void onError(int error, String msg) {
        Utils.tip(this,"列表加载失败");
    }
}
