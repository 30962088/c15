package com.cctv.music.cctv15.model;


public class AppItem {

    private String img;

    private String name;

    private String desc;

    private String packagename;


    public AppItem(String img, String name, String desc, String packagename) {
        this.img = img;
        this.name = name;
        this.desc = desc;
        this.packagename = packagename;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPackagename() {
        return packagename;
    }
}
