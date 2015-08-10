package com.cctv.music.cctv15;


import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.cctv.music.cctv15.ui.CircleLayout;
import com.cctv.music.cctv15.ui.PausableRotateAnimation;
import com.cctv.music.cctv15.ui.RotateView;

import java.io.IOException;

public class MainActivity extends BaseActivity implements RotateView.OnRotateListener,View.OnClickListener{




    private class ViewHolder{
        private View clock;
        private View line;
        private View guang;
        private View kedu;
        private RotateView rotateview;
        private CircleLayout circleview;

        public ViewHolder() {
            clock = findViewById(R.id.clock);
            line = findViewById(R.id.line);
            guang = findViewById(R.id.guang);
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
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        initMedia();
        holder = new ViewHolder();
        holder.rotateview.setOnRotateListener(this);
        initAnimation();

    }
    private void initAnimation() {
        lineAnimation = new PausableRotateAnimation(0f,358f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
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
        int rotate = 360/6;
        if(dir == RotateView.Direction.Up){
            rotate = -rotate;
        }
        this.rotate += rotate;
        holder.circleview.setDegree(dir == RotateView.Direction.Up?false:true,200);
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
        switch (v.getId()){
            case R.id.btn:
                onBtnClick();
                break;
        }
    }

    private void onBtnClick() {
        int index = holder.circleview.getIndex();
        switch (index){
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
                TicketActivity.open(this);
                break;
            case 5:
                LiveActivity.open(this);
                break;
        }
    }

}
