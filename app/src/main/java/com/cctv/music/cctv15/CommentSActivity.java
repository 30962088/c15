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
import android.widget.TextView;

import com.cctv.music.cctv15.adapter.CommentAdapter;
import com.cctv.music.cctv15.model.Song;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.GetSongOfCommentInfoRequest;
import com.cctv.music.cctv15.network.InsertSongCommentRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.ui.Comment2View;
import com.cctv.music.cctv15.ui.CommentPublishView;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.ui.SharePopup;
import com.cctv.music.cctv15.utils.AppConfig;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommentSActivity extends BaseActivity implements BaseListView.OnLoadListener,CommentPublishView.OnPublishListener,Comment2View.OnCommentViewListener,View.OnTouchListener {

    public static void open(Context context, Song song) {

        Intent intent = new Intent(context, CommentSActivity.class);

        intent.putExtra("song", song);

        context.startActivity(intent);

    }

    private Song song;

    private List<Comment2View.CommentItem> list = new ArrayList<>();

    private CommentAdapter adapter;

    private BaseListView listView;

    private CommentPublishView publishView;

    private View container;

    private TextView commentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        song = (Song) getIntent().getSerializableExtra("song");
        setContentView(R.layout.activity_comment_song);
        commentView = ((TextView)findViewById(R.id.comemntcount));
        commentView.setText("" + song.getComment_count());
        ((TextView)findViewById(R.id.title)).setText(song.getSongname());
        container = findViewById(R.id.container);
        publishView = (CommentPublishView) findViewById(R.id.publishview);
        publishView.setModel(this);
        listView = (BaseListView) findViewById(R.id.listview);
        listView.getRefreshableView().setOnTouchListener(this);
        listView.setLimit(20);
        adapter = new CommentAdapter(this,list,this);
        listView.setAdapter(adapter);
        listView.setOnLoadListener(this);
        listView.load(true);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("song", song);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public BaseClient onLoad(int offset, int limit) {
        GetSongOfCommentInfoRequest request = new GetSongOfCommentInfoRequest(this,new GetSongOfCommentInfoRequest.Params(""+song.getSid(),offset,limit));
        return request;
    }

    @Override
    public boolean onLoadSuccess(Object object, int offset, int limit) {
        GetSongOfCommentInfoRequest.Result result = (GetSongOfCommentInfoRequest.Result)object;

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
        File bitmapFile = ImageLoader.getInstance().getDiskCache().get(song.getSurfaceurl());
        SharePopup.shareWebsite(context, "欢迎参与音乐频道官方客户端互动。"+song.getSongname(), AppConfig.getInstance().getHost()+"/voteview/MusicShare?songid="+song.getSid(), bitmapFile);
    }

    @Override
    public void onsend(String text) {

        if(TextUtils.isEmpty(text)){
            Utils.tip(context,"请输入要评论的内容");
            return;
        }

        InsertSongCommentRequest request = new InsertSongCommentRequest(context,new InsertSongCommentRequest.Params(""+song.getSid(),text, Preferences.getInstance().getUid(),iscommentid,isuserid,Preferences.getInstance().getPkey()));


        LoadingPopup.show(context);
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {

                song.setComment_count(song.getComment_count()+1);
                commentView.setText(""+song.getComment_count());

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
        JubaoActivity.open(context,comment);
    }



}
