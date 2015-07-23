package com.cctv.music.cctv15.utils;


import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class Utils {
    public static void tip(final Context context, final String str) {
        new Handler(context.getMainLooper()).post(new Runnable() {

            public void run() {
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

            }
        });

    }
}
