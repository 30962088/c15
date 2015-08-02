package com.cctv.music.cctv15.model;

public enum Sex {
    Female("female"), Male("male"), UnKouwn("unkouwn");
    private String code;
    private Sex(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
