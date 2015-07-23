package com.cctv.music.cctv15.model;


import com.cctv.music.cctv15.utils.DateUtils;

import java.util.Date;

public class Content {
    //categoryid=1
    private int categoryid;
    //categoryname=新闻
    private String categoryname;
    //commentcount=1
    private int commentcount;
    //contentsdate=/Date(1428566663000)/
    private String contentsdate;
    //contentsid=473
    private int contentsid;
    //contentstitle=【魔法奇迹】一分钟魔术大赛第三季第三周参赛视频！APP投票通道开启
    private String contentstitle;
    //ishavevideo=1
    private int ishavevideo;
    //poemauthor=
    private String poemauthor;
    //praisecount=0
    private int praisecount;
    //videositeurl=
    private String videositeurl;

    private Attachment attachment;

    public Attachment getAttachment() {
        return attachment;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public Date getContentsdate() {
        return DateUtils.parse(contentsdate);
    }

    public int getContentsid() {
        return contentsid;
    }

    public String getContentstitle() {
        return contentstitle;
    }

    public int getIshavevideo() {
        return ishavevideo;
    }

    public String getPoemauthor() {
        return poemauthor;
    }

    public int getPraisecount() {
        return praisecount;
    }

    public String getVideositeurl() {
        return videositeurl;
    }
}
