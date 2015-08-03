package com.cctv.music.cctv15.model;

import java.io.Serializable;
import java.util.List;

public class MyTicket implements Serializable{

    //highestscore=260

    private long highestscore;

    //loginuserimgurl=http://cctv15.1du1du.com/cctv15/getTheImage?fileName=07581329cb384055a7153b2235ac98b8.jpg

    private String loginuserimgurl;

    //myranking=5

    private int myranking;

    //myscore=260

    private int myscore;

    //myticket_count=0

    private int myticket_count;

    //username=阳光的小部的

    private String username;

    private List<TicketItem> activitylist;

    public long getHighestscore() {
        return highestscore;
    }

    public String getLoginuserimgurl() {
        return loginuserimgurl;
    }

    public int getMyranking() {
        return myranking;
    }

    public int getMyscore() {
        return myscore;
    }

    public int getMyticket_count() {
        return myticket_count;
    }

    public String getUsername() {
        return username;
    }

    public List<TicketItem> getActivitylist() {
        return activitylist;
    }
}
