package com.cctv.music.cctv15.model;


public class GameImg {
    //gameimgguid=45caef01e9b642b9acbb34d0a25035bf.jpg

    private String gameimgguid;

    //gameimgurl=http://cctv15.1du1du.com/cctv15/getTheImage?fileName=45caef01e9b642b9acbb34d0a25035bf.jpg&width=0&height=0
    private String gameimgurl;
    //levelnum=1
    private int levelnum;
    //song_index=1
    private int song_index;

    public String getGameimgguid() {
        return gameimgguid;
    }

    public String getGameimgurl() {
        return gameimgurl;
    }

    public int getLevelnum() {
        return levelnum;
    }

    public int getSong_index() {
        return song_index;
    }
}
