package com.cctv.music.cctv15;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cctv.music.cctv15.adapter.AppAdapter;
import com.cctv.music.cctv15.model.AppItem;
import com.cctv.music.cctv15.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends BaseActivity implements View.OnClickListener,AppAdapter.OnDownloadListener{

    public static void open(Context context) {

        Intent intent = new Intent(context, AppListActivity.class);

        context.startActivity(intent);

    }

    private ListView listView;

    private AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);
        findViewById(R.id.back).setOnClickListener(this);
        List<AppItem> list = new ArrayList<>();
        list.add(new AppItem("assets://logo_xiqu.png", "央视戏曲", "中央电视台戏曲频道官方客户端","com.cctv.xiqu.android"));
        adapter = new AppAdapter(this,list,this);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void ondownload(AppItem item) {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id="+item.getPackagename()));
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Utils.tip(this,"请安装安卓市场app");
        }

    }
}
