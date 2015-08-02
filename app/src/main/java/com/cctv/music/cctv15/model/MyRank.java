package com.cctv.music.cctv15.model;


import java.io.Serializable;
import java.util.List;

public class MyRank implements Serializable{

    //highestscore=480

    private int highestscore;

    //myloginuserimgurl=http://cctv15.1du1du.com/cctv15/getTheImage?fileName=99bfa998fe0149ccbfc8ff801e8964ab.jpg

    private String myloginuserimgurl;

    //myranking=4

    private int myranking;

    //myscore=480

    private int myscore;

    //myusername=阳光的小部的

    private String myusername;

    //userid=170

    private int userid;

    private List<RankItem> ranklist;

    public int getHighestscore() {
        return highestscore;
    }

    public String getMyloginuserimgurl() {
        return myloginuserimgurl;
    }

    public int getMyranking() {
        return myranking;
    }

    public int getMyscore() {
        return myscore;
    }

    public String getMyusername() {
        return myusername;
    }

    public int getUserid() {
        return userid;
    }

    public List<RankItem> getRanklist() {
        return ranklist;
    }
}
