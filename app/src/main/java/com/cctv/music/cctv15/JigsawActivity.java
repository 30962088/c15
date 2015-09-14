package com.cctv.music.cctv15;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.model.GameImg;
import com.cctv.music.cctv15.model.MyTicket;
import com.cctv.music.cctv15.network.ActivistListRequest;
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

import java.util.Date;
import java.util.List;


/*
 前1/2张图是 3*3 完成拼图奖励200分
 后1/2张图是 4*4 完成拼图奖励300分
 如果图片总数是单数，如3张 则从第2张开始是4*4
 如果图片总数是双数，如4张 则从第3张开始是4*4

 游戏时间均为100秒
 如果100秒内完成拼图，则总得分 = (100 - 游戏时间) * 5 + 完成奖励分
 如果超过100秒，则总得分 = 完成奖励分
*/
public class JigsawActivity extends BaseActivity implements JigsawView.OnJigsawViewChangeListener, View.OnClickListener {

    public static void open(Context context, MyTicket myTicket) {

        Intent intent = new Intent(context, JigsawActivity.class);

        intent.putExtra("myTicket", myTicket);

        context.startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                JigsawImgActivity.open(this, gameImgs.get(index).getGameimgurl());
                break;
        }
    }


    private class ViewHolder {
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

        public void setMyTicket(MyTicket myTicket) {
            if(!Preferences.getInstance().isLogin()){
                holder.avatar.setImageResource(R.drawable.user_default);
                holder.name.setText("尚未登录");
            }else{
                ImageLoader.getInstance().displayImage(myTicket.getLoginuserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
                name.setText(myTicket.getUsername());
            }

            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!Preferences.getInstance().isLogin()){
                        LoginActivity.open(context);
                    }

                }
            });

            score.setText("" + myTicket.getMyscore());
            rank.setText("" + myTicket.getMyranking());
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

    @Override
    protected void onResume() {
        super.onResume();
        requestScore();
    }

    private void requestScore() {
        ActivistListRequest request = new ActivistListRequest(this,new ActivistListRequest.Params(Preferences.getInstance().getUid()));
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                ActivistListRequest.Result result = (ActivistListRequest.Result)object;
                myTicket = result;
                holder.setMyTicket(myTicket);
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
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

    private void next() {
        index++;
        if (index >= gameImgs.size()) {
            index = 0;
        }
        start(index);
    }

    private Date startTime;

    private void start(int index) {
        ImageLoader.getInstance().loadImage(gameImgs.get(index).getGameimgurl(), DisplayOptions.IMG.getOptions(), new ImageLoadingListener() {
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
                startTime = new Date();
                holder.jigsaw.init(loadedImage, getSquare());
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                LoadingPopup.hide(context);
            }
        });
    }

    private int getSquare() {

        if (index + 1 <= gameImgs.size() / 2) {
            return 3;
        }
        return 4;

    }

    private int getSquareScore() {
        int count = getSquare();
        if (count == 3) {
            return 200;
        }
        return 300;
    }

    private int getDeltaScore() {
        int delta = (int) ((100 - ((new Date().getTime() - startTime.getTime()) / 1000)) * 5);
        if (delta < 0) {
            delta = 0;
        }
        return delta;
    }

    @Override
    public void onJigsawViewChange(boolean checked) {
        if (checked) {

            if(!Preferences.getInstance().isLogin()){
                next();
                return;
            }

            final int score = getDeltaScore()+getSquareScore();
            ChangeUserScoreRequest request = new ChangeUserScoreRequest(context, new ChangeUserScoreRequest.Params(score, Preferences.getInstance().getUid()));
            request.request(new BaseClient.RequestHandler() {
                @Override
                public void onComplete() {

                }

                @Override
                public void onSuccess(Object object) {

                    Dialog alertDialog = new AlertDialog.Builder(context).
                            setTitle("恭喜").
                            setMessage("本次得分：" + score).
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).
                            create();

                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            myTicket.setMyscore(myTicket.getMyscore() + score);
                            holder.setMyTicket(myTicket);
                            next();
                        }
                    });

                    alertDialog.show();

                }

                @Override
                public void onError(int error, String msg) {

                }
            });
        }
    }

}
