package com.cctv.music.cctv15.fragment;


import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

public class BaseFragment extends Fragment implements Serializable{

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("fragment:" + getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("fragment:"+getClass().getSimpleName() );
    }

}
