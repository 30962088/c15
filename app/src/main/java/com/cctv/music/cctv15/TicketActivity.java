package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cctv.music.cctv15.adapter.TicketAdapter;
import com.cctv.music.cctv15.db.OfflineDataField;
import com.cctv.music.cctv15.fragment.RankFragment;
import com.cctv.music.cctv15.model.MyTicket;
import com.cctv.music.cctv15.model.TicketItem;
import com.cctv.music.cctv15.network.ActivistListRequest;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TicketActivity extends BaseActivity implements BaseClient.RequestHandler,View.OnClickListener,AdapterView.OnItemClickListener{


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
    private ActivistListRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        holder = new ViewHolder();
        holder.btn_start.setOnClickListener(this);
        holder.btn_rank.setOnClickListener(this);
        holder.listview.setOnItemClickListener(this);
        holder.count.setOnClickListener(this);


        request = new ActivistListRequest(this,new ActivistListRequest.Params(Preferences.getInstance().getUid()));

        OfflineDataField dataField = OfflineDataField.getOffline(context,request.getOfflineHash());
        if(dataField != null){
            ActivistListRequest.Result result = new Gson().fromJson(dataField.getData(), ActivistListRequest.Result.class);
            initTicket(result);
        }else{
            holder.listview.post(new Runnable() {
                @Override
                public void run() {
                    LoadingPopup.show(context);
                }
            });
        }

        initSidemenu();

    }

    @Override
    protected void onResume() {
        super.onResume();
        request.request(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.count:
                if(Preferences.getInstance().isLogin()){
                    MyTicketActivity.open(context);
                }else{
                    Utils.tip(context,"请先登录");
                    LoginActivity.open(context);
                }

                break;
            case R.id.btn_rank:
                if(menu != null){
                    menu.toggle();
                }
                break;
            case R.id.btn_start:
                if(myTicket != null){
                    JigsawActivity.open(context,myTicket);
                }
                break;
        }
    }

    @Override
    public void onComplete() {
        LoadingPopup.hide(context);
    }

    private MyTicket myTicket;

    @Override
    public void onSuccess(Object object) {
        ActivistListRequest.Result result = (ActivistListRequest.Result)object;

        initTicket(result);


    }

    private void initTicket(ActivistListRequest.Result result){
        myTicket = result;
        holder.count.setText("" + result.getMyticket_count());
        if(!Preferences.getInstance().isLogin()){
            holder.avatar.setImageResource(R.drawable.user_default);
            holder.name.setText("尚未登录");
        }else{
            ImageLoader.getInstance().displayImage(result.getLoginuserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
            holder.name.setText("" + result.getUsername());
        }

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Preferences.getInstance().isLogin()) {
                    LoginActivity.open(context);
                }

            }
        });

        holder.rank.setText("" + result.getMyranking());
        holder.score.setText("" + result.getMyscore());
        if(result.getActivitylist() != null){
            holder.listview.setAdapter(new TicketAdapter(this, result.getActivitylist()));
        }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TicketItem item = (TicketItem) parent.getAdapter().getItem(position);
        TicketDetailActivity.open(this,item,myTicket.getMyscore());
    }


}
