package com.cctv.music.cctv15.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cctv.music.cctv15.FillUserActivity;
import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Sex;
import com.cctv.music.cctv15.model.UserType;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.PhoneCodeRequest;
import com.cctv.music.cctv15.network.PhoneCodeVerifyRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.RegexUtils;
import com.cctv.music.cctv15.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class SigninFragment extends BaseFragment implements View.OnClickListener{


    public static SigninFragment newInstance() {
        return new SigninFragment();
    }

    private class ViewHolder{
        private EditText phone;
        private EditText verifycode;
        private EditText password;
        private EditText repassword;
        private TextView btn_send;

        public ViewHolder(View view) {
            phone = (EditText) view.findViewById(R.id.phone);
            verifycode = (EditText) view.findViewById(R.id.verifycode);
            password = (EditText) view.findViewById(R.id.password);
            repassword = (EditText) view.findViewById(R.id.repassword);
            btn_send = (TextView) view.findViewById(R.id.btn_send);
        }
    }

    private ViewHolder holder;

    private boolean verify;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_singin,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        holder = new ViewHolder(view);
        holder.btn_send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                onconfirm();
                break;
            case R.id.btn_send:
                onsend();
                break;
        }
    }

    private void onsend() {
        String phone = holder.phone.getText().toString();


        if (TextUtils.isEmpty(phone)) {
            Utils.tip(getActivity(), "手机号不能为空");
            return;
        }
        if (!RegexUtils.checkPhone(phone)) {
            Utils.tip(getActivity(), "手机号格式错误");
            return;
        }



        PhoneCodeRequest request = new PhoneCodeRequest(getActivity(), new PhoneCodeRequest.Params(phone));
        LoadingPopup.show(getActivity());
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(getActivity());
            }

            @Override
            public void onSuccess(Object object) {
                startTimer();
                verify = true;
            }

            @Override
            public void onError(int error, String msg) {
                switch (error) {
                    case 1015:
                        Utils.tip(getActivity(), "手机已经注册过了");
                        break;
                    default:
                        Utils.tip(getActivity(), "发送验证码失败");
                        break;
                }
            }
        });
    }

    public void onconfirm() {
        final String phone = holder.phone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            Utils.tip(getActivity(), "请输入手机号");
            return;
        }
        if (!RegexUtils.checkPhone(phone)) {
            Utils.tip(getActivity(), "手机号格式错误");
            return;
        }

        String verify = holder.verifycode.getText().toString();
        if(TextUtils.isEmpty(verify)){
            Utils.tip(getActivity(), "请输入验证码");
            return;
        }
        final String password = holder.password.getText().toString();
        if(TextUtils.isEmpty(password)){
            Utils.tip(getActivity(), "密码不能为空");
            return;
        }
        String repassword = holder.repassword.getText().toString();
        if(TextUtils.isEmpty(repassword)){
            Utils.tip(getActivity(), "请确认密码");
            return;
        }
        if(!TextUtils.equals(password, repassword)){
            Utils.tip(getActivity(), "两次密码输入不一致");
            return;
        }
        if(!this.verify){
            Utils.tip(getActivity(),"您还没有获取验证码");
            return;
        }

        LoadingPopup.show(getActivity());

        PhoneCodeVerifyRequest verifyRequest = new PhoneCodeVerifyRequest(getActivity(), new PhoneCodeVerifyRequest.Params(verify));

        verifyRequest.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(getActivity());
            }

            @Override
            public void onSuccess(Object object) {
                FillUserActivity.open(getActivity(),new FillUserFragment.Model(UserType.USERTYPE_USERNAME,phone,null,null, Sex.UnKouwn,null,password));
            }

            @Override
            public void onError(int error, String msg) {

            }
        });

    }

    private void startTimer() {
        Timer t = new Timer();
        t.schedule(new SendTimer(60), 0, 1000);
    }

    private class SendTimer extends TimerTask {

        private int second;

        public SendTimer(int second) {
            super();
            this.second = second;
            holder.btn_send.setEnabled(false);
        }

        @Override
        public void run() {
            Activity activity = getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        second--;
                        holder.btn_send.setText("发送验证码(" + second + ")");
                        if (second == 0) {
                            holder.btn_send.setText("发送验证码");
                            holder.btn_send.setEnabled(true);
                            cancel();
                        }

                    }
                });
            }

        }

    }

}
