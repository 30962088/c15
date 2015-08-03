package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cctv.music.cctv15.adapter.PushInfoAdapter;
import com.cctv.music.cctv15.model.PushInfo;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.PushInfoRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

public class PushInfoActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<ListView>,AdapterView.OnItemClickListener,View.OnClickListener,BaseClient.RequestHandler{


    public static void open(Context context,ArrayList<PushInfo> arrayList) {

        Intent intent = new Intent(context, PushInfoActivity.class);

        intent.putExtra("list",arrayList);

        context.startActivity(intent);

    }

    private PullToRefreshListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<PushInfo> list = (ArrayList<PushInfo>) getIntent().getSerializableExtra("list");

        setContentView(R.layout.activity_pushinfo);
        findViewById(R.id.back).setOnClickListener(this);
        listView = (PullToRefreshListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        if(list != null){
            listView.setAdapter(new PushInfoAdapter(this,list));
        }else{
            listView.setRefreshing();
        }


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
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        PushInfoRequest request = new PushInfoRequest(this,new PushInfoRequest.Params(1,100000));
        request.request(this);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSuccess(Object object) {
        PushInfoRequest.Result result = (PushInfoRequest.Result)object;
        listView.setAdapter(new PushInfoAdapter(this,result.getPushinfolist()));
    }

    @Override
    public void onError(int error, String msg) {

    }
}
