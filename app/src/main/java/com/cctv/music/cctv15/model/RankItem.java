package com.cctv.music.cctv15.model;

import java.io.Serializable;

public class RankItem implements Serializable{

    //loginuserimgurl=http://cctv15.1du1du.com/cctv15/getTheImage?fileName=c96c28e68b42464ba45cf14893a51618.jpg

    private String loginuserimgurl;

    //total_score=70720

    private int total_score;

    //userid=169

    private int userid;

    //username=sdkasdd

    private String username;

    public String getLoginuserimgurl() {
        return loginuserimgurl;
    }

    public int getTotal_score() {
        return total_score;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }
}
