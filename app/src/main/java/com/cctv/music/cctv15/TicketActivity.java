package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cctv.music.cctv15.adapter.TicketAdapter;
import com.cctv.music.cctv15.fragment.RankFragment;
import com.cctv.music.cctv15.network.ActivistListRequest;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TicketActivity extends BaseActivity implements BaseClient.RequestHandler,View.OnClickListener{


    public static void open(Context context) {

        Intent intent = new Intent(context, TicketActivity.class);

        context.startActivity(intent);

    }



    private class ViewHolder{

        private TextView count;

        private View btn_start;

        private ImageView avatar;

        private TextView name;

        private TextView score;

        private TextView rank;

        private View btn_rank;

        private ListView listview;

        public ViewHolder() {

            count = (TextView) findViewById(R.id.count);
            btn_start = findViewById(R.id.btn_start);
            avatar = (ImageView) findViewById(R.id.avatar);
            name = (TextView) findViewById(R.id.name);
            score = (TextView) findViewById(R.id.score);
            rank = (TextView) findViewById(R.id.rank);
            btn_rank = findViewById(R.id.btn_rank);
            listview = (ListView) findViewById(R.id.listview);

        }
    }

    private ViewHolder holder;

    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        findViewById(R.id.back).setOnClickListener(this);
        holder = new ViewHolder();
        holder.btn_rank.setOnClickListener(this);
        ActivistListRequest request = new ActivistListRequest(this,new ActivistListRequest.Params(Preferences.getInstance().getUid()));
        request.request(this);
        initSidemenu();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rank:
                if(menu != null){
                    menu.toggle();
                }
                break;
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
        ActivistListRequest.Result result = (ActivistListRequest.Result)object;
        holder.count.setText("" + result.getMyticket_count());
        ImageLoader.getInstance().displayImage(result.getLoginuserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
        holder.name.setText("" + result.getUsername());
        holder.rank.setText("" + result.getMyranking());
        holder.score.setText("" + result.getMyscore());
        holder.listview.setAdapter(new TicketAdapter(this, result.getActivitylist()));


    }

    private void initSidemenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sidemenu);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sidemenu, RankFragment.newInstance()).commit();
    }

    @Override
    public void onError(int error, String msg) {

    }


}
