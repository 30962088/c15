package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;
import com.cctv.music.cctv15.ui.CircleLayout;
import com.cctv.music.cctv15.ui.PausableRotateAnimation;
import com.cctv.music.cctv15.ui.RotateView;
import com.cctv.music.cctv15.utils.AppConfig;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateConfig;

import java.io.IOException;

public class MainActivity extends BaseActivity implements RotateView.OnRotateListener, View.OnClickListener {


    public static void open(Context context) {

        Intent intent = new Intent(context, MainActivity.class);

        context.startActivity(intent);

    }

    public static final String ACTION_TOLOGIN = "action_tologin";

    public static final String ACTION_OPEN = "action_open";


    private class ViewHolder {
        private View clock;
        private View line;
        private View kedu;
        private RotateView rotateview;
        private CircleLayout circleview;

        public ViewHolder() {
            clock = findViewById(R.id.clock);
            line = findViewById(R.id.line);
            kedu = findViewById(R.id.kedu);
            rotateview = (RotateView) findViewById(R.id.rotateview);
            circleview = (CircleLayout) findViewById(R.id.circleview);
        }
    }

    private ViewHolder holder;

    private PausableRotateAnimation lineAnimation;

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPush();
        UmengUpdateAgent.update(this);
        if (TextUtils.equals(AppConfig.getInstance().getUMENG_CHANNEL(), "development")) {
            UpdateConfig.setDebug(true);
        }
        MobclickAgent.updateOnlineConfig(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        initMedia();
        holder = new ViewHolder();
        holder.rotateview.setOnRotateListener(this);
        initAnimation();



    }

    private void initAnimation() {
        lineAnimation = new PausableRotateAnimation(0f, 358f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        lineAnimation.setInterpolator(new LinearInterpolator());
        lineAnimation.setDuration(2000);
        lineAnimation.setFillAfter(true);
        lineAnimation.setRepeatCount(Animation.INFINITE);
        holder.line.startAnimation(lineAnimation);
    }

    private void initMedia() {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            AssetFileDescriptor descriptor = getAssets().openFd("button.wav");
            player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start() {
        lineAnimation.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lineAnimation.pause();
    }

    @Override
    public void onrotate(double rotate) {
        player.seekTo(0);
        player.start();
        ViewCompat.setRotation(holder.clock, (float) rotate);
    }


    private int rotate = 0;

    @Override
    public void end(RotateView.Direction dir) {
        int rotate = 360 / 6;
        if (dir == RotateView.Direction.Up) {
            rotate = -rotate;
        }
        this.rotate += rotate;
        holder.circleview.setDegree(dir == RotateView.Direction.Up ? false : true, 200);
        lineAnimation.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lineAnimation.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn:
                onBtnClick();
                break;
        }
    }

    private void onBtnClick() {
        int index = holder.circleview.getIndex();
        switch (index) {
            case 0:
                NewsActivity.open(this);
                break;
            case 1:
                VoteActivity.open(this);
                break;
            case 2:
                AlbumSongActivity.open(this);
                break;
            case 3:
                ZoneActivity.open(this);
                break;
            case 4:
                /*if (!Preferences.getInstance().isLogin()) {
                    Utils.tip(this, "请先登录");
                    LoginActivity.open(this);
                    return;
                }*/
                TicketActivity.open(this);
                break;
            case 5:
                LiveActivity.open(this);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (TextUtils.equals(ACTION_TOLOGIN, intent.getAction())) {
            Utils.tip(context, "该账号已经在其他设备上登陆");
        }else if(TextUtils.equals(ACTION_OPEN,intent.getAction())){
            try {
                Intent i = new Intent(context,Class.forName(intent.getStringExtra("class")));

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void initPush() {

        PushSettings.enableDebugMode(context, true);
        if (Preferences.getInstance().getNewsPush()) {
            PushManager.startWork(getApplicationContext(),
                    PushConstants.LOGIN_TYPE_API_KEY,
                    AppConfig.getInstance().getPush_api_key());

        }
        if(Preferences.getInstance().isLogin()){
            Utils.setTag(context, Preferences.getInstance().getUid());
        }

        if (Preferences.getInstance().getVoice()) {
            PushManager.setNoDisturbMode(this, 0, 0, 0, 0);
        } else {
            PushManager.setNoDisturbMode(this, 0, 0, 23, 59);
        }

    }

}
