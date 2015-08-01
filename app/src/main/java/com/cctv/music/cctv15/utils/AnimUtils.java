package com.cctv.music.cctv15.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class AnimUtils {

    public static void expandOrCollapse(final View v,String exp_or_colpse) {
        TranslateAnimation anim = null;
        if (exp_or_colpse.equals("expand"))
        {
            anim = new TranslateAnimation(0.0f, 0.0f, -v.getHeight(), 0.0f);
            v.setVisibility(View.VISIBLE);
        }
        else{
            anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, -v.getHeight());
            Animation.AnimationListener collapselistener= new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }
            };

            anim.setAnimationListener(collapselistener);
        }

        // To Collapse
        //

        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator(0.5f));
        v.startAnimation(anim);
    }

}
