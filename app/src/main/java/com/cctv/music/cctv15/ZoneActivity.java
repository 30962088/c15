package com.cctv.music.cctv15;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cctv.music.cctv15.utils.CacheManager;
import com.cctv.music.cctv15.utils.Preferences;

public class ZoneActivity extends BaseActivity implements View.OnClickListener,BaseActivity.OnWeiboBindingListener{




    private class ViewHolder{
        private View btn_login;
        private View btn_about;
        private TextView cachesize;
        private TextView binding;


        public ViewHolder() {
            btn_login = findViewById(R.id.btn_login);
            btn_about = findViewById(R.id.btn_about);
            cachesize = (TextView) findViewById(R.id.cachesize);
            binding = (TextView) findViewById(R.id.binding);
        }
    }


    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone);
        holder = new ViewHolder();
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_weibo).setOnClickListener(this);
        holder.btn_login.setOnClickListener(this);
        holder.btn_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_login:
                LoginActivity.open(this);
                break;
            case R.id.btn_about:
                AboutActivity.open(this);
                break;
            case R.id.btn_clear:
                CacheManager.clearCache(this,null);
                getCacheSize();
                break;
            case R.id.btn_weibo:
                bindingWeibo(this);
                break;
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        getCacheSize();
        checkBinding();
    }

    private void checkBinding() {
        String accessToken = Preferences.getInstance().getWeiboAccessToken();
        if(accessToken == null){
            holder.binding.setText("未绑定");
            holder.binding.setSelected(false);
        }else{
            holder.binding.setText("已绑定");
            holder.binding.setSelected(true);
        }
    }


    @Override
    public void onWeiboBinding() {
        onResume();
    }

    private void getCacheSize() {

        new AsyncTask<Context, Void, Long>() {

            @Override
            protected Long doInBackground(Context... params) {
                // TODO Auto-generated method stub
                return CacheManager.folderSize(params[0].getCacheDir())
                        + CacheManager.folderSize(params[0]
                        .getExternalCacheDir());
            }

            @Override
            protected void onPostExecute(Long result) {
                holder.cachesize.setText(Math.round(result / 1024 / 1024 * 100)
                        / 100.0 + "M");
            }

        }.execute(this);
    }
}