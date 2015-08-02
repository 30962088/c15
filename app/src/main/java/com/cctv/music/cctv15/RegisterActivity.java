package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cctv.music.cctv15.fragment.SigninFragment;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    public static void open(Context context) {

        Intent intent = new Intent(context, RegisterActivity.class);

        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        SigninFragment.newInstance())
                .addToBackStack("singin").commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

        }
    }




}
