package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cctv.music.cctv15.fragment.FillUserFragment;


public class FillUserActivity extends BaseActivity {

    public static void open(Context context, FillUserFragment.Model model) {

        Intent intent = new Intent(context, FillUserActivity.class);

        intent.putExtra("model", model);

        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filluser);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FillUserFragment.newInstance((FillUserFragment.Model) getIntent().getSerializableExtra("model"))).commit();
    }

}
