package com.cctv.music.cctv15.model;

import com.cctv.music.cctv15.utils.DateUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketItem implements Serializable{

    //address= 万事达中心（原五棵松体育馆）

    private String address;

    //aid=1

    private int aid;

    //convert_score=30000

    private long convert_score;

    //endtime=/Date(1440864000000)/

    private String endtime;

    //imgurl=http://cctv15.1du1du.com/cctv15/getTheImage?fileName=57bc726ad39a4722b813ff5d4332f4d8.jpg

    private String imgurl;

    //starttime=/Date(1438704000000)/

    private String starttime;

    //title=生来征服2015王杰北京演唱会7777

    private String title;

    public String getAddress() {
        return address;
    }

    public int getAid() {
        return aid;
    }

    public long getConvert_score() {
        return convert_score;
    }

    public Date getEndtime() {
        return DateUtils.parse(endtime);
    }

    public String getImgurl() {
        return imgurl;
    }

    public Date getStarttime() {
        return DateUtils.parse(starttime);
    }

    public String getTitle() {
        return title;
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
