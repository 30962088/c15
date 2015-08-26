package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.model.ClientUser;
import com.cctv.music.cctv15.model.PushInfo;
import com.cctv.music.cctv15.network.ActivistListRequest;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.GetClientUserInfoRequest;
import com.cctv.music.cctv15.network.PushInfoRequest;
import com.cctv.music.cctv15.network.VoteRequest;
import com.cctv.music.cctv15.ui.VoteItem2;
import com.cctv.music.cctv15.utils.CacheManager;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class ZoneActivity extends BaseActivity implements View.OnClickListener, BaseActivity.OnWeiboBindingListener {

    public static void open(Context context) {

        Intent intent = new Intent(context, ZoneActivity.class);

        context.startActivity(intent);

    }

    public static String Action_Login = "LOGIN";

    public static void login(Context context) {

        Intent intent = new Intent(context, ZoneActivity.class);

        intent.setAction(Action_Login);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(intent);

    }


    public void onCheckedChanged(View buttonView) {
        buttonView.setSelected(!buttonView.isSelected());
        boolean val = buttonView.isSelected();
        if(buttonView == holder.newsSwitch){
            Preferences.getInstance().setNewsPush(val);
        }else{
            Preferences.getInstance().setVoice(val);
        }
    }


    private class ViewHolder {
        private View btn_avatar;
        private View btn_about;
        private TextView cachesize;
        private TextView binding;
        private TextView count_push;
        private ImageView avatar;
        private TextView username;
        private VoteItem2 vote;
        private TextView rank;
        private TextView score;
        private TextView ticket;
        private View voiceSwitch,newsSwitch;




        public ViewHolder() {
            btn_avatar = findViewById(R.id.btn_avatar);
            btn_about = findViewById(R.id.btn_about);
            cachesize = (TextView) findViewById(R.id.cachesize);
            binding = (TextView) findViewById(R.id.binding);
            count_push = (TextView) findViewById(R.id.count_push);
            avatar = (ImageView) findViewById(R.id.avatar);
            username = (TextView) findViewById(R.id.username);
            vote = (VoteItem2) findViewById(R.id.vote);
            rank = (TextView) findViewById(R.id.rank);
            score = (TextView) findViewById(R.id.score);
            ticket = (TextView) findViewById(R.id.ticket);
            voiceSwitch =  findViewById(R.id.voice);
            newsSwitch =  findViewById(R.id.news);
        }
    }


    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone);
        holder = new ViewHolder();
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_weibo).setOnClickListener(this);
        findViewById(R.id.btn_app).setOnClickListener(this);
        findViewById(R.id.btn_pushinfo).setOnClickListener(this);
        findViewById(R.id.btn_account).setOnClickListener(this);
        holder.btn_avatar.setOnClickListener(this);
        holder.btn_about.setOnClickListener(this);
        findViewById(R.id.newswraper).setOnClickListener(this);
        findViewById(R.id.voicewraper).setOnClickListener(this);
        fillVote();
    }

    private void fillVote() {
        VoteRequest request = new VoteRequest(context,new VoteRequest.Params(1,1));
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                VoteRequest.Result result = (VoteRequest.Result) object;
                if (result.getVotelist() != null && result.getVotelist().size() == 1) {
                    holder.vote.setVisibility(View.VISIBLE);
                    holder.vote.setVote(result.getVotelist().get(0));
                } else {
                    holder.vote.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_account:
                if(Preferences.getInstance().isLogin()){
                    AccountActivity.open(this);
                }else{
                    LoginActivity.open(this);
                }

                break;
            case R.id.btn_avatar:
                onavatar();
                break;
            case R.id.btn_about:
                AboutActivity.open(this);
                break;
            case R.id.btn_clear:
                CacheManager.clearCache(this, null);
                getCacheSize();
                break;
            case R.id.btn_weibo:
                bindingWeibo(this);
                break;
            case R.id.btn_app:
                AppListActivity.open(this);
                break;
            case R.id.btn_pushinfo:
                PushInfoActivity.open(this, infoList);
                break;
            case R.id.voicewraper:
                onCheckedChanged(holder.voiceSwitch);
                break;
            case R.id.newswraper:
                onCheckedChanged(holder.newsSwitch);
                break;
        }

    }

    private void onavatar() {
        if (Preferences.getInstance().isLogin()) {
            AccountActivity.open(this);
        } else {
            LoginActivity.open(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCacheSize();
        checkBinding();
        checkPushInfo();
        fillUser();
        checkTicket();
        holder.voiceSwitch.setSelected(Preferences.getInstance().getVoice());
        holder.newsSwitch.setSelected(Preferences.getInstance().getNewsPush());

    }

    private void checkTicket() {
        if(Preferences.getInstance().isLogin()){
            ActivistListRequest request = new ActivistListRequest(context,new ActivistListRequest.Params(Preferences.getInstance().getUid()));
            request.request(new BaseClient.RequestHandler() {
                @Override
                public void onComplete() {

                }

                @Override
                public void onSuccess(Object object) {
                    ActivistListRequest.Result result = (ActivistListRequest.Result)object;
                    holder.score.setText(""+result.getMyscore());
                    holder.rank.setText(""+result.getMyranking());
                    holder.ticket.setText(""+result.getMyticket_count());
                }

                @Override
                public void onError(int error, String msg) {

                }
            });
        }else{
            holder.score.setText("0");
            holder.rank.setText("0");
            holder.ticket.setText("0");
        }

    }

    private void fillUser() {
        if (Preferences.getInstance().isLogin()) {


            GetClientUserInfoRequest request = new GetClientUserInfoRequest(this, new GetClientUserInfoRequest.Params(Preferences.getInstance().getUid()));
            request.request(new BaseClient.RequestHandler() {
                @Override
                public void onComplete() {

                }

                @Override
                public void onSuccess(Object object) {
                    GetClientUserInfoRequest.Result result = (GetClientUserInfoRequest.Result) object;
                    ClientUser user = result.getModel();
                    holder.username.setText(user.getNickname());
                    ImageLoader.getInstance().displayImage(user.getUserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
                }

                @Override
                public void onError(int error, String msg) {

                }
            });
        }else {
            holder.avatar.setImageResource(R.drawable.user_default);
            holder.username.setText("");
        }
    }

    private ArrayList<PushInfo> infoList;

    private void checkPushInfo() {
        new PushInfoRequest(this, new PushInfoRequest.Params(1, 100000)).request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                PushInfoRequest.Result result = (PushInfoRequest.Result) object;
                holder.count_push.setText(result.getNewCount(ZoneActivity.this) + "条新消息");
                infoList = (ArrayList) result.getPushinfolist();
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
    }

    private void checkBinding() {
        String accessToken = Preferences.getInstance().getWeiboAccessToken();
        if (accessToken == null) {
            holder.binding.setText("未绑定");
            holder.binding.setSelected(false);
        } else {
            holder.binding.setText("已绑定");
            holder.binding.setSelected(true);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction() == Action_Login) {
            AccountActivity.open(this);
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
