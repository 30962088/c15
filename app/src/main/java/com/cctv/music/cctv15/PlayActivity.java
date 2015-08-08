package com.cctv.music.cctv15;


import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.cctv.music.cctv15.ui.MyRatingbar;
import com.cctv.music.cctv15.ui.PercentView;
import com.cctv.music.cctv15.utils.AnimUtils;
import com.cctv.music.cctv15.utils.Utils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MyRatingbar.OnRateListener {


    private class ViewHolder {
        private View btn_play;
        private View btn_next;
        private View btn_prev;
        private View btn_star;
        private TextView singer;
        private TextView songname;
        private TextView label;
        private PercentView percent;
        private View container;
        private MyRatingbar ratebar;

        public ViewHolder() {
            container = findViewById(R.id.container);
            btn_play = findViewById(R.id.btn_play);
            btn_next = findViewById(R.id.btn_next);
            btn_prev = findViewById(R.id.btn_prev);
            btn_star = findViewById(R.id.btn_star);
            singer = (TextView) findViewById(R.id.singer);
            songname = (TextView) findViewById(R.id.songname);
            label = (TextView) findViewById(R.id.label);
            percent = (PercentView) findViewById(R.id.percent);
            ratebar = (MyRatingbar) findViewById(R.id.ratebar);
        }
    }

    private Timer timer;


    private ViewHolder holder;

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        holder = new ViewHolder();
        initEvent();
        initPlayer();
        start("test.mp3");
    }

    private void stopTimer() {
        timer.cancel();
        timer = null;
    }

    @Override
    public void onrate(int rate) {
        holder.btn_star.setSelected(true);
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTimer();
                    }
                });

            }
        }, 0, 1000);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        holder.btn_play.setSelected(false);
        stopTimer();
        seekTo(0);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        holder.label.setText(Utils.formatTimer(mp.getCurrentPosition()) + " / " + Utils.formatTimer(mp.getDuration()));
        onplay();
    }

    private void initPlayer() {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
    }

    private void start(String url) {
        try {
            AssetFileDescriptor descriptor = getAssets().openFd(url);
            player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        stopTimer();
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    private void initEvent() {

        holder.btn_play.setOnClickListener(this);
        holder.btn_prev.setOnClickListener(this);
        holder.btn_next.setOnClickListener(this);
        holder.btn_star.setOnClickListener(this);
        holder.container.setOnTouchListener(this);
        holder.ratebar.setOnRateListener(this);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean res = true;
        switch (v.getId()) {
            case R.id.container:
                res = onContainerTouch(v, event);
                break;
        }
        return res;
    }

    public boolean onContainerTouch(View v, MotionEvent event) {
        boolean res = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                res = onContainerDown(v, event);
                break;
            case MotionEvent.ACTION_MOVE:
                onContainerMove(v, event);
                break;
            case MotionEvent.ACTION_UP:
                onContainerUp(v, event);
                break;
        }
        return res;
    }

    private void onContainerUp(View v, MotionEvent event) {
        int percent = calculate(event);
        seekTo(percent);
    }

    private void onContainerMove(View v, MotionEvent event) {
        int percent = calculate(event);
        holder.percent.setPercent(percent);
    }

    private int calculate(MotionEvent event) {

        double angle = calcRotationAngleInDegrees(new PointF(last.x, last.y),new PointF(event.getX(),event.getY()));

        percent = (int)(angle/360*100);
        if (percent < 0) {
            percent = 100 - percent;
        }
        if (percent > 100) {
            percent = 0;
        }
        return percent;
    }

    private void seekTo(int percent) {
        double p = (double) percent / (double) 100;
        player.seekTo((int) (player.getDuration() * p));
        updateTimer();
    }

    private int percent = 0;

    private Point last;

    private boolean onContainerDown(View v, MotionEvent event) {
        int dis = Utils.dpToPx(this, 40);
        last = new Point(v.getLeft()+v.getWidth() / 2, v.getTop()+v.getHeight()/2);
        boolean res = false;
        float viewX = event.getX() - v.getLeft();
        float viewY = event.getY() - v.getTop();
        if (viewX < dis || v.getWidth() - viewX < dis) {
            res = true;
        }
        if (viewY < dis || v.getHeight() - viewY < dis) {
            res = true;
        }

        return res;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_play:
                onplay();
                break;
            case R.id.btn_prev:
                onprev();
                break;
            case R.id.btn_next:
                onnext();
                break;
            case R.id.btn_star:
                onstar();
                break;
        }
    }

    private boolean starShow = false;

    private void onstar() {
        View ratebar = holder.ratebar;
        if (!starShow) {
            AnimUtils.expandOrCollapse(ratebar, "expand");
            starShow = true;
        } else {
            AnimUtils.expandOrCollapse(ratebar, "collapse");
            starShow = false;
        }
    }

    private void onnext() {
        start("test.mp3");
    }

    private void onprev() {
        start("test.mp3");
    }

    private void updateTimer() {
        player.getDuration();
        holder.label.setText(Utils.formatTimer(player.getCurrentPosition()) + " / " + Utils.formatTimer(player.getDuration()));
        int percent = (int) ((double) player.getCurrentPosition() / (double) player.getDuration() * 100);
        holder.percent.setPercent(percent);
    }

    private void onplay() {
        boolean selected = holder.btn_play.isSelected();
        if (!selected) {
            player.start();
            startTimer();
        } else {
            player.pause();
            stopTimer();

        }
        holder.btn_play.setSelected(!selected);
    }

    public double calcRotationAngleInDegrees(PointF centerPt, PointF targetPt){
        double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);
        theta += Math.PI/2.0;
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

}
