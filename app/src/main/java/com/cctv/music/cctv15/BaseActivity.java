package com.cctv.music.cctv15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.cctv.music.cctv15.ui.PhotoSelectPopupWindow;
import com.cctv.music.cctv15.utils.AppConfig;
import com.cctv.music.cctv15.utils.Dirctionary;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.io.File;

public class BaseActivity extends FragmentActivity {

    public static interface OnWeiboBindingListener{
        public void onWeiboBinding();
    }

    private void initUmeng() throws PackageManager.NameNotFoundException {
        AppConfig config = AppConfig.getInstance();
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this,
                config.getQQ_APPID(), config.getQQ_APPKEY());
        qqSsoHandler.addToSocialSDK();
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, config.getQQ_APPID(),config.getQQ_APPKEY());
        qZoneSsoHandler.addToSocialSDK();
        UMWXHandler wxHandler = new UMWXHandler(this,config.getWX_APPID());
        wxHandler.addToSocialSDK();
        UMWXHandler wxCircleHandler = new UMWXHandler(this,config.getWX_AppSecret());
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    private static final int ACTION_REQUEST_BINDING_WEIBO = 7;

    private static final int ACTION_REQUEST_NICKNAME = 4;

    public static interface OnNicknameFillListener{
        public void onNicknameFill(String nickname);
    }

    private OnNicknameFillListener onNicknameFillListener;

    public void getNickname(String nickname,OnNicknameFillListener onNicknameFillListener){
        this.onNicknameFillListener = onNicknameFillListener;
        Intent intent = new Intent(this, NicknameActivity.class);
        intent.putExtra("nickname", nickname);
        startActivityForResult(intent, ACTION_REQUEST_NICKNAME);
    }

    private OnWeiboBindingListener onWeiboBindingListener;

    public void bindingWeibo(OnWeiboBindingListener onWeiboBindingListener){
        this.onWeiboBindingListener = onWeiboBindingListener;
        Intent intent = new Intent(this, BindingWeiboActivity.class);
        startActivityForResult(intent, ACTION_REQUEST_BINDING_WEIBO);
    }

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initUmeng();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onContentView();
    }

    private void onContentView() {
        View back = findViewById(R.id.back);
        if(back != null){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_REQUEST_NICKNAME:
                if (resultCode == Activity.RESULT_OK) {
                    if(onNicknameFillListener != null){
                        onNicknameFillListener.onNicknameFill(data.getStringExtra("nickname"));
                    }
                }
                break;
            case ACTION_REQUEST_CITY:
                if (resultCode == Activity.RESULT_OK) {
                    if(onCitySelectionListener != null){
                        onCitySelectionListener.onCitySelection(data.getStringExtra("city"));
                    }
                }
                break;
            case ACTION_REQUEST_BINDING_WEIBO:
                if (resultCode == Activity.RESULT_OK) {
                    if(onWeiboBindingListener != null){
                        onWeiboBindingListener.onWeiboBinding();
                    }
                }
                break;
            case ACTION_REQUEST_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    if(onGallerySelectionListener != null){
                        File file = new File(convertMediaUriToPath(this, data.getData()));

                        onGallerySelectionListener.onGallerySelection(file);
                    }
                }
                break;
            case ACTION_REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    if(onGallerySelectionListener != null){
                        File file = new File(convertMediaUriToPath(this, cameraPic));
                        onGallerySelectionListener.onGallerySelection(file);
                    }
                }
                break;
        }
    }

    public static String convertMediaUriToPath(Context context, Uri uri) {
        if(uri.toString().startsWith("file://")){
            return uri.getPath();
        }
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[] { document_id }, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor
                .getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public static interface OnGallerySelectionListener{
        public void onGallerySelection(File file);
    }

    private OnGallerySelectionListener onGallerySelectionListener;

    private Uri cameraPic;

    private static final int ACTION_REQUEST_GALLERY = 1;

    private static final int ACTION_REQUEST_CAMERA = 2;

    private static final int ACTION_REQUEST_CITY = 3;

    public void getCity(OnCitySelectionListener onCitySelectionListener){
        this.onCitySelectionListener = onCitySelectionListener;
        Intent intent = new Intent(this, CityActivity.class);
        startActivityForResult(intent, ACTION_REQUEST_CITY);
    }

    public static interface OnCitySelectionListener{
        public void onCitySelection(String city);
    }

    private OnCitySelectionListener onCitySelectionListener;

    public void getPhoto(OnGallerySelectionListener onGallerySelectionListener){
        this.onGallerySelectionListener = onGallerySelectionListener;
        new PhotoSelectPopupWindow(this,new PhotoSelectPopupWindow.OnItemClickListener() {

            @Override
            public void onItemClick(int id) {
                switch (id) {
                    case R.id.take_photo:
                        Intent getCameraImage = new Intent("android.media.action.IMAGE_CAPTURE");
                        cameraPic = Uri.fromFile(Dirctionary.creatPicture());
                        getCameraImage.putExtra(MediaStore.EXTRA_OUTPUT, cameraPic);
                        startActivityForResult(getCameraImage, ACTION_REQUEST_CAMERA);
                        break;
                    case R.id.read_photo:
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");

                        Intent chooser = Intent.createChooser(intent, "从本地相册读取");
                        startActivityForResult(chooser, ACTION_REQUEST_GALLERY);
                        break;
                    default:
                        break;
                }
            }
        },"设置头像");
    }



}
