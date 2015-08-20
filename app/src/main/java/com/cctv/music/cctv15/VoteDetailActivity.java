package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vote = (Vote) getIntent().getSerializableExtra("vote");
        setContentView(R.layout.activity_vote_detail);
        findViewById(R.id.share).setOnClickListener(this);
        MyWebView webView = (MyWebView) findViewById(R.id.webview);
        webView.loadUrl(vote.getShareUrl());
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
}
