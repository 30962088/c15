package com.cctv.music.cctv15.model;


import java.util.List;

public class CCTV15 {

    //channelName=CCTV-15 音乐

    private String channelName;

    //isLive=精彩音乐汇

    private String isLive;

    //liveSt=1437885300

    private long liveSt;

    private List<Program> program;

    public String getChannelName() {
        return channelName;
    }

    public String getIsLive() {
        return isLive;
    }

    public List<Program> getProgram() {
        return program;
    }

    public long getLiveSt() {
        return liveSt;
    }
}
