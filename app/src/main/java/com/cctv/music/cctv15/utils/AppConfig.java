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

    private AppConfig(Context context) {

        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            host = bundle.getString("network_host");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }
}
