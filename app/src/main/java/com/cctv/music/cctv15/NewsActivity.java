package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.cctv.music.cctv15.adapter.NewsAdapter;
import com.cctv.music.cctv15.db.OfflineDataField;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.ContentRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.utils.AppConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity implements BaseListView.OnLoadListener,AdapterView.OnItemClickListener{

    public static void open(Context context) {

        Intent intent = new Intent(context, NewsActivity.class);

        context.startActivity(intent);

    }

    private List<Content> list = new ArrayList<>();

    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        BaseListView listView = (BaseListView)findViewById(R.id.listview);
        OfflineDataField offlineDataField =  OfflineDataField.getOffline(context, AppConfig.getInstance().getHost() + "/cctv15/contents");
        if(offlineDataField != null){
            List<Content> list1 = new Gson().fromJson(offlineDataField.getData(),new TypeToken<List<Content>>(){}.getType());
            list.addAll(list1);
        }

        adapter = new NewsAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnLoadListener(this);
        listView.load(true);
    }

    @Override
    public BaseClient onLoad(int offset, int limit) {

        ContentRequest request = new ContentRequest(this,new ContentRequest.Params(1,offset,limit));


        return request;
    }

    @Override
    public boolean onLoadSuccess(Object object, int offset, int limit) {

        ContentRequest.Result result = (ContentRequest.Result)object;

        if(offset == 1){
            list.clear();
        }

        list.addAll(result.getList());

        adapter.notifyDataSetChanged();

        return result.getList().size()>=limit?true:false;
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
        Content content = list.get(position - 1);
        NewsDetailActivity.open(this,content);
    }



}
