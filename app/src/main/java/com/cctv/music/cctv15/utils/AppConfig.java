package com.cctv.music.cctv15.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.io.Serializable;

public class AppConfig implements Serializable{

    private static AppConfig instance;

    public static AppConfig getInstance() {
        return instance;
    }

    public static void init(Context context){
        instance = new AppConfig(context);
    }

    private String host;

    private String WX_APPID;
    private String WX_AppSecret;
    private String QQ_APPID;
    private String QQ_APPKEY;
    private String UMENG_APPKEY;
    private String UMENG_CHANNEL;
    private String push_api_key;

    private AppConfig(Context context) {

        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            host = bundle.getString("network_host");
            WX_APPID = bundle.getString("WX_APPID");
            WX_AppSecret = bundle.getString("WX_AppSecret");
            QQ_APPID = bundle.getString("QQ_APPID");
            QQ_APPKEY = bundle.getString("QQ_APPKEY");
            UMENG_APPKEY = bundle.getString("UMENG_APPKEY");
            UMENG_CHANNEL = bundle.getString("UMENG_CHANNEL");
            push_api_key = bundle.getString("push_api_key");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public String getWX_APPID() {
        return WX_APPID;
    }

    public String getWX_AppSecret() {
        return WX_AppSecret;
    }

    public String getQQ_APPID() {
        return QQ_APPID;
    }

    public String getQQ_APPKEY() {
        return QQ_APPKEY;
    }

    public String getUMENG_APPKEY() {
        return UMENG_APPKEY;
    }

    public String getUMENG_CHANNEL() {
        return UMENG_CHANNEL;
    }

    public String getPush_api_key() {
        return push_api_key;
    }
}
