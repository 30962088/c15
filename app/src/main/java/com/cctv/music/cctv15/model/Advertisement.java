package com.cctv.music.cctv15.model;


import java.io.Serializable;

public class Advertisement implements Serializable{

    //adid=4

    private String adid;

    //adtitle=官网

    private String adtitle;

    //devicetype=iphone4

    private String devicetype;

    //imageformat=.png

    private String imageformat;

    //imageguid=d0581da45f254730b4221821b0845761

    private String imageguid;

    //imageurl=http://cctv15.1du1du.com/cctv15/getTheImage?fileName=d0581da45f254730b4221821b0845761.png

    private String imageurl;

    //linkurl=http://cctv15.cntv.cn/

    private String linkurl;

    public String getAdid() {
        return adid;
    }

    public String getAdtitle() {
        return adtitle;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public String getImageformat() {
        return imageformat;
    }

    public String getImageguid() {
        return imageguid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getLinkurl() {
        return linkurl;
    }
}
