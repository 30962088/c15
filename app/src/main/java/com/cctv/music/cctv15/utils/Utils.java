package com.cctv.music.cctv15.utils;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static String logStringCache = "";

    public static void setTag(Context context, final String userId){
        PushManager.setTags(context, new ArrayList<String>() {{
            add("userId" + userId);
        }});
    }

    public static void delTag(Context context){
        PushManager.setTags(context,null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Activity activity){
        int width;
        int height;
        Point size = new Point();
        WindowManager w = activity.getWindowManager();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)    {
            w.getDefaultDisplay().getSize(size);
            width = size.x;
            height = size.y;
        }else{
            Display d = w.getDefaultDisplay();
            width = d.getWidth();
            height = d.getHeight();
        }

        return new Point(width,height);

    }

    public static void tip(final Context context, final String str) {
        new Handler(context.getMainLooper()).post(new Runnable() {

            public void run() {
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public static int countDays(Date date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }


    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String formatTimer(int milliseconds) {

        int second = milliseconds / 1000;

        return _formatTimer(second / 60) + ":" + _formatTimer(second % 60);
    }

    private static String _formatTimer(int count) {
        if (count < 10) {
            return "0" + count;
        }
        return "" + count;
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static int dpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public static String getError(int code){
        Map<Integer,String> map = new HashMap<>();
        map.put(1001,"图片不存在");
        map.put(1002,"生成图片错误");
        map.put(1003,"上传图片错误");
        map.put(1007,"提交投票时，该userid用户已参与过投票，请不要重复投票");
        map.put(1008,"提交投票时，该phone用户已参与过投票，请不要重复投票");
        map.put(1010,"昵称重复");
        map.put(1011,"用户注册，该用户名已注册");
        map.put(1013,"验证码短信发送失败");
        map.put(1014,"您正在找回密码，但是该手机还没有注册，请先注册");
        map.put(1015,"您正在注册CCTV11唱戏吧,该手机号码已注册，请不要重复注册");
        map.put(1016,"您正在修改CCTV11唱戏吧手机号码,该手机号码已绑定其他用户，请更换其它手机号码绑定");
        map.put(1017,"您正在修改CCTV11唱戏吧手机号码,该手机号码已绑定您的用户");
        map.put(1018,"修改密码，原密码输入错误");
        map.put(1019,"pkey已经过期，请重新登录");
        map.put(1020,"请输入正确的手机号");
        map.put(1021,"输入密码错误");
        map.put(1022,"用户名不存在");
        map.put(1023,"短信验证码输入错误！");
        map.put(1024,"短信验证码已过期。");
        map.put(1025,"操作过于频繁");
        map.put(1026,"该用户已兑换过此活动的门票");
        map.put(1027,"没有登录，请先登录");
        map.put(1028,"该用户已为该歌曲打过评分");
        map.put(9999,"系统异常");
        map.put(1029,"该活动门票已抢完");
        return map.get(code);
    }

}
