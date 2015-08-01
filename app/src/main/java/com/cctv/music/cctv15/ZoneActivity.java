package com.cctv.music.cctv15;

import android.os.Bundle;
import android.view.View;

public class ZoneActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.login_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.login_btn:
                onlogin();
                break;
        }

    }

    private void onlogin() {

    }
}
