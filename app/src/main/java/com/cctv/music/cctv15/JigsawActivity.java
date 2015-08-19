package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.model.GameImg;
import com.cctv.music.cctv15.model.MyTicket;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.ChangeUserScoreRequest;
import com.cctv.music.cctv15.network.GetGameImgListRequest;
import com.cctv.music.cctv15.ui.JigsawView;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class JigsawActivity extends BaseActivity implements JigsawView.OnJigsawViewChangeListener,View.OnClickListener {

    public static void open(Context context,MyTicket myTicket) {

        Intent intent = new Intent(context, JigsawActivity.class);

        intent.putExtra("myTicket", myTicket);

        context.startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_right:
                JigsawImgActivity.open(this,gameImgs.get(index).getGameimgurl());
                break;
        }
    }


    private class ViewHolder{
        private JigsawView jigsaw;
        private ImageView avatar;
        private TextView name;
        private TextView score;
        private TextView rank;

        public ViewHolder() {

            jigsaw = (JigsawView) findViewById(R.id.jigsaw);
            avatar = (ImageView) findViewById(R.id.avatar);
            name = (TextView) findViewById(R.id.name);
            score = (TextView) findViewById(R.id.score);
            rank = (TextView) findViewById(R.id.rank);

        }

        public void setMyTicket(MyTicket myTicket){
            ImageLoader.getInstance().displayImage(myTicket.getLoginuserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
            name.setText(myTicket.getUsername());
            score.setText("" + myTicket.getMyscore());
            rank.setText(""+myTicket.getMyranking());
        }

    }

    private ViewHolder holder;

    private MyTicket myTicket;

    private List<GameImg> gameImgs;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTicket = (MyTicket) getIntent().getSerializableExtra("myTicket");
        setContentView(R.layout.activity_jigsaw);
        holder = new ViewHolder();
        holder.setMyTicket(myTicket);
        holder.jigsaw.setOnJigsawViewChangeListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
        request();
    }

    private void request() {
        GetGameImgListRequest request = new GetGameImgListRequest(context);

        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                GetGameImgListRequest.Result result = (GetGameImgListRequest.Result) object;
                gameImgs = result.getGameimglist();
                start(0);
            }

            @Override
            public void onError(int error, String msg) {
                Utils.tip(context, "游戏加载失败");
            }
        });
    }

    private void next(){
        index ++;
        if(index >= gameImgs.size()){
            index = 0;
        }
        start(index);
    }

    private void start(int index){
        ImageLoader.getInstance().loadImage(gameImgs.get(index).getGameimgurl(),DisplayOptions.IMG.getOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                LoadingPopup.show(context);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                LoadingPopup.hide(context);
                Utils.tip(context, "图片加载失败");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                LoadingPopup.hide(context);
                holder.jigsaw.init(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                LoadingPopup.hide(context);
            }
        });
    }

    @Override
    public void onJigsawViewChange(boolean checked) {
        if(checked){
            final int score = 20;
            ChangeUserScoreRequest request = new ChangeUserScoreRequest(context,new ChangeUserScoreRequest.Params(score, Preferences.getInstance().getUid()));
            request.request(new BaseClient.RequestHandler() {
                @Override
                public void onComplete() {

                }

                @Override
                public void onSuccess(Object object) {
                    myTicket.setMyscore(myTicket.getMyscore()+score);
                    holder.setMyTicket(myTicket);
                    next();
                }

                @Override
                public void onError(int error, String msg) {

                }
            });
        }
    }

}
