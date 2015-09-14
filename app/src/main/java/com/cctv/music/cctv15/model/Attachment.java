package com.cctv.music.cctv15.model;

import com.cctv.music.cctv15.utils.AppConfig;
import com.cctv.music.cctv15.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable{
    //attachmentdate=/Date(1428566663000)/
    private String attachmentdate;
    //attachmentformat=.jpg
    private String attachmentformat;
    //attachmentguid=dc60130a948649bb8083317eb246c31b
    private String attachmentguid;
    //attachmentheight=309
    private String attachmentheight;
    //attachmentid=528
    private String attachmentid;
    //attachmentimgurl=http://music.1du1du.com/cctv15/getTheImage?fileName=dc60130a948649bb8083317eb246c31b.jpg
    private String attachmentimgurl;
    //attachmentname=U9990P28T3D4058519F326DT20131211113706
    private String attachmentname;
    //attachmentsize=67553
    private long attachmentsize;
    //attachmentwidth=412
    private int attachmentwidth;
    //isfirstimg=0
    private int isfirstimg;

    public Date getAttachmentdate() {
        return DateUtils.parse(attachmentdate);
    }

    public String getAttachmentformat() {
        return attachmentformat;
    }

    public String getAttachmentguid() {
        return attachmentguid;
    }

    public String getAttachmentheight() {
        return attachmentheight;
    }

    public String getAttachmentid() {
        return attachmentid;
    }

    public String getAttachmentimgurl(int width,int height){
        return String.format(AppConfig.getInstance().getHost()+"/cctv15/getTheImage?fileName=%s%s&width=%d&height=%d",attachmentguid,attachmentformat,width,height);
    }

    public String getAttachmentimgurl() {
        return attachmentimgurl;
    }

    public String getAttachmentname() {
        return attachmentname;
    }

    public long getAttachmentsize() {
        return attachmentsize;
    }

    public int getAttachmentwidth() {
        return attachmentwidth;
    }

    public int getIsfirstimg() {
        return isfirstimg;
    }
}
