package com.cctv.music.cctv15;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.cctv.music.cctv15.adapter.NewsAdapter;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.ContentRequest;
import com.cctv.music.cctv15.ui.BaseListView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity implements BaseListView.OnLoadListener,AdapterView.OnItemClickListener{

    private List<Content> list = new ArrayList<>();

    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        BaseListView listView = (BaseListView)findViewById(R.id.listview);
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
