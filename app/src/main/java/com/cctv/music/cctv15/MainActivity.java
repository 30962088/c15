package com.cctv.music.cctv15;


import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.cctv.music.cctv15.ui.CircleView;
import com.cctv.music.cctv15.ui.PausableRotateAnimation;
import com.cctv.music.cctv15.ui.RotateView;

import java.io.IOException;

public class MainActivity extends BaseActivity implements RotateView.OnRotateListener{



    private class ViewHolder{
        private View clock;
        private View line;
        private View guang;
        private View kedu;
        private RotateView rotateview;
        private CircleView circleview;

        public ViewHolder() {
            clock = findViewById(R.id.clock);
            line = findViewById(R.id.line);
            guang = findViewById(R.id.guang);
            kedu = findViewById(R.id.kedu);
            rotateview = (RotateView) findViewById(R.id.rotateview);
            circleview = (CircleView) findViewById(R.id.circleview);
        }
    }

    private ViewHolder holder;

    private PausableRotateAnimation lineAnimation;

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMedia();
        holder = new ViewHolder();
        holder.rotateview.setOnRotateListener(this);
        initAnimation();

    }
    private void initAnimation() {
        lineAnimation = new PausableRotateAnimation(0f,358f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        lineAnimation.setInterpolator(new LinearInterpolator());
        lineAnimation.setDuration(1000);
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
    public void onrotate(double rotate) {
        player.seekTo(0);
        player.start();
        ViewCompat.setRotation(holder.clock, (float)rotate);
    }

    @Override
    public void end(RotateView.Direction dir) {
        int rotate = 300;
        if(dir == RotateView.Direction.Up){
            rotate = -rotate;
        }
        ViewCompat.setRotation(holder.circleview,rotate);
        lineAnimation.resume();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
