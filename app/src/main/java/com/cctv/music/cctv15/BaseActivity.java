package com.cctv.music.cctv15;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

    public static interface OnWeiboBindingListener{
        public void onWeiboBinding();
    }

    private static final int ACTION_REQUEST_BINDING_WEIBO = 7;

    private OnWeiboBindingListener onWeiboBindingListener;

    public void bindingWeibo(OnWeiboBindingListener onWeiboBindingListener){
        this.onWeiboBindingListener = onWeiboBindingListener;
        Intent intent = new Intent(this, BindingWeiboActivity.class);
        startActivityForResult(intent, ACTION_REQUEST_BINDING_WEIBO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_REQUEST_BINDING_WEIBO:
                if (resultCode == Activity.RESULT_OK) {
                    if(onWeiboBindingListener != null){
                        onWeiboBindingListener.onWeiboBinding();
                    }
                }
                break;
        }
    }
}
