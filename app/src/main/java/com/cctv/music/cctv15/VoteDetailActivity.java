package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cctv.music.cctv15.model.Vote;
import com.cctv.music.cctv15.ui.MyWebView;
import com.cctv.music.cctv15.ui.SharePopup;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

public class VoteDetailActivity extends BaseActivity implements View.OnClickListener{

    public static void open(Context context, Vote vote) {

        Intent intent = new Intent(context, VoteDetailActivity.class);

        intent.putExtra("vote", vote);

        context.startActivity(intent);

    }

    private Vote vote;

    private MyWebView webView;

    private View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vote = (Vote) getIntent().getSerializableExtra("vote");
        setContentView(R.layout.activity_vote_detail);
        loading = findViewById(R.id.loading);
        findViewById(R.id.share).setOnClickListener(this);
        webView = (MyWebView) findViewById(R.id.webview);
        webView.loadUrl(vote.getWebUrl());
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                loading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share:
                onshare();
                break;
        }
    }

    private void onshare() {
        File bitmapFile = ImageLoader.getInstance().getDiskCache().get(vote.getAttachment().getAttachmentimgurl());

        SharePopup.shareWebsite(context,vote.getVotetitle(),vote.getShareUrl(),bitmapFile);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.loadUrl("about:blank");
    }
}
