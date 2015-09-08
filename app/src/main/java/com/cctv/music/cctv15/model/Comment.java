package com.cctv.music.cctv15.model;

import android.text.TextUtils;

import com.cctv.music.cctv15.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{

    //colstatus=Published

    private String colstatus;

    //commentcount=0

    private long commentcount;

    //commentid=156

    private int commentid;

    //contentsid=475

    private int contentsid;

    //datetime=/Date(1433126630000)/

    private String datetime;

    //iscommentid=0

    private int iscommentid;

    //isuserid=0

    private int isuserid;

    //isuserimgguid=

    private String isuserimgguid;

    //isuserimgurl=(null)

    private String isuserimgurl;

    //isusername=

    private String isusername;

    //remark=vccgg is a great day to day and night with the best of the day before I get a follow back on my way home from work to

    private String remark;

    //userid=1005

    private int userid;

    //userimgformat=.jpg

    private String userimgformat;

    //userimgguid=4804cc72a7394edea0d2b3b2ab635890

    private String userimgguid;

    //userimgurl=http://music.1du1du.com/cctv15/getTheImage?fileName=4804cc72a7394edea0d2b3b2ab635890.jpg

    private String userimgurl;

    //username=测测测测册

    private String username;

    public String getColstatus() {
        return colstatus;
    }

    public long getCommentcount() {
        return commentcount;
    }

    public int getCommentid() {
        return commentid;
    }

    public int getContentsid() {
        return contentsid;
    }

    public Date getDatetime() {
        return DateUtils.parse(datetime);
    }

    public int getIscommentid() {
        return iscommentid;
    }

    public int getIsuserid() {
        return isuserid;
    }

    public String getIsuserimgguid() {
        return isuserimgguid;
    }

    public String getIsuserimgurl() {
        return isuserimgurl;
    }

    public String getIsusername() {
        return isusername;
    }

    public String getRemark() {
        return remark;
    }

    private String content;

    public String getContent(){
        if(content == null){
            content = remark;
            if(!TextUtils.isEmpty(isusername)){
                content = "回复<font color='#0b92c3'>"+isusername+"</font>"+" "+content;
            }
        }
        return content;
    }

    public int getUserid() {
        return userid;
    }

    public String getUserimgformat() {
        return userimgformat;
    }

    public String getUserimgguid() {
        return userimgguid;
    }

    public String getUserimgurl() {
        return userimgurl;
    }

    public String getUsername() {
        return username;
    }
}
