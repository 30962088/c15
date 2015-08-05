package com.cctv.music.cctv15.model;


import java.io.Serializable;

public class TicketDetail implements Serializable{

    //address= 万事达中心（原五棵松体育馆）

    private String address;

    //aid=1

    private String aid;

    //colstatus=Published

    private String colstatus;

    //contents=生来征服2015王杰北京演唱会将于2015-08-08在万事达中心（原五棵松体育馆）演出，同时永乐票务为您长期提供万事达中心（原五棵松体育馆）演出信息，各种演唱会门票预订服务，演出门票尽在永乐票务。

    private String contents;
    //convert_score=30000
    private String convert_score;
    //diffdays=24
    private int diffdays;
    //endtime=/Date(1440864000000)/
    private String endtime;
    //registdatetime=/Date(1437558441000)/
    private String registdatetime;
    //starttime=/Date(1438704000000)/
    private String starttime;
    //surplusnumber=93
    private int surplusnumber;
    //title=生来征服2015王杰北京演唱会7777
    private String title;
    //totalnumber=100
    private int totalnumber;

    public String getAddress() {
        return address;
    }

    public String getAid() {
        return aid;
    }

    public String getColstatus() {
        return colstatus;
    }

    public String getContents() {
        return contents;
    }

    public String getConvert_score() {
        return convert_score;
    }

    public int getDiffdays() {
        return diffdays;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getRegistdatetime() {
        return registdatetime;
    }

    public String getStarttime() {
        return starttime;
    }

    public int getSurplusnumber() {
        return surplusnumber;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalnumber() {
        return totalnumber;
    }
}
