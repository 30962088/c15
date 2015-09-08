package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.model.ClientUser;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.GetClientUserInfoRequest;
import com.cctv.music.cctv15.network.UpdateClientUserInfoRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.AliyunUtils;
import com.cctv.music.cctv15.utils.CropImageUtils;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.io.File;

public class AccountActivity extends BaseActivity implements View.OnClickListener,BaseActivity.OnGallerySelectionListener,BaseActivity.OnCitySelectionListener,AliyunUtils.UploadListener,BaseActivity.OnNicknameFillListener,BaseActivity.OnModifyPasswordListener {

    public static void open(Context context) {

        Intent intent = new Intent(context, AccountActivity.class);

        context.startActivity(intent);

    }

    @Override
    public void onModifyPassword(String password) {

    }


    private class ViewHolder{
        private TextView nickname;
        private TextView city;
        private ImageView avatar;
        private View advance;
        private TextView phone;


        public ViewHolder() {
            nickname = (TextView) findViewById(R.id.nickname);
            city = (TextView) findViewById(R.id.city);
            avatar = (ImageView) findViewById(R.id.avatar);
            advance = findViewById(R.id.advance);
            phone = (TextView) findViewById(R.id.phone);
        }
    }

    private ViewHolder holder;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_account);
        findViewById(R.id.btn_avatar).setOnClickListener(this);
        findViewById(R.id.btn_city).setOnClickListener(this);
        findViewById(R.id.btn_logout).setOnClickListener(this);
        findViewById(R.id.btn_nickname).setOnClickListener(this);
        findViewById(R.id.btn_phone).setOnClickListener(this);
        findViewById(R.id.btn_password).setOnClickListener(this);

        String phone = Preferences.getInstance().getPhone();

        if(!TextUtils.isEmpty(phone)){
            holder.advance.setVisibility(View.VISIBLE);
            holder.phone.setText(phone);
        }else{
            holder.advance.setVisibility(View.GONE);
        }

        holder = new ViewHolder();
        request();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_avatar:
                getPhoto(this);
                break;
            case R.id.btn_city:
                getCity(this);
                break;
            case R.id.btn_logout:
                logout();
                break;
            case R.id.btn_nickname:
                onnickname();
                break;
            case R.id.btn_password:
                modifyPassowrd(this);
                break;
        }
    }

    private void onnickname() {
        getNickname(holder.nickname.getText().toString(),this);
    }

    @Override
    public void onGallerySelection(File file) {
        LoadingPopup.show(this);
        AliyunUtils.getInstance().upload(CropImageUtils.cropImage(this, file, 300, 300), "cctv11", this);

    }

    @Override
    public void onNicknameFill(final String nickname) {
        UpdateClientUserInfoRequest.Params params = new UpdateClientUserInfoRequest.Params(Preferences.getInstance().getUid(),Preferences.getInstance().getPkey());
        params.setNickname(nickname);
        UpdateClientUserInfoRequest request = new UpdateClientUserInfoRequest(this,params);
        LoadingPopup.show(this);
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                holder.nickname.setText(nickname);
            }

            @Override
            public void onError(int error, String msg) {
                Utils.tip(context,"操作失败");
            }
        });
    }

    @Override
    public void onCitySelection(final String city) {
        UpdateClientUserInfoRequest.Params params = new UpdateClientUserInfoRequest.Params(Preferences.getInstance().getUid(),Preferences.getInstance().getPkey());
        params.setAddress(city);
        UpdateClientUserInfoRequest request = new UpdateClientUserInfoRequest(this,params);
        LoadingPopup.show(context);
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                holder.city.setText(""+city);
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
    }

    private void logout() {
        Preferences.getInstance().logout();
        finish();

    }

    private void request() {
        GetClientUserInfoRequest request = new GetClientUserInfoRequest(this,new GetClientUserInfoRequest.Params(Preferences.getInstance().getUid()));
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                GetClientUserInfoRequest.Result result = (GetClientUserInfoRequest.Result) object;
                ClientUser user = result.getModel();
                ImageLoader.getInstance().displayImage(user.getUserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
                holder.nickname.setText("" + user.getNickname());
                holder.city.setText(""+user.getAddress());
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
    }

    @Override
    public void onsuccess(AliyunUtils.UploadResult result) {
        UpdateClientUserInfoRequest.Params params = new UpdateClientUserInfoRequest.Params(Preferences.getInstance().getUid(),Preferences.getInstance().getPkey());
        params.setUserimgformat(result.getExt());
        params.setUserimgguid(result.getGuid());
        UpdateClientUserInfoRequest request = new UpdateClientUserInfoRequest(this,params);
        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                request();
            }

            @Override
            public void onError(int error, String msg) {
                Utils.tip(context,"操作失败");
            }
        });
    }

}
