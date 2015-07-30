package com.cctv.music.cctv15;


import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.cctv.music.cctv15.ui.PercentView;
import com.cctv.music.cctv15.utils.Utils;

public class PlayActivity extends BaseActivity implements View.OnClickListener,View.OnTouchListener{




    private class ViewHolder{
        private View btn_play;
        private View btn_next;
        private View btn_prev;
        private View btn_star;
        private TextView singer;
        private TextView songname;
        private TextView label;
        private PercentView percent;
        private View container;

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

        }
    }

    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        holder = new ViewHolder();
        initEvent();



    }

    private void initEvent() {

        holder.btn_play.setOnClickListener(this);
        holder.btn_prev.setOnClickListener(this);
        holder.btn_next.setOnClickListener(this);
        holder.btn_star.setOnClickListener(this);
        holder.container.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean res = true;
        switch (v.getId()){
            case R.id.container:
                res = onContainerTouch(v,event);
                break;
        }
        return res;
    }

    public boolean onContainerTouch(View v,MotionEvent event){
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
       calculate(event);
    }

    private void onContainerMove(View v, MotionEvent event) {
        calculate(event);
    }

    private void calculate(MotionEvent event){
        float dw = event.getX()-last.x,dh = event.getY()-last.y;
        double tan = Math.atan2(dh,dw);
        percent = (int)((tan*(180 / Math.PI) *2)/360*100);
        if(percent<0){
            percent = 180-percent;
        }
        if(percent>100){
            percent=0;
        }
        holder.percent.setPercent(percent);
        Log.d("zzm","x:"+dw+",y:"+dh+",percent:"+percent);
    }

    private int percent = 0;

    private Point last;

    private boolean onContainerDown(View v, MotionEvent event) {
        last = new Point(v.getWidth()/2,0);
        boolean res = false;
        int dis = Utils.dpToPx(this,40);
        float viewX = event.getX() - v.getLeft();
        float viewY = event.getY() - v.getTop();
        if(viewX < dis || v.getWidth()-viewX<dis){
            res = true;
        }
        if(viewY < dis || v.getHeight()-viewY<dis){
            res = true;
        }

        return res;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

    private void onstar() {
    }

    private void onnext() {

    }

    private void onprev() {

    }

    private void onplay() {

    }

}
