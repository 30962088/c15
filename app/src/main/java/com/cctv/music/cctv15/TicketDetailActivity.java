package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.model.TicketItem;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.TicketDetailRequest;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TicketDetailActivity extends BaseActivity implements BaseClient.RequestHandler,View.OnClickListener{


    public static void open(Context context,TicketItem item,int score) {

        Intent intent = new Intent(context, TicketDetailActivity.class);

        intent.putExtra("item",item);

        intent.putExtra("score",score);

        context.startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change:
                onchange();
                break;
        }
    }

    private void onchange() {

        if(!Preferences.getInstance().isLogin()){
            Utils.tip(context,"请先登录");
            LoginActivity.open(context);
            return;
        }

        if(score < item.getConvert_score()){
            Utils.tip(context,"您没有足够兑换的积分");
            return;
        }

        ActivityTicketFill.open(context,item);

    }


    private class ViewHolder{
        private TextView title;
        private ImageView img;
        private TextView desc;
        private TextView score;
        private TextView count;
        private TextView day;

        public ViewHolder() {

            title = (TextView) findViewById(R.id.title);
            img = (ImageView) findViewById(R.id.img);
            desc = (TextView) findViewById(R.id.desc);
            score = (TextView) findViewById(R.id.score);
            count = (TextView) findViewById(R.id.count);
            day = (TextView) findViewById(R.id.day);

        }
    }

    private ViewHolder holder;

    private TicketItem item;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (TicketItem) getIntent().getSerializableExtra("item");
        score = getIntent().getIntExtra("score",0);
        setContentView(R.layout.activity_ticket_detail);
        findViewById(R.id.btn_change).setOnClickListener(this);
        holder = new ViewHolder();
        TicketDetailRequest request = new TicketDetailRequest(this,new TicketDetailRequest.Params(item.getAid()));
        request.request(this);
    }



    @Override
    public void onComplete() {

    }

    @Override
    public void onSuccess(Object object) {
        TicketDetailRequest.Result result = (TicketDetailRequest.Result)object;
        ImageLoader.getInstance().displayImage(item.getImgurl(), holder.img, DisplayOptions.IMG.getOptions());
        holder.title.setText(result.getTitle());
        holder.count.setText(""+result.getSurplusnumber()+"张");
        holder.day.setText(""+result.getDiffdays()+"天");
        holder.desc.setText(""+result.getContents());
        holder.score.setText(""+result.getConvert_score());
    }

    @Override
    public void onError(int error, String msg) {

    }



}
