package com.cctv.music.cctv15.model;

import com.cctv.music.cctv15.utils.DateUtils;

import java.util.Date;

public class NewsDescription {

    //commentcount=5

    private int commentcount;

    //contentsdate=/Date(1431324920000)/

    private String contentsdate;

    //contentsid=475

    private int contentsid;

    //contentstitle=反法西斯战争胜利70周年优秀音乐电视作品展播-----放飞和平歌！

    private String contentstitle;

    //description=<p><img alt="" src="http://music.1du1du.com/cctv15/getTheImage?fileName=719629deca8743818578d51709379174.png" style="width: 300px; height: 166px;" /></p>

    private String description;

    //poemauthor=

    private String poemauthor;

    //praisecount=0

    private int praisecount;

    //videositeurl=(null)

    private String videositeurl;

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

    public String getDescription() {
        return description;
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
