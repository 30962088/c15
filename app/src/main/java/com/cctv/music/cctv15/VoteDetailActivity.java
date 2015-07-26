package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cctv.music.cctv15.ui.MyWebView;
import com.cctv.music.cctv15.ui.SharePopup;

public class VoteDetailActivity extends BaseActivity implements View.OnClickListener{

    public static void open(Context context, String url) {

        Intent intent = new Intent(context, VoteDetailActivity.class);

        intent.putExtra("url", url);

        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_vote_detail);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        MyWebView webView = (MyWebView) findViewById(R.id.webview);
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share:
                onshare();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void onshare() {
        SharePopup.shareContent(this);
    }
}
