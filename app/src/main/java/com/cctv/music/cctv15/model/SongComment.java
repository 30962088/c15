package com.cctv.music.cctv15.model;


import com.cctv.music.cctv15.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class SongComment implements Serializable{

    //becommentid=0

    private int becommentid;

    //beuserid=0

    private int beuserid;

    //beusername=

    private String beusername;

    //commentcontent=不错噶

    private String commentcontent;

    //commentdate=/Date(1440740784000)/

    private String commentdate;

    //id=1

    private String id;

    //songid=10

    private String songid;

    //userid=207

    private String userid;

    //userimgurl=http://cctv15.1du1du.com/cctv15/getTheImage?fileName=a27627faf498bf8554bcf9248ad030ef4512b9bd.jpg

    private String userimgurl;

    //username=龙龙

    private String username;

    public int getBecommentid() {
        return becommentid;
    }

    public void setBecommentid(int becommentid) {
        this.becommentid = becommentid;
    }

    public int getBeuserid() {
        return beuserid;
    }

    public void setBeuserid(int beuserid) {
        this.beuserid = beuserid;
    }

    public String getBeusername() {
        return beusername;
    }

    public void setBeusername(String beusername) {
        this.beusername = beusername;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public Date getCommentdate() {
        return DateUtils.parse(commentdate);
    }

    public void setCommentdate(String commentdate) {
        this.commentdate = commentdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserimgurl() {
        return userimgurl;
    }

    public void setUserimgurl(String userimgurl) {
        this.userimgurl = userimgurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
