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
import com.nostra13.universalimageloader.core.ImageLoader;

public class TicketDetailActivity extends BaseActivity implements View.OnClickListener,BaseClient.RequestHandler{


    public static void open(Context context,TicketItem item) {

        Intent intent = new Intent(context, TicketDetailActivity.class);

        intent.putExtra("item",item);

        context.startActivity(intent);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (TicketItem) getIntent().getSerializableExtra("item");
        setContentView(R.layout.activity_ticket_detail);
        holder = new ViewHolder();
        findViewById(R.id.back).setOnClickListener(this);
        TicketDetailRequest request = new TicketDetailRequest(this,new TicketDetailRequest.Params(item.getAid()));
        request.request(this);
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
    public void onComplete() {

    }

    @Override
    public void onSuccess(Object object) {
        TicketDetailRequest.Result result = (TicketDetailRequest.Result)object;
        ImageLoader.getInstance().displayImage(item.getImgurl(), holder.img, DisplayOptions.IMG.getOptions());
        holder.title.setText(result.getTitle());
        holder.count.setText(""+result.getSurplusnumber());
        holder.day.setText(""+result.getDiffdays());
        holder.desc.setText(""+result.getContents());
        holder.score.setText(""+result.getConvert_score());
    }

    @Override
    public void onError(int error, String msg) {

    }



}
