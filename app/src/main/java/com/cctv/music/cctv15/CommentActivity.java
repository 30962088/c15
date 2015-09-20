package com.cctv.music.cctv15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cctv.music.cctv15.adapter.CommentAdapter;
import com.cctv.music.cctv15.model.Comment;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.CommentRequest;
import com.cctv.music.cctv15.network.InsertcommentRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.ui.Comment2View;
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

public class CommentActivity extends BaseActivity implements BaseListView.OnLoadListener,CommentPublishView.OnPublishListener,Comment2View.OnCommentViewListener,View.OnTouchListener {

  /*  public static void open(Context context, Content content) {

        Intent intent = new Intent(context, CommentActivity.class);

        intent.putExtra("content", content);

        context.startActivity(intent);

    }*/

    private Content content;

    private List<Comment2View.CommentItem> list = new ArrayList<>();

    private CommentAdapter adapter;

    private BaseListView listView;

    private CommentPublishView publishView;

    private View container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = (Content) getIntent().getSerializableExtra("content");
        setContentView(R.layout.activity_commentl);
        container = findViewById(R.id.container);
        publishView = (CommentPublishView) findViewById(R.id.publishview);
        publishView.setModel(this);
        listView = (BaseListView) findViewById(R.id.listview);
        listView.getRefreshableView().setOnTouchListener(this);
        HdCommentView hdCommentView = new HdCommentView(this);
        listView.getRefreshableView().addHeaderView(hdCommentView);
        listView.setLimit(20);
        hdCommentView.setModel(content);
        adapter = new CommentAdapter(this,list,this);
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
    protected void onResume() {
        super.onResume();
        publishView.refresh();
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

        InsertcommentRequest request = new InsertcommentRequest(context,new InsertcommentRequest.Params(""+content.getContentsid(),text, Preferences.getInstance().getUid(),iscommentid,isuserid,Preferences.getInstance().getPkey()));


        LoadingPopup.show(context);
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                Utils.tip(context,"评论成功");
                content.setCommentcount(content.getCommentcount()+1);
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

    private String isuserid = "0";

    private String iscommentid = "0";

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        EditText editText = publishView.getHoder().getEditText();
        if (editText.isFocused()) {
            /*isuserid = "0";
            iscommentid = "0";*/
            container.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    @Override
    public void onCommentClick(Comment2View.CommentItem comment) {
        if (!Preferences.getInstance().isLogin()) {
            Utils.tip(context,"系统检测您还没有登录");
            LoginActivity.open(this);
            return;
        }
        EditText editText = publishView.getHoder().getEditText();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        isuserid = ""+comment.getUserid();
        iscommentid = ""+comment.getCommentid();
        editText.requestFocus();
        editText.setHint("回复 "+comment.getUsername());
    }

    @Override
    public void onJubaoClick(Comment2View.CommentItem comment) {
        JubaoActivity.open(context, comment);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("content", content);
        setResult(Activity.RESULT_OK, intent);
        super.finish();
    }

}
