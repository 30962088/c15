package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cctv.music.cctv15.model.Comment;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.CommentRequest;
import com.cctv.music.cctv15.network.DescriptionRequest;
import com.cctv.music.cctv15.ui.CommentPublishView;
import com.cctv.music.cctv15.ui.CommentView;
import com.cctv.music.cctv15.ui.MyWebView;
import com.cctv.music.cctv15.ui.NewsTitleView;
import com.cctv.music.cctv15.utils.HtmlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NewsDetailActivity extends BaseActivity{

    public static void open(Context context, Content content) {

        Intent intent = new Intent(context, NewsDetailActivity.class);

        intent.putExtra("content", content);

        context.startActivity(intent);

    }

    private class ViewHolder{
        private NewsTitleView titleview;
        private MyWebView webview;
        private CommentPublishView publishview;
        private ViewGroup container;
        private TextView comemntcount;

        public ViewHolder() {
            titleview = (NewsTitleView) findViewById(R.id.titleview);
            webview = (MyWebView) findViewById(R.id.webview);
            publishview = (CommentPublishView) findViewById(R.id.publishview);
            container = (ViewGroup) findViewById(R.id.container);
            comemntcount = (TextView) findViewById(R.id.comemntcount);
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
        holder.titleview.setModel(content);
        holder.comemntcount.setText("" + content.getCommentcount());
        request();
    }

    private void request() {

        CommentRequest commentRequest = new CommentRequest(this,new CommentRequest.Params(content.getContentsid(),0,10));

        commentRequest.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                CommentRequest.Result result = (CommentRequest.Result) object;
                renderCommentList(result.getCommentlist());
            }

            @Override
            public void onError(int error, String msg) {

            }
        });


        DescriptionRequest request = new DescriptionRequest(this,new DescriptionRequest.Params(content.getContentsid()));

        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
    }

    private void renderCommentList(List<Comment> list){
        for(Comment comment:list){
            CommentView view = new CommentView(this);
            view.setModel(comment);
            holder.container.addView(view);
        }

    }

}
