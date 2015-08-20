package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cctv.music.cctv15.model.TicketItem;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.ChangeActivityTicketRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.RegexUtils;
import com.cctv.music.cctv15.utils.Utils;

public class ActivityTicketFill extends BaseActivity implements View.OnClickListener{

    public static void open(Context context,TicketItem ticket) {

        Intent intent = new Intent(context, ActivityTicketFill.class);

        intent.putExtra("ticket",ticket);

        context.startActivity(intent);

    }

    private class ViewHolder{
        private TextView title;
        private TextView time;
        private TextView address;
        private EditText name;
        private EditText phone;

        public ViewHolder() {
            title = (TextView) findViewById(R.id.title);
            time = (TextView) findViewById(R.id.time);
            address = (TextView) findViewById(R.id.address);
            name = (EditText) findViewById(R.id.name);
            phone = (EditText) findViewById(R.id.phone);
        }
    }

    private ViewHolder holder;

    private TicketItem ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ticket = (TicketItem) getIntent().getSerializableExtra("ticket");
        setContentView(R.layout.activity_ticket_fill);
        holder = new ViewHolder();
        holder.title.setText(ticket.getTitle());
        holder.time.setText("时间："+ticket.getDate());
        holder.address.setText("地点："+ticket.getAddress());
        findViewById(R.id.btn_ok).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                onOK();
                break;
        }
    }

    private void onOK() {
        String phone = holder.phone.getText().toString();
        String name = holder.name.getText().toString();
        if(TextUtils.isEmpty(name)){
            Utils.tip(context,"请输入姓名");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            Utils.tip(context,"请输入手机号");
            return;
        }
        if(!RegexUtils.checkPhone(phone)){
            Utils.tip(context,"请输入正确的手机号");
            return;
        }

        ChangeActivityTicketRequest request = new ChangeActivityTicketRequest(context,new ChangeActivityTicketRequest.Params(""+ticket.getAid(),phone,name, Preferences.getInstance().getUid()));


        LoadingPopup.show(context);

        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                Utils.tip(context,"兑换成功");
                MyTicketActivity.open(context);
                finish();
            }

            @Override
            public void onError(int error, String msg) {
                switch (error){
                    case 1026:
                        Utils.tip(context,"您已兑换过该活动门票");
                        break;
                    default:
                        Utils.tip(context,"兑换失败");
                        break;
                }
            }
        });

    }

}
