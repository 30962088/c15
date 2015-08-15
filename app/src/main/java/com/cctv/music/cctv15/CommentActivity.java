package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cctv.music.cctv15.adapter.CommentAdapter;
import com.cctv.music.cctv15.model.Comment;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.CommentRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.ui.HdCommentView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends BaseActivity implements BaseListView.OnLoadListener{

    public static void open(Context context, Content content) {

        Intent intent = new Intent(context, CommentActivity.class);

        intent.putExtra("content", content);

        context.startActivity(intent);

    }

    private Content content;

    private List<Comment> list = new ArrayList<>();

    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = (Content) getIntent().getSerializableExtra("content");
        setContentView(R.layout.activity_commentl);
        BaseListView listView = (BaseListView) findViewById(R.id.listview);
        HdCommentView hdCommentView = new HdCommentView(this);
        listView.getRefreshableView().addHeaderView(hdCommentView);
        hdCommentView.setModel(content);
        adapter = new CommentAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnLoadListener(this);
        listView.load(true);
    }

    @Override
    public BaseClient onLoad(int offset, int limit) {
        CommentRequest request = new CommentRequest(this,new CommentRequest.Params(content.getContentsid(),offset,limit));
        return request;
    }

    @Override
    public boolean onLoadSuccess(Object object, int offset, int limit) {
        CommentRequest.Result result = (CommentRequest.Result)object;

        if(offset == 1){
            list.clear();
        }
        list.addAll(result.getCommentlist());

        adapter.notifyDataSetChanged();

        return result.getCommentlist().size()>=limit?true:false;
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public BaseListView.Type getRequestType() {
        return BaseListView.Type.PAGE;
    }

}
