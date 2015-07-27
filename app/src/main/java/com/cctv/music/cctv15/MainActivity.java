package com.cctv.music.cctv15;


import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class MainActivity extends BaseActivity {

    private class ViewHolder{
        private View clock;
        private View line;
        private View guang;
        private View kedu;

        public ViewHolder() {
            clock = findViewById(R.id.clock);
            line = findViewById(R.id.line);
            guang = findViewById(R.id.guang);
            kedu = findViewById(R.id.kedu);
        }
    }

    private ViewHolder holder;

    private RotateAnimation lineAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        holder = new ViewHolder();
        lineAnimation = new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        lineAnimation.setDuration(1000);
        lineAnimation.setFillAfter(true);
        lineAnimation.setRepeatCount(Animation.INFINITE);
        holder.line.startAnimation(lineAnimation);
        holder.clock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
