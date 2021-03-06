package com.cctv.music.cctv15.model;

import com.cctv.music.cctv15.ui.SliderFragment;

import java.io.Serializable;

public class Song implements Serializable{

    //sid=14

    private int sid;

    //comment_count=8

    private long comment_count;

    //singername=张菲菲

    private String singername;

    //songguid=e8afb92c55474c51ad5cb2395e40217b.mp3

    private String songguid;

    //songname=水浒传

    private String songname;

    //songurl=http://cctv11.oss-cn-hangzhou.aliyuncs.com/e8afb92c55474c51ad5cb2395e40217b.mp3

    private String songurl;

    //surfaceguid=77b14ea1e9fc4f4eade47047d6806929.jpg

    private String surfaceguid;

    //surfaceurl=http://cctv15.1du1du.com/cctv15/getTheImage?fileName=77b14ea1e9fc4f4eade47047d6806929.jpg&width=100&height=100

    private String surfaceurl;

    private String recommendimgurl;

    private String recommendimgguid;

    public int getSid() {
        return sid;
    }

    public String getSingername() {
        return singername;
    }

    public String getSongguid() {
        return songguid;
    }

    public String getSongname() {
        return songname;
    }

    public String getSongurl() {
        return songurl;
    }

    public String getSurfaceguid() {
        return surfaceguid;
    }

    public String getSurfaceurl() {
        return surfaceurl;
    }

    public void setComment_count(long comment_count) {
        this.comment_count = comment_count;
    }

    public SliderFragment.Model toSliderModel(){
        return new SliderFragment.Model(null,getRecommendimgurl(),getSingername()+"《"+getSongname()+"》");
    }

    public long getComment_count() {
        return comment_count;
    }

    public String getRecommendimgguid() {
        return recommendimgguid;
    }

    public String getRecommendimgurl() {
        return recommendimgurl;
    }
}
