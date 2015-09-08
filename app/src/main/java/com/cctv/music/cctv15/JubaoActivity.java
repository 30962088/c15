package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cctv.music.cctv15.model.Comment;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.InsertReportRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.RegexUtils;
import com.cctv.music.cctv15.utils.Utils;

public class JubaoActivity extends BaseActivity implements View.OnClickListener{

    public static void open(Context context,Comment comment) {

        Intent intent = new Intent(context, JubaoActivity.class);

        intent.putExtra("comment",comment);

        context.startActivity(intent);

    }

    private class ViewHolder{

        private EditText name;

        private EditText phone;

        private EditText content;

        public ViewHolder() {
            name = (EditText) findViewById(R.id.name);
            phone = (EditText) findViewById(R.id.phone);
            content = (EditText) findViewById(R.id.content);
        }
    }



    private Comment comment;

    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comment = (Comment) getIntent().getSerializableExtra("comment");
        setContentView(R.layout.activity_jubao);
        holder = new ViewHolder();
        if(Preferences.getInstance().isLogin()){
            holder.name.setVisibility(View.GONE);
            holder.phone.setVisibility(View.GONE);
        }
        findViewById(R.id.btn_send).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                onsend();
                break;
        }
    }

    private void onsend() {

        String content = holder.content.getText().toString();
        if(TextUtils.isEmpty(content)){
            Utils.tip(this,"请输入要发送的内容");
            return;
        }

        InsertReportRequest request;

        if(!Preferences.getInstance().isLogin()){

            String name = holder.name.getText().toString();

            String phone = holder.phone.getText().toString();

            if(TextUtils.isEmpty(name)){
                Utils.tip(this,"请输入姓名");
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                Utils.tip(this, "请输入手机号码");
                return;
            }
            if (!RegexUtils.checkPhone(phone)) {
                Utils.tip(this, "手机号码格式错误");
                return;
            }


            request = new InsertReportRequest(this,new InsertReportRequest.Params(""+comment.getCommentid(),name,phone,content));

        }else{

            request = new InsertReportRequest(this,new InsertReportRequest.Params(""+comment.getCommentid(),content,Preferences.getInstance().getUid()));

        }


        LoadingPopup.show(this);

        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                Utils.tip(context,"发送成功");
                finish();
            }

            @Override
            public void onError(int error, String msg) {

            }
        });

    }
}
