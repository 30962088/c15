package com.cctv.music.cctv15.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

public class BaseFragment extends Fragment implements Serializable{

    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

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
