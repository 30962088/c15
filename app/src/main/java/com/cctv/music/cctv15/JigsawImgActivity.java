package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.cctv.music.cctv15.utils.DisplayOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class JigsawImgActivity extends BaseActivity{


    public static void open(Context context,String url) {

        Intent intent = new Intent(context, JigsawImgActivity.class);

        intent.putExtra("url",url);

        context.startActivity(intent);

    }

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_jigsaw_img);
        ImageView imageView = ((ImageView) findViewById(R.id.img));
        ImageLoader.getInstance().displayImage(url, imageView, DisplayOptions.IMG.getOptions());

    }
}
