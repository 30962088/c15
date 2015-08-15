package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cctv.music.cctv15.model.PushInfo;

public class InfoDetailActivity extends BaseActivity{

    public static void open(Context context,PushInfo info) {

        Intent intent = new Intent(context, InfoDetailActivity.class);

        intent.putExtra("info",info);

        context.startActivity(intent);

    }

    private class ViewHolder{
        private TextView title;
        private TextView date;
        private TextView desc;

        public ViewHolder() {

            title = (TextView) findViewById(R.id.title);
            date = (TextView) findViewById(R.id.date);
            desc = (TextView) findViewById(R.id.desc);

        }
    }

    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushInfo info = (PushInfo) getIntent().getSerializableExtra("info");
        setContentView(R.layout.activity_info_detail);
        holder = new ViewHolder();
        holder.title.setText(""+info.getTitle());
        holder.date.setText("发布时间："+info.getDate());
        holder.desc.setText(""+info.getDescription());
    }



}
