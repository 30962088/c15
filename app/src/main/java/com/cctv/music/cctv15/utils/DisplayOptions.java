package com.cctv.music.cctv15.utils;

import com.cctv.music.cctv15.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


public enum DisplayOptions {
    IMG(new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.empty)
            .showImageOnLoading(R.drawable.empty).cacheInMemory(true)
            .cacheOnDisk(true).build());

    DisplayImageOptions options;

    DisplayOptions(DisplayImageOptions options) {
        this.options = options;
    }

    public DisplayImageOptions getOptions() {
        return options;
    }
}