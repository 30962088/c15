package com.cctv.music.cctv15;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cctv.music.cctv15.model.Comment;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.CommentRequest;
import com.cctv.music.cctv15.network.DescriptionRequest;
import com.cctv.music.cctv15.network.InsertcommentRequest;
import com.cctv.music.cctv15.ui.CommentPublishView;
import com.cctv.music.cctv15.ui.CommentView;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.ui.MyWebView;
import com.cctv.music.cctv15.ui.NewsTitleView;
import com.cctv.music.cctv15.ui.SharePopup;
import com.cctv.music.cctv15.utils.HtmlUtils;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener{

   /* public static void open(Context context, Content content) {

        Intent intent = new Intent(context, NewsDetailActivity.class);

        intent.putExtra("content", content);

        context.startActivity(intent);

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comemntcount:
                oncomment();
                break;
        }
    }

    private void oncomment() {
        newsComment(content, new OnNewsCommentListener() {
            @Override
            public void onnewscomment(Content c) {
                if(content.getContentsid() == c.getContentsid()){
                    if(content.getCommentcount() != c.getCommentcount()){
                        content.setCommentcount(c.getCommentcount());
                        holder.comemntcount.setText(""+content.getCommentcount());
                        requestComment();
                    }

                }
            }
        });

    }

    private class ViewHolder{
        private NewsTitleView titleview;
        private MyWebView webview;
        private CommentPublishView publishview;
        private ViewGroup container;
        private TextView comemntcount;
        private View bottom;

        public ViewHolder() {
            bottom = findViewById(R.id.bottom);
            titleview = (NewsTitleView) findViewById(R.id.titleview);
            webview = (MyWebView) findViewById(R.id.webview);
            publishview = (CommentPublishView) findViewById(R.id.publishview);
            container = (ViewGroup) findViewById(R.id.container);
            comemntcount = (TextView) findViewById(R.id.comemntcount);
            publishview.setModel(new CommentPublishView.OnPublishListener() {
                @Override
                public void onshare() {
                    File bitmapFile = ImageLoader.getInstance().getDiskCache().get(content.getAttachment().getAttachmentimgurl());
                    SharePopup.shareWebsite(context, content.getContentstitle(), content.getShareUrl(), bitmapFile);
                }

                @Override
                public void onsend(String text) {
                    if (TextUtils.isEmpty(text)) {
                        Utils.tip(context, "请输入要评论的内容");
                        return;
                    }

                    InsertcommentRequest request = new InsertcommentRequest(context, new InsertcommentRequest.Params("" + content.getContentsid(), text, Preferences.getInstance().getUid(), "0", "0", Preferences.getInstance().getPkey()));


                    LoadingPopup.show(context);
                    request.request(new BaseClient.RequestHandler() {
                        @Override
                        public void onComplete() {
                            LoadingPopup.hide(context);
                        }

                        @Override
                        public void onSuccess(Object object) {
                            Utils.tip(context, "评论成功");
                            publishview.clear();
                            requestComment();
                            content.setCommentcount(content.getCommentcount() + 1);
                            holder.comemntcount.setText("" + content.getCommentcount());

                        }

                        @Override
                        public void onError(int error, String msg) {
                            if (error == 1025) {
                                Utils.tip(context, "频率过于频繁");
                            } else {
                                Utils.tip(context, "评论失败");
                            }
                        }
                    });
                }
            });
        }
    }

    private Content content;

    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = (Content) getIntent().getSerializableExtra("content");
        setContentView(R.layout.activity_news_detail);
        holder = new ViewHolder();
        holder.bottom.setVisibility(View.GONE);
        holder.titleview.setModel(content);
        holder.comemntcount.setText("" + content.getCommentcount());
        holder.comemntcount.setOnClickListener(this);
        holder.webview.post(new Runnable() {
            @Override
            public void run() {
                request();
            }
        });

    }


    private void requestComment(){
        CommentRequest commentRequest = new CommentRequest(this,new CommentRequest.Params(content.getContentsid(),0,5));

        commentRequest.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                CommentRequest.Result result = (CommentRequest.Result) object;
                renderCommentList(result.getCommentList());
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
    }
    private void request() {





        LoadingPopup.show(context);

        DescriptionRequest request = new DescriptionRequest(this,new DescriptionRequest.Params(content.getContentsid()));

        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                final DescriptionRequest.Result result = (DescriptionRequest.Result) object;

                holder.comemntcount.setText("" + result.getCommentcount());
                if (result.getDescription() != null) {
                    try {
                        String html = HtmlUtils.getHtml(NewsDetailActivity.this,
                                "desc_template.html",
                                new HashMap<String, String>() {
                                    {
                                        put("content", result.getDescription());
                                    }
                                });
                        holder.webview.loadDataWithBaseURL(null, html, "text/html",
                                "utf-8", null);
                        holder.bottom.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                requestComment();
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
    }

    private void renderCommentList(List<Comment> list){
        holder.container.removeAllViews();
        for(Comment comment:list){
            CommentView view = new CommentView(this);
            view.setModel(comment);
            holder.container.addView(view);
        }

    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("content", content);
        setResult(Activity.RESULT_OK, intent);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        holder.webview.loadUrl("about:blank");
    }

    @Override
    protected void onResume() {
        super.onResume();
        holder.publishview.refresh();
    }
}
