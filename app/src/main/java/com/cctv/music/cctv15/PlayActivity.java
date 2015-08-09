package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.model.Song;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.GetSongScoreRequest;
import com.cctv.music.cctv15.network.UpdateSongScoreRequest;
import com.cctv.music.cctv15.ui.MyRatingbar;
import com.cctv.music.cctv15.ui.PercentView;
import com.cctv.music.cctv15.utils.AnimUtils;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MyRatingbar.OnRateListener {

    public static class Model implements Serializable{
        private int index;
        private List<Song> list;

        public Model(int index, List<Song> list) {
            this.index = index;
            this.list = list;
        }

        public Song getCurrent(){
            return list.get(index);
        }

    }

    public static void open(Context context,Model model) {

        Intent intent = new Intent(context, PlayActivity.class);

        intent.putExtra("model",model);

        context.startActivity(intent);

    }

    private class ViewHolder {
        private View btn_play;
        private View btn_next;
        private View btn_prev;
        private View btn_star;
        private ImageView img;
        private TextView singer;
        private TextView songname;
        private TextView label;
        private PercentView percent;
        private View container;
        private MyRatingbar ratebar;

        public ViewHolder() {
            img = (ImageView) findViewById(R.id.img);
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

    private Model model;

    private ViewHolder holder;

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = (Model) getIntent().getSerializableExtra("model");
        setContentView(R.layout.activity_play);
        holder = new ViewHolder();
        initEvent();
        initSong(model.index);
    }

    private void initScore() {
        holder.ratebar.setRate(0);
        holder.btn_star.setSelected(false);
        holder.btn_star.setEnabled(false);
        if(Preferences.getInstance().isLogin()){
            GetSongScoreRequest request = new GetSongScoreRequest(this,new GetSongScoreRequest.Params(Preferences.getInstance().getUid(),model.getCurrent().getSid()));
            request.request(new BaseClient.RequestHandler() {
                @Override
                public void onComplete() {

                }

                @Override
                public void onSuccess(Object object) {
                    holder.btn_star.setEnabled(true);
                    GetSongScoreRequest.Result result = (GetSongScoreRequest.Result)object;
                    if(result.getScore()>0){

                        holder.btn_star.setSelected(true);
                        holder.ratebar.setRate(result.getScore()/20);
                    }
                }

                @Override
                public void onError(int error, String msg) {

                }
            });
        }

    }

    private void initSong(int index) {
        Song song = model.list.get(index);
        holder.singer.setText(song.getSingername());
        holder.songname.setText(song.getSongname());
        ImageLoader.getInstance().displayImage(song.getSurfaceurl(), holder.img, DisplayOptions.IMG.getOptions());
        start(song.getSongurl());
        initScore();
    }

    private void stopTimer() {
        if(timer != null){
            timer.cancel();
            timer = null;
        }

    }

    @Override
    public void onrate(final int rate) {


        if(holder.btn_star.isSelected()){
            Utils.tip(this,"您已经对这首歌进行了评分");
            return;
        }

        UpdateSongScoreRequest request = new UpdateSongScoreRequest(this,new UpdateSongScoreRequest.Params(rate*20,model.getCurrent().getSid(),Preferences.getInstance().getUid()));

        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                Utils.tip(PlayActivity.this,"评分成功");
                holder.btn_star.setSelected(true);
                holder.ratebar.setRate(rate);
            }

            @Override
            public void onError(int error, String msg) {

            }
        });


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

    private boolean isPrepared = false;

    @Override
    public void onCompletion(MediaPlayer mp) {
        holder.btn_play.setSelected(false);
        stopTimer();
        seekTo(0);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        isPrepared = true;
        mp.seekTo(0);
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
        releasePlay();
        initPlayer();
        stopTimer();
        holder.btn_play.setSelected(false);
        holder.label.setText("00:00" + " / " + "00:00");
        holder.percent.setPercent(0);
        try {
            player.setDataSource(url);
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void releasePlay(){
        isPrepared = false;
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        stopTimer();
        super.onDestroy();
        releasePlay();
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
        if(!Preferences.getInstance().isLogin()){
            Utils.tip(this,"系统检测到您还没有登录");
            LoginActivity.open(this);
            return;
        }
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
        if(model.index<model.list.size()-1){
            model.index++;
            initSong(model.index);
        }

    }

    private void onprev() {
        if(model.index>0){
            model.index--;
            initSong(model.index);
        }
    }

    private void updateTimer() {
        player.getDuration();
        holder.label.setText(Utils.formatTimer(player.getCurrentPosition()) + " / " + Utils.formatTimer(player.getDuration()));
        int percent = (int) ((double) player.getCurrentPosition() / (double) player.getDuration() * 100);
        holder.percent.setPercent(percent);
    }

    private void onplay() {
        if(isPrepared){
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
