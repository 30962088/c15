package com.cctv.music.cctv15.model;


import com.cctv.music.cctv15.utils.DateUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivistTicket implements Serializable{

    //aid=1

    private String aid;

    //changed_code=18500238470

    private String changed_code;

    //changed_name=11

    private String changed_name;

    //endtime=/Date(1440864000000)/

    private String endtime;

    //isover=1

    private int isover;

    //starttime=/Date(1438704000000)/

    private String starttime;

    //title=生来征服2015王杰北京演唱会7777

    private String title;

    //userid=189

    private String userid;

    public String getAid() {
        return aid;
    }

    public String getChanged_code() {
        return changed_code;
    }

    public String getChanged_name() {
        return changed_name;
    }

    public Date getEndtime() {
        return DateUtils.parse(endtime);
    }

    public int getIsover() {
        return isover;
    }

    public Date getStarttime() {
        return DateUtils.parse(starttime);
    }

    public String getTitle() {
        return title;
    }

    public String getUserid() {
        return userid;
    }

    private String date;

    public String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        if(date == null){
            date = format.format(getStarttime())+"-"+format.format(getEndtime());
        }
        return date;
    }

}
