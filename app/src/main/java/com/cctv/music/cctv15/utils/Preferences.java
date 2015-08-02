package com.cctv.music.cctv15.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {


    private static Preferences instance;

    public static Preferences getInstance() {
        return instance;
    }

    public static void init(Context context){
        instance = new Preferences(context);
    }

    private static final String NAME = "Preferences";

    private SharedPreferences preferences;

    private Context context;

    public Preferences(Context context) {
        preferences = context.getSharedPreferences(NAME, 0);
        this.context = context;
    }

    public void setWeiboUid(String uid){
        preferences.edit().putString("weibo_uid", uid).commit();
    }

    public String getWeiboUid(){
        return preferences.getString("weibo_uid",null);
    }

    public void setWeiboAccessToken(String access_token){
        preferences.edit().putString("weibo_access_token", access_token).commit();
    }

    public String getWeiboAccessToken(){
        return preferences.getString("weibo_access_token",null);
    }

    public void clearWeibo(){
        setWeiboAccessToken(null);
        setWeiboUid(null);
    }

}
