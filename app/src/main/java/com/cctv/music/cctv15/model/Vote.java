package com.cctv.music.cctv15.model;

import com.cctv.music.cctv15.utils.AppConfig;
import com.cctv.music.cctv15.utils.DateUtils;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;

import java.io.Serializable;
import java.util.Date;

public class Vote implements Serializable{
    private Attachment attachment;
    //attachmentid=531
    private int attachmentid;
    //datetime=/Date(1437901755875)/
    private String datetime;
    //endtime=/Date(1431792000000)/
    private String endtime;
    //templeteid=1
    private int templeteid;
    //templeteurl=http://music.1du1du.com/voteview/index
    private String templeteurl;
    //votecommentcount=0
    private int votecommentcount;
    //votecontent=票选一分钟魔术大赛第三季月赛冠军！
    private String votecontent;
    //voteid=29
    private int voteid;
    //votetitle=票选一分钟魔术大赛第三季月赛冠军！
    private String votetitle;
    //voteusercount=1785
    private int voteusercount;

    public Attachment getAttachment() {
        return attachment;
    }

    public int getAttachmentid() {
        return attachmentid;
    }

    public Date getDatetime() {
        return DateUtils.parse(datetime);
    }

    public Date getEndtime() {
        return DateUtils.parse(endtime);
    }

    public int getTempleteid() {
        return templeteid;
    }

    public String getTempleteurl() {
        return templeteurl;
    }

    public int getVotecommentcount() {
        return votecommentcount;
    }

    public String getVotecontent() {
        return votecontent;
    }

    public int getVoteid() {
        return voteid;
    }

    public String getVotetitle() {
        return votetitle;
    }

    public int getVoteusercount() {
        return voteusercount;
    }

    public String getDetailUrl(){
        return templeteurl+"?voteid="+voteid;
    }

    private Boolean end;

    public boolean isEnd(){
        if(end == null){
            end = new Date().getTime()>getEndtime().getTime();
        }
        return end;
    }

    public String getDate2(){
        String str;
        if(isEnd()){
            str = "已截止";
        }else{

            str = "距离活动还有"+ (-Utils.countDays(getEndtime()))+"天";
        }
        return str;
    }

    public String getWebUrl(){
        String url = getShareUrl();
        if(Preferences.getInstance().isLogin()){
            url = getShareUrl()+"&userid="+Preferences.getInstance().getUid();
        }
        return url;
    }



    public String getShareUrl(){
        String tpl;
        if(isEnd()){
            tpl = "complete";
        }else{
            tpl = "index";
        }
        return AppConfig.getInstance().getHost()+"/voteview/"+tpl+"?voteid="+voteid;
    }

}
