package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.cctv.music.cctv15.adapter.CommentAdapter;
import com.cctv.music.cctv15.model.Comment;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.CommentRequest;
import com.cctv.music.cctv15.network.InsertcommentRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.ui.CommentPublishView;
import com.cctv.music.cctv15.ui.HdCommentView;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.ui.SharePopup;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends BaseActivity implements BaseListView.OnLoadListener,CommentPublishView.OnPublishListener{

    public static void open(Context context, Content content) {

        Intent intent = new Intent(context, CommentActivity.class);

        intent.putExtra("content", content);

        context.startActivity(intent);

    }

    private Content content;

    private List<Comment> list = new ArrayList<>();

    private CommentAdapter adapter;

    private BaseListView listView;

    private CommentPublishView publishView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = (Content) getIntent().getSerializableExtra("content");
        setContentView(R.layout.activity_commentl);
        publishView = (CommentPublishView) findViewById(R.id.publishview);
        publishView.setModel(this);
        listView = (BaseListView) findViewById(R.id.listview);
        HdCommentView hdCommentView = new HdCommentView(this);
        listView.getRefreshableView().addHeaderView(hdCommentView);
        listView.setLimit(20);
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

    @Override
    public void onshare() {
        File bitmapFile = ImageLoader.getInstance().getDiskCache().get(content.getAttachment().getAttachmentimgurl());
        SharePopup.shareWebsite(context, content.getContentstitle(), content.getShareUrl(), bitmapFile);
    }

    @Override
    public void onsend(String text) {

        if(TextUtils.isEmpty(text)){
            Utils.tip(context,"请输入要评论的内容");
            return;
        }

        InsertcommentRequest request = new InsertcommentRequest(context,new InsertcommentRequest.Params(""+content.getContentsid(),text, Preferences.getInstance().getUid(),"0",0,Preferences.getInstance().getPkey()));


        LoadingPopup.show(context);
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                Utils.tip(context,"评论成功");
                publishView.clear();
                listView.load(false);
            }

            @Override
            public void onError(int error, String msg) {
                if(error == 1025){
                    Utils.tip(context,"频率过于频繁");
                }else{
                    Utils.tip(context,"评论失败");
                }
            }
        });

    }
}
