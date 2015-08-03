package com.cctv.music.cctv15.model;


import com.cctv.music.cctv15.utils.DateUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PushInfo implements Serializable{

    //description=请期待...

    private String description;

    //pid=260

    private int pid;

    //sendtime=/Date(1410699122000)/

    private String sendtime;

    //title=music App 即将发布

    private String title;

    public String getDescription() {
        return description;
    }

    private String date;

    public String getDate(){
        if(date == null){
            date = new SimpleDateFormat("yyyy.MM.dd").format(getSendtime());
        }
        return date;
    }

    public int getPid() {
        return pid;
    }

    public Date getSendtime() {
        return DateUtils.parse(sendtime);
    }

    public String getTitle() {
        return title;
    }

}
