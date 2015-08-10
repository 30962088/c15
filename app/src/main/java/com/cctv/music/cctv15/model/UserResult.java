package com.cctv.music.cctv15.model;

import java.io.Serializable;


public class UserResult implements Serializable{

    private String pkey;

    private Integer userid;

    public String getPkey() {
        return pkey;
    }

    public boolean exsits(){
        if(userid == null || userid == 0){
            return false;
        }
        return true;
    }

    public Integer getUserid() {
        return userid;
    }
}
